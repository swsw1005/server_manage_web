package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.nginx.NginxServerEntity;

public interface NginxServerRepository extends JpaRepository<NginxServerEntity, String> {
}