package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.nginx.NginxLocationEntity;

public interface NginxLocationRepository extends JpaRepository<NginxLocationEntity, String> {
}