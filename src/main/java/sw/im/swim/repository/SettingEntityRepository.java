package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.SettingEntity;

public interface SettingEntityRepository extends JpaRepository<SettingEntity, Long> {
}