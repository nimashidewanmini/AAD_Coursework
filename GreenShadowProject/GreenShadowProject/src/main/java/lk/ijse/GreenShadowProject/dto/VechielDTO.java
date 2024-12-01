package lk.ijse.GreenShadowProject.dto;

import lk.ijse.GreenShadowProject.util.Enum.Fuel;
import lk.ijse.GreenShadowProject.util.Enum.Status;
import lk.ijse.GreenShadowProject.util.Enum.VTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VechielDTO {
    private String vehicleCode;
    private String licensePlateNumber;
    private VTypes vehicleCategory;
    private Fuel fuelType;
    private Status status;
    private String allocatedStaffId; // Only including staff ID, or could include other details as needed
    private String remarks;
}
