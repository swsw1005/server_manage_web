package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.DatabaseOwnerEntity;

public interface DatabaseOwnerEntityRepository extends JpaRepository<DatabaseOwnerEntity, Long> {
}