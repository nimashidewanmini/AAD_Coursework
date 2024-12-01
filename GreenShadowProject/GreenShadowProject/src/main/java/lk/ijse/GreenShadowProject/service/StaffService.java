package lk.ijse.GreenShadowProject.service;



import lk.ijse.GreenShadowProject.dto.StaffDTO;

import java.util.List;

public interface StaffService {
    public List<StaffDTO> getAllCrops() ;


    public StaffDTO saveStaff(StaffDTO sDTO) ;


    public void updateStaff(StaffDTO c) ;

    public void deleteStaff(String id) ;


    public StaffDTO searchStaff(String id) ;


    public String genarateNextStaffCode() ;
}
