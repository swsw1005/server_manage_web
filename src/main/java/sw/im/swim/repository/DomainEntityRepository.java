package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.DomainEntity;

public interface DomainEntityRepository extends JpaRepository<DomainEntity, Long> {
}