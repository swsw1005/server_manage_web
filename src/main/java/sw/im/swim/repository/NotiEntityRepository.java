package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.NotiEntity;

public interface NotiEntityRepository extends JpaRepository<NotiEntity, Long> {

}