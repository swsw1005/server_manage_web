package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.DatabaseEntity;

public interface DatabaseEntityRepository extends JpaRepository<DatabaseEntity, Long> {
}