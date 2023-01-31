package infopolus.test.demo.controller;

import infopolus.test.demo.repository.entity.CarEntity;
import infopolus.test.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @PostMapping("/addCar")
    public CarEntity createCar(@RequestBody CarEntity carEntity) {
        return carService.createCar(carEntity);
    }

    @GetMapping("/{id}")
    public CarEntity getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @GetMapping
    public List<CarEntity> getAllCars() {
        return carService.getAllCars();
    }

    @PutMapping
    public CarEntity updateCarById(@RequestBody CarEntity carEntity) {
        return carService.updateCar(carEntity);
    }

    @GetMapping("")
    public List<CarEntity> getAllCarsByDriverId(Long id) {
        return carService.getAllCarsByDriverId(id);
    }

    @DeleteMapping("{id}")
    public boolean deleteCarById(@PathVariable Long id) {
        return carService.deleteCarById(id);
    }
}
