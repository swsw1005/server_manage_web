package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.admin.AdminLogEntity;

public interface AdminLogRepository extends JpaRepository<AdminLogEntity, String> {
}