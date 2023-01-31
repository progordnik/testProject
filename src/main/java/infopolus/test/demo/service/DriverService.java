package infopolus.test.demo.service;

import infopolus.test.demo.controller.dto.DriverResponseDto;
import infopolus.test.demo.repository.entity.DriverEntity;

import java.util.List;

public interface DriverService {
    DriverResponseDto createDriver(DriverEntity driverEntity);

    DriverResponseDto getDriverById(Long id);

    List<DriverResponseDto> getAllDrivers();

    DriverResponseDto updateDriver(DriverEntity driverEntity);

    boolean deleteDriver(Long id);

    DriverResponseDto findByLogin(String login);
}
