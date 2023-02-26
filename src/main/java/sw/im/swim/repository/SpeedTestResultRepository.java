package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.speedtest.SpeedTestResultEntity;

public interface SpeedTestResultRepository extends JpaRepository<SpeedTestResultEntity, String> {
}