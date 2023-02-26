package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.im.swim.bean.entity.domain.SubDomainEntity;

public interface SubDomainRepository extends JpaRepository<SubDomainEntity, String> {
}