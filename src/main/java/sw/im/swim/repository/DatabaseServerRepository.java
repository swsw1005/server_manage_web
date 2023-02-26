package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.database.DatabaseServerEntity;

public interface DatabaseServerRepository extends JpaRepository<DatabaseServerEntity, String> {
}