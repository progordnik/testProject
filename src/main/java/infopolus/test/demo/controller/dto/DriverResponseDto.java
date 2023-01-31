package infopolus.test.demo.controller.dto;

import infopolus.test.demo.repository.entity.CarEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
public class DriverResponseDto {
    private Long id;
    private String name;
    private String licenseNumber;
    private String login;
    private String password;
    private List<CarResponseDto> carResponseDtoList;

    public DriverResponseDto(String name, String licenseNumber, String login, String password) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.login = login;
        this.password = password;
    }
    public DriverResponseDto(String name, String licenseNumber, String login, String password, List<CarResponseDto> carResponseDtoList) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.login = login;
        this.password = password;
        this.carResponseDtoList = carResponseDtoList;
    }
}
