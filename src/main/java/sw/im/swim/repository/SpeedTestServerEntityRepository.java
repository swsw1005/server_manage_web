package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.speedtest.SpeedTestServerEntity;

public interface SpeedTestServerEntityRepository extends JpaRepository<SpeedTestServerEntity, Long> {
}