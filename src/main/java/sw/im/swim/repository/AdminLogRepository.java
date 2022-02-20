package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.entity.AdminLogEntity;

import java.util.List;

@Repository
public interface AdminLogRepository extends JpaRepository<AdminLogEntity, Long> {
    List<AdminLogEntity> findAllBySidIsNotNullOrderByCreatedAtDesc();
}