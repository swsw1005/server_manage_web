package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.nginx.NginxUpstreamEntity;

public interface NginxUpstreamRepository extends JpaRepository<NginxUpstreamEntity, String> {
}