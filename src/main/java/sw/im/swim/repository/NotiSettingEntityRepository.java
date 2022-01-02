package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.NotiSettingEntity;

public interface NotiSettingEntityRepository extends JpaRepository<NotiSettingEntity, Long> {
}