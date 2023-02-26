package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.admin.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, String> {
}