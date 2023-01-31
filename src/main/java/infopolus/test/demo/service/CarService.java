package infopolus.test.demo.service;

import infopolus.test.demo.repository.entity.CarEntity;

import java.util.List;

public interface CarService {
    CarEntity createCar(CarEntity carEntity);

    CarEntity getCarById(Long id);

    List<CarEntity> getAllCars();

    CarEntity updateCar(CarEntity carEntity);

    List<CarEntity> getAllCarsByDriverId(Long id);

    boolean deleteCarById(Long id);
}
