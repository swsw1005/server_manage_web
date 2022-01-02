package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sw.im.swim.bean.entity.AdminEntity;

import java.util.List;
import java.util.Optional;

public interface AdminEntityRepository extends JpaRepository<AdminEntity, Long> {
    List<AdminEntity> findByDeletedAtIsNotNull();

}