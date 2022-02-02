package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sw.im.swim.bean.entity.NginxServerEntity;

import java.util.List;

public interface NginxServerEntityRepository extends JpaRepository<NginxServerEntity, Long> {

}