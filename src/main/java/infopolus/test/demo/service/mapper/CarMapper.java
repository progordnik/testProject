package infopolus.test.demo.service.mapper;

import infopolus.test.demo.controller.dto.CarResponseDto;
import infopolus.test.demo.repository.entity.CarEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarMapper {
    public CarResponseDto mapCarEntityToCarResponseDto(CarEntity carEntity) {
        return new CarResponseDto(carEntity.getModel(), carEntity.getNumber(), carEntity.getDriverId());
    }

    public List<CarResponseDto> mapCarEntityListToCarResponseDtoList(List<CarEntity> carEntityList) {
        List<CarResponseDto> carResponseDtoList = new ArrayList<>();
        for (CarEntity carEntity : carEntityList) {
            carResponseDtoList.add(new CarResponseDto(carEntity.getModel(), carEntity.getNumber(), carEntity.getDriverId()));
        }
        return carResponseDtoList;
    }
}
