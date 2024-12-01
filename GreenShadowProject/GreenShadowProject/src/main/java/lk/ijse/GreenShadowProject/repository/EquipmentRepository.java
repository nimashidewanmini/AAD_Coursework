package lk.ijse.GreenShadowProject.repository;


import lk.ijse.GreenShadowProject.entity.Equipment;
import lk.ijse.GreenShadowProject.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EquipmentRepository extends JpaRepository<Equipment,String> {
    Staff findByEquipmentId(String eqId); // Assuming staffId is a String


    Boolean existsByEquipmentId(String id);

    @Query(value = "SELECT equipment_id FROM equipment ORDER BY equipment_id DESC LIMIT 1", nativeQuery = true)
    String findLatestEqId();
}
