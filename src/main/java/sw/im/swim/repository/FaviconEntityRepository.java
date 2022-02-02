package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.FaviconEntity;

public interface FaviconEntityRepository extends JpaRepository<FaviconEntity, Long> {
}