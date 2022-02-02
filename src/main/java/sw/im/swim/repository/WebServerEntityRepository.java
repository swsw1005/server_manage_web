package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.WebServerEntity;

import java.util.List;

public interface WebServerEntityRepository extends JpaRepository<WebServerEntity, Long> {

    List<WebServerEntity> getAllByDeletedAtIsNullOrderByIpAscPortAscHttpsAscCreatedAtDesc();

}