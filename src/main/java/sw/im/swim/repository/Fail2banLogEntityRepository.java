package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.entity.Fail2banLogEntity;

@Repository
public interface Fail2banLogEntityRepository extends JpaRepository<Fail2banLogEntity, Long> {
}