package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sw.im.swim.bean.entity.NginxPolicyEntity;

import java.util.List;

public interface NginxPolicyEntityRepository extends JpaRepository<NginxPolicyEntity, Long> {

    @Query("select n from nginx_policy_info n where n.deletedAt is null")
    List<NginxPolicyEntity> getAllByDeletedAtIsNull();

}