package lk.ijse.GreenShadowProject.service.impl;


import lk.ijse.GreenShadowProject.dto.CropDetailDTO;
import lk.ijse.GreenShadowProject.dto.FieldDTO;
import lk.ijse.GreenShadowProject.dto.MonitorlogDTO;
import lk.ijse.GreenShadowProject.entity.Crop;
import lk.ijse.GreenShadowProject.entity.CropDetails;
import lk.ijse.GreenShadowProject.entity.Field;
import lk.ijse.GreenShadowProject.entity.MonitoringLogService;
import lk.ijse.GreenShadowProject.exception.NotFoundException;
import lk.ijse.GreenShadowProject.repository.*;
import lk.ijse.GreenShadowProject.util.Enum.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MonitorlogServiceIMPL {

    @Autowired
    CropRepository cropRepo;

    @Autowired
    FieldRepository fieldRepo;

    @Autowired
    private MonitorLogRepository monitoringLogRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private CropDetailRepository cropDetailRepo;
    @Autowired
    private ModelMapper modelMapper;


    public void saveLog(MonitorlogDTO monitorlogDTO){
        monitorlogDTO.setLogCode(nextCode("LOG-"));

        MonitoringLogService savedLog =
                monitoringLogRepository.save(modelMapper.map(monitorlogDTO, MonitoringLogService.class));
        if(savedLog == null){
            System.out.println("Log not saved");
        }

    }


    public void updateLog(String id, MonitorlogDTO monitorlogDTO, FieldDTO fieldDTO){
        MonitoringLogService log = monitoringLogRepository.findByLogCode(id)
                .orElseThrow(() -> new NotFoundException("Log not found"));
        log.setLogDate(monitorlogDTO.getLogDate());
        log.setLogDetails(monitorlogDTO.getLogDetails());
        log.setRole(UserRole.valueOf(monitorlogDTO.getRole()));

        Field field = fieldRepo.findByFieldCode(fieldDTO.getFieldCode())
                .orElseThrow(() -> new NotFoundException("Field not found"));
        log.setField(field);

        monitoringLogRepository.save(modelMapper.map(log, MonitoringLogService.class));

    }



    public List<MonitorlogDTO> getAllMonitoringLogs() {
        List<MonitoringLogService> logs = monitoringLogRepository.findAll();

        return logs.stream().map(log -> {
            MonitorlogDTO dto = new MonitorlogDTO();
            dto.setLogCode(log.getLogCode());
            dto.setLogDate(LocalDate.parse(log.getLogDate().toString()));
            dto.setLogDetails(log.getLogDetails());
            dto.setRole(log.getRole().name());
            dto.setFieldCode(log.getField().getFieldCode());

            List<CropDetailDTO> cropDetailsDTOs = log.getCropDetails().stream().map(cropDetail -> {
                CropDetailDTO cropDetailDTO = new CropDetailDTO();
                cropDetailDTO.setLogCode(cropDetail.getLogCode());
                cropDetailDTO.setCropCode(cropDetail.getCrop_code());
                cropDetailDTO.setStaffId(cropDetail.getStaff_id());
                cropDetailDTO.setQuantity(cropDetail.getQuantity());
                cropDetailDTO.setMembersInStaff(cropDetail.getMembersInStaff());
                return cropDetailDTO;
            }).collect(Collectors.toList());

            dto.setCropDetails(cropDetailsDTOs);

            return dto;
        }).collect(Collectors.toList());
    }

    public List<CropDetailDTO> getAllCropsDetail() {
        return cropDetailRepo.findAll().stream()
                .map(crop -> modelMapper.map(crop, CropDetailDTO.class))
                .collect(Collectors.toList());
    }



    @Transactional(readOnly = true)
    public Crop findMostUsedCrop() {
        List<CropDetails> cropDetailsList = cropDetailRepo.findAll();

        Map<String, Integer> cropCountMap = new HashMap<>();

        for (CropDetails cropDetails : cropDetailsList) {
            String cropCode = cropDetails.getCrop_code();
            cropCountMap.put(cropCode, cropCountMap.getOrDefault(cropCode, 0) + cropDetails.getQuantity());
        }

        String mostUsedCropCode = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : cropCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostUsedCropCode = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        if (mostUsedCropCode != null) {
            return cropRepo.findById(mostUsedCropCode)
                    .orElseThrow(() -> new IllegalArgumentException("Most used crop not found"));
        }

        return null;
    }

    public String nextCode(String prefix) {
        long count = monitoringLogRepository.count();

        String nextInventoryCode = prefix + String.format("%03d", count + 1);
        return nextInventoryCode;
    }

}
