package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.admin.AdminSettingEntity;

public interface AdminSettingRepository extends JpaRepository<AdminSettingEntity, String> {
}