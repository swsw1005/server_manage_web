package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.AdminEntity;
import sw.im.swim.bean.entity.AdminLogEntity;

import java.util.List;

public interface AdminLogRepository extends JpaRepository<AdminLogEntity, Long> {
    List<AdminLogEntity> findAllBySidIsNotNullOrderByCreatedAtDesc();
}