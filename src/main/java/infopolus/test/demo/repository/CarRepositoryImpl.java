package infopolus.test.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import infopolus.test.demo.repository.entity.CarEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CarRepositoryImpl implements CarRepository {
    private static final Logger logger = LogManager.getLogger();
    private final DataSource dataSource;

    @Autowired
    public CarRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CarEntity createCar(CarEntity carEntity) {
        String insertQuery = "INSERT INTO car (model, number, driver_id)"
                + "VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, carEntity.getModel());
            preparedStatement.setString(2, carEntity.getNumber());
            preparedStatement.setLong(3, carEntity.getDriverId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                carEntity.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            logger.error(String.format("Can`t add car %s to DB", carEntity), e.getMessage());
            throw new RuntimeException("Can't create car " + carEntity, e);
        }
        logger.info("Car {} was added to DB", carEntity);
        return carEntity;
    }

    @Override
    public Optional<CarEntity> getCarById(Long id) {
        String selectQuery = "SELECT *"
                + "FROM car"
                + "WHERE id = ? AND deleted = false";
        CarEntity carEntity = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                carEntity = parseCarFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(String.format("Can`t get car %s by id", id), e.getMessage());
            throw new RuntimeException("Can't get car by id: " + id, e);
        }
        logger.info("Car {} was added to DB", carEntity);
        return Optional.ofNullable(carEntity);
    }

    @Override
    public List<CarEntity> getAllCars() {
        String selectQuery = "SELECT *"
                + "FROM car"
                + "WHERE deleted = false";
        List<CarEntity> carEntities = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carEntities.add(parseCarFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can`t get all cars from DB", e.getMessage());
            throw new RuntimeException("Can't get all cars", e);
        }
        logger.info("Got all cars from DB complete");
        return carEntities;
    }

    @Override
    public CarEntity updateCar(CarEntity carEntity) {
        String selectQuery = "UPDATE car SET model = ?, number = ?, driver_id = ? "
                + "WHERE id = ? and deleted = false";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, carEntity.getModel());
            preparedStatement.setString(2, carEntity.getNumber());
            preparedStatement.setLong(3, carEntity.getDriverId());
            preparedStatement.setLong(4, carEntity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Can`t update car %s", carEntity), e.getMessage());
            throw new RuntimeException("Can't update car " + carEntity, e);
        }
        logger.info("Update car {} complete", carEntity);
        return carEntity;
    }

    @Override
    public boolean deleteCar(Long id) {
        String selectQuery = "UPDATE car SET deleted = true "
                +"WHERE id = ? and deleted = false";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, id);
            logger.info("Delete car by id = {} success", id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(String.format("Can`t delete car by id = {}", id), e.getMessage());
            throw new RuntimeException("Can't delete car by id " + id, e);
        }
    }

    @Override
    public List<CarEntity> getAllCarsByDriverId(Long id) {
        String selectQuery = "SELECT *"
                + "FROM car"
                + "WHERE driver_id = ?";
        List<CarEntity> carEntities = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carEntities.add(parseCarFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can`t get all cars from DB", e.getMessage());
            throw new RuntimeException("Can't get all cars", e);
        }
        logger.info("Got all cars from DB complete");
        return carEntities;
    }

    private CarEntity parseCarFromResultSet(ResultSet resultSet) throws SQLException {
        long carId = resultSet.getLong("id");
        String model = resultSet.getString("model");
        String number = resultSet.getString("number");
        long driver_id = resultSet.getLong("driver_id");
        CarEntity carEntity = new CarEntity(model, number, driver_id);
        carEntity.setId(carId);
        return carEntity;
    }
}