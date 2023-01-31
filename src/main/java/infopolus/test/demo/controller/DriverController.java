package infopolus.test.demo.controller;

import infopolus.test.demo.controller.dto.DriverResponseDto;
import infopolus.test.demo.repository.entity.DriverEntity;
import infopolus.test.demo.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public DriverResponseDto createDriver(@RequestBody DriverEntity driverEntity) {
        return driverService.createDriver(driverEntity);
    }

    @GetMapping("{/id}")
    public DriverResponseDto getDriverById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }

    @GetMapping
    public List<DriverResponseDto> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @PutMapping
    public DriverResponseDto updateDriver(@RequestBody DriverEntity driverEntity) {
        return driverService.updateDriver(driverEntity);
    }

    @DeleteMapping("{/id}")
    public boolean deleteDriverById(@PathVariable Long id) {
        return driverService.deleteDriver(id);
    }

    @GetMapping("/by-login")
    public DriverResponseDto findDriverByLogin(@PathVariable String login) {
        return driverService.findByLogin(login);
    }
}
