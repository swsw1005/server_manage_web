package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.Fail2banLogEntity;

//@Repository
public interface Fail2banLogEntityRepository extends JpaRepository<Fail2banLogEntity, Long> {
}