package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.cache.ServerPortCacheEntity;

public interface ServerPortCacheRepository extends JpaRepository<ServerPortCacheEntity, String> {
}