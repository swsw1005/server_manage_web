package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.entity.FaviconEntity;

@Repository
public interface FaviconEntityRepository extends JpaRepository<FaviconEntity, Long> {
}