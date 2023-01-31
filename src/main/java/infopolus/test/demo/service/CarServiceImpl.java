package infopolus.test.demo.service;

import infopolus.test.demo.repository.CarRepository;
import infopolus.test.demo.repository.entity.CarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarEntity createCar(CarEntity carEntity) {
        return carRepository.createCar(carEntity);
    }

    @Override
    public CarEntity getCarById(Long id) {
        return carRepository.getCarById(id).get();
    }

    @Override
    public List<CarEntity> getAllCars() {
        return carRepository.getAllCars();
    }

    @Override
    public CarEntity updateCar(CarEntity carEntity) {
        return carRepository.updateCar(carEntity);
    }

    @Override
    public List<CarEntity> getAllCarsByDriverId(Long id) {
        return carRepository.getAllCarsByDriverId(id);
    }

    @Override
    public boolean deleteCarById(Long id) {
        return carRepository.deleteCar(id);
    }
}
