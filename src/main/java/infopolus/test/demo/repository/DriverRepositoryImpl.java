package infopolus.test.demo.repository;

import infopolus.test.demo.repository.entity.DriverEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Repository
public class DriverRepositoryImpl implements DriverRepository {
    private static final Logger logger = LogManager.getLogger();
    private final DataSource dataSource;

    @Autowired
    public DriverRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public DriverEntity createDriver(DriverEntity driverEntity) {
        String query = "INSERT INTO driver (name, license_number, login, password) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, driverEntity.getName());
            statement.setString(2, driverEntity.getLicenseNumber());
            statement.setString(3, driverEntity.getLogin());
            statement.setString(4, driverEntity.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                driverEntity.setId(resultSet.getObject(1, Long.class));
            }
            logger.info("Driver {} added to DB", driverEntity);
            return driverEntity;
        } catch (SQLException e) {
            logger.error(String.format("Can`t add driver %s to DB", driverEntity), e.getMessage());
            throw new RuntimeException("Couldn't create "
                    + driverEntity + ". ", e);
        }
    }

    @Override
    public Optional<DriverEntity> getDriverById(Long id) {
        String query = "SELECT * FROM driver"
               + " WHERE id = ? AND deleted = FALSE";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            DriverEntity driverEntity = null;
            if (resultSet.next()) {
                driverEntity = parseDriverFromResultSet(resultSet);
            }
            logger.info("Got driver from DB by id = {}", id);
            return Optional.ofNullable(driverEntity);
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get driver from DB with id = %s", id),
                    e.getMessage());
            throw new RuntimeException("Couldn't get driver by id " + id, e);
        }
    }

    @Override
    public List<DriverEntity> getAllDrivers() {
        String query = "SELECT * FROM driver WHERE deleted = FALSE";
        List<DriverEntity> driverEntities = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                driverEntities.add(parseDriverFromResultSet(resultSet));
            }
            logger.info("Got all drivers from DB");
            return driverEntities;
        } catch (SQLException e) {
            logger.error("Couldn't get all drivers from DB: {}", e.getMessage());
            throw new RuntimeException("Couldn't get a list of drivers from driversDB.",
                    e);
        }
    }

    @Override
    public DriverEntity updateDriver(DriverEntity driverEntity) {
        String query = "UPDATE driver "
                + "SET name = ?, license_number = ? "
                + "WHERE id = ? AND deleted = FALSE";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement
                     = connection.prepareStatement(query)) {
            statement.setString(1, driverEntity.getName());
            statement.setString(2, driverEntity.getLicenseNumber());
            statement.setLong(3, driverEntity.getId());
            statement.executeUpdate();
            logger.info("Updated {} in DB", driverEntity);
            return driverEntity;
        } catch (SQLException e) {
            logger.error(String.format("Couldn't update %s in DB", driverEntity)
                    + "{}",e.getMessage());
            throw new RuntimeException("Couldn't update "
                    + driverEntity + " in driversDB.", e);
        }
    }

    @Override
    public boolean deleteDriverById(Long id) {
        String query = "UPDATE driver SET deleted = TRUE WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            logger.info("Deleted driver from DB by id = {}", id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(String.format("Couldn't delete driver from DB with id = %s", id),
                    e.getMessage());
            throw new RuntimeException("Couldn't delete driver with id " + id, e);
        }
    }

    private DriverEntity parseDriverFromResultSet(ResultSet resultSet) throws SQLException {
        Long newId = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String licenseNumber = resultSet.getString("license_number");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        DriverEntity driverEntity = new DriverEntity(name, licenseNumber, login, password);
        driverEntity.setId(newId);
        driverEntity.setLogin(login);
        driverEntity.setPassword(password);
        return driverEntity;
    }

    @Override
    public Optional<DriverEntity> findDriverByLogin(String login) {
        String query = "SELECT * FROM driver WHERE login = ? AND deleted = FALSE;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            DriverEntity driverEntity = null;
            if (resultSet.next()) {
                driverEntity = parseDriverFromResultSet(resultSet);
            }
            logger.info("Got driver by login: {}", login);
            return Optional.ofNullable(driverEntity);
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get driver by login: %s", login) + "{}",
                    e.getMessage());
            throw new RuntimeException("Couldn't get driver by login " + login, e);
        }
    }
}
