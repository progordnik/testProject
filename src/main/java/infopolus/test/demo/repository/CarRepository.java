package infopolus.test.demo.repository;

import infopolus.test.demo.repository.entity.CarEntity;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    public CarEntity createCar(CarEntity carEntity);
    Optional<CarEntity> getCarById(Long id);

    List<CarEntity> getAllCars();

    CarEntity updateCar(CarEntity carEntity);

    List<CarEntity> getAllCarsByDriverId(Long id);

    boolean deleteCar(Long id);
}
