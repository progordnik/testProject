package infopolus.test.demo.repository;

import infopolus.test.demo.repository.entity.DriverEntity;

import java.util.List;
import java.util.Optional;

public interface DriverRepository {

    DriverEntity createDriver(DriverEntity driverEntity);

    Optional<DriverEntity> getDriverById(Long id);

    List<DriverEntity> getAllDrivers();

    DriverEntity updateDriver(DriverEntity driverEntity);

    boolean deleteDriverById(Long id);

    Optional<DriverEntity> findDriverByLogin(String login);
}
