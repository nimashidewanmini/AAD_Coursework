package lk.ijse.GreenShadowProject.controller;

import lk.ijse.GreenShadowProject.dto.StaffDTO;
import lk.ijse.GreenShadowProject.exception.NotFoundException;
import lk.ijse.GreenShadowProject.service.impl.StaffServiceIMPL;
import lk.ijse.GreenShadowProject.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/staff")
@CrossOrigin
public class StaffController {


    @Autowired
    StaffServiceIMPL staffServiceIMPL;

    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

    @GetMapping
    public ResponseUtil getAllStaffs() {
        logger.info("getAllStaffs successfully");
        return new ResponseUtil(200, "OK", staffServiceIMPL.getAllCrops());
    }


    @PostMapping
    public ResponseEntity<StaffDTO> saveStaff(@RequestBody StaffDTO staffDTO) {
        try {
            StaffDTO savedStaff = staffServiceIMPL.saveStaff(staffDTO);
            logger.info("saveStaff successfully");
            return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("saveStaff error", e);
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<StaffDTO> updateStaff(@RequestBody StaffDTO staffDTO) {
        try {
            staffServiceIMPL.updateStaff(staffDTO);
            logger.info("updateStaff successfully");
            return new ResponseEntity<>(staffDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("updateStaff error", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping
    public  ResponseUtil deleteStaff(@RequestParam("sCode") String sCode){
        staffServiceIMPL.deleteStaff(sCode);
        logger.info("deleteStaff successfully");
        return new ResponseUtil(200,"Deleted",null);

    }


    @GetMapping("/nextId")
    @ResponseStatus(HttpStatus.ACCEPTED)
    String getNextStaffCode(){
        logger.info("getNextStaffCode successfully");
        return staffServiceIMPL.genarateNextStaffCode();
    }
}
