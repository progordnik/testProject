package infopolus.test.demo.repository.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CarEntity {
    private Long id;

    private Long driverId;
    private String model;

    private String number;

    public CarEntity(String model, String number, Long driverId) {
        this.model = model;
        this.number = number;
        this.driverId = driverId;
    }

}
