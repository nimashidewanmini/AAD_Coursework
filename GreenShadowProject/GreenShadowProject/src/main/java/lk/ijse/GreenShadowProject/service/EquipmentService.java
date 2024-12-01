package lk.ijse.GreenShadowProject.service;


import lk.ijse.GreenShadowProject.dto.EquipmentDTO;

public interface EquipmentService {

    public EquipmentDTO saveEq(EquipmentDTO eDTO) ;



    public void updateEq(EquipmentDTO c) ;


    public void deleteEq(String sid);



    public String genarateNextEcode() ;

}
