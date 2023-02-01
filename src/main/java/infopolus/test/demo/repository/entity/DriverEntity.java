package infopolus.test.demo.repository.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class DriverEntity {
    private Long id;
    private String name;
    private String licenseNumber;
    private String login;
    private String password;

    private List<CarEntity> carEntityList;


    public DriverEntity(String name, String licenseNumber, String login, String password) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.login = login;
        this.password = password;
    }
}