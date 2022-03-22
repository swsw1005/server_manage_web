package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.SpeedTestClientEntity;

public interface SpeedTestClientEntityRepository extends JpaRepository<SpeedTestClientEntity, Long> {
}