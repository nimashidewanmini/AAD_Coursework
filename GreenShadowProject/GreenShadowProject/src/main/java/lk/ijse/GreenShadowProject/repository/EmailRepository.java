package lk.ijse.GreenShadowProject.repository;


import lk.ijse.GreenShadowProject.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email,Long> {
}
