package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.entity.DatabaseServerEntity;

import java.util.List;

@Repository
public interface DatabaseServerEntityRepository extends JpaRepository<DatabaseServerEntity, Long> {

    List<DatabaseServerEntity> findAllByDeletedAtIsNullOrderByIpAscPortAscDbTypeAsc();

}