package lk.ijse.GreenShadowProject.entity;

import jakarta.persistence.*;

import lk.ijse.GreenShadowProject.util.Enum.Fuel;
import lk.ijse.GreenShadowProject.util.Enum.Status;
import lk.ijse.GreenShadowProject.util.Enum.VTypes;
import lombok.Data;

@Data
@Entity
public class Vehicle {
    @Id
    private String vehicleCode;

    private String licensePlateNumber;

    @Enumerated(EnumType.STRING)
    private VTypes vehicleCategory;

    @Enumerated(EnumType.STRING)
    private Fuel fuelType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff allocatedStaff;

    private String remarks;




}
