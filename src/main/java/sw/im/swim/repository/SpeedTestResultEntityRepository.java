package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.SpeedTestResultEntity;

public interface SpeedTestResultEntityRepository extends JpaRepository<SpeedTestResultEntity, Long> {
}