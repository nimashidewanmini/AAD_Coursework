package lk.ijse.GreenShadowProject.repository;


import lk.ijse.GreenShadowProject.entity.MonitoringLogService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonitorLogRepository extends JpaRepository<MonitoringLogService,String> {

    Boolean existsByLogCode(String monitoringLogId);

    Optional<MonitoringLogService> findByLogCode(String logCode);

    Optional<MonitoringLogService> findTopByOrderByLogCodeDesc();
}
