package lk.ijse.GreenShadowProject.entity;

import jakarta.persistence.*;

import lk.ijse.GreenShadowProject.util.Enum.EquipmentTypes;
import lk.ijse.GreenShadowProject.util.Enum.Status;
import lombok.Data;

@Entity
@Data
@Table(name = "equipment")
public class Equipment {
    @Id
    private String equipmentId;

    private String name;

    @Enumerated(EnumType.STRING)
    private EquipmentTypes type;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int equntity;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff assignedStaff;

    @ManyToOne
    @JoinColumn(name = "field_code")
    private Field assignedField;

//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =  "equpment")
//    private List<CropDetails> cropDetails=new ArrayList<>();




}
