package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.AdminEntity;

public interface AdminEntityRepository extends JpaRepository<AdminEntity, Long> {
}