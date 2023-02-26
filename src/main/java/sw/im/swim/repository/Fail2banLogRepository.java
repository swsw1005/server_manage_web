package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.fail2ban.Fail2banLogEntity;

public interface Fail2banLogRepository extends JpaRepository<Fail2banLogEntity, String> {
}