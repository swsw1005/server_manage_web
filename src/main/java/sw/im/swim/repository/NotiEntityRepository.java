package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.admin.NotiEntity;

public interface NotiEntityRepository extends JpaRepository<NotiEntity, Long> {

}