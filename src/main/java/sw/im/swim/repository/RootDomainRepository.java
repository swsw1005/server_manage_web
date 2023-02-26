package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.domain.RootDomainEntity;

public interface RootDomainRepository extends JpaRepository<RootDomainEntity, String> {
}