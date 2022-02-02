package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.entity.DomainEntity;

import java.util.List;

@Repository
public interface DomainEntityRepository extends JpaRepository<DomainEntity, Long> {

    @Query("select di from domain_info di order by di.domain asc")
    List<DomainEntity> getAllDomains();
}