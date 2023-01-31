package infopolus.test.demo.service.mapper;

import infopolus.test.demo.controller.dto.CarResponseDto;
import infopolus.test.demo.controller.dto.DriverResponseDto;
import infopolus.test.demo.repository.entity.DriverEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DriverMapper {
    public DriverResponseDto mapDriverEntityToDriverResponseDto(DriverEntity driverEntity, List<CarResponseDto> carResponseDtoList) {
        return new DriverResponseDto(driverEntity.getName(),
                driverEntity.getLogin(),
                driverEntity.getPassword(),
                driverEntity.getLicenseNumber(),
                carResponseDtoList);
    }

    public DriverResponseDto mapDriverEntityToDriverResponseDto(DriverEntity driverEntity) {
        return new DriverResponseDto(driverEntity.getName(),
                driverEntity.getLogin(),
                driverEntity.getPassword(),
                driverEntity.getLicenseNumber());
    }

    public List<DriverResponseDto> mapDriverEntityListToDriverResponseDtoList(List<DriverEntity> driverEntityList) {
        List<DriverResponseDto> driverResponseDtoList = new ArrayList<>();
        for (DriverEntity driverEntity : driverEntityList) {
            driverResponseDtoList.add(new DriverResponseDto(driverEntity.getName(),
                    driverEntity.getLogin(),
                    driverEntity.getPassword(),
                    driverEntity.getLicenseNumber()));
        }
        return driverResponseDtoList;
    }
}
