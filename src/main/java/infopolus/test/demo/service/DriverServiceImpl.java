package infopolus.test.demo.service;

import infopolus.test.demo.controller.dto.DriverResponseDto;
import infopolus.test.demo.repository.CarRepository;
import infopolus.test.demo.repository.DriverRepository;
import infopolus.test.demo.repository.entity.DriverEntity;
import infopolus.test.demo.service.mapper.CarMapper;
import infopolus.test.demo.service.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverMapper driverMapper;

    private final CarMapper carMapper;
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;

    @Autowired
    public DriverServiceImpl(DriverMapper driverMapper, CarMapper carMapper, DriverRepository driverRepository, CarRepository carRepository) {
        this.driverMapper = driverMapper;
        this.carMapper = carMapper;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    @Override
    public DriverResponseDto createDriver(DriverEntity driverEntity) {
        return driverMapper.mapDriverEntityToDriverResponseDto(driverRepository.createDriver(driverEntity));
    }

    @Override
    public DriverResponseDto getDriverById(Long id) {
        return driverMapper.mapDriverEntityToDriverResponseDto(driverRepository.getDriverById(id).get(),
                carMapper.mapCarEntityListToCarResponseDtoList(carRepository.getAllCarsByDriverId(id)));
    }

    @Override
    public List<DriverResponseDto> getAllDrivers() {
        return driverMapper.mapDriverEntityListToDriverResponseDtoList(driverRepository.getAllDrivers());
    }

    @Override
    public DriverResponseDto updateDriver(DriverEntity driverEntity) {
        return driverMapper.mapDriverEntityToDriverResponseDto(driverRepository.updateDriver(driverEntity));
    }

    @Override
    public boolean deleteDriver(Long id) {
        return driverRepository.deleteDriverById(id);
    }

    @Override
    public DriverResponseDto findByLogin(String login) {
        return driverMapper.mapDriverEntityToDriverResponseDto(driverRepository.findDriverByLogin(login).get());
    }
}
