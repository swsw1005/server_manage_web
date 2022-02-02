package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sw.im.swim.bean.entity.NginxPolicyServerEntity;

public interface NginxPolicyServerEntityRepository extends JpaRepository<NginxPolicyServerEntity, Long> {

    @Modifying
    @Query("delete from nginx_policy_server_join n where n.nginxPolicyEntity = :sid")
    void deleteAllByNginxPolicyEntityEquals(@Param("sid") long sid);

    @Modifying
    @Query("delete from nginx_policy_server_join n where n.nginxServerEntity = :sid")
    void deleteAllByNginxServerEntityEquals(@Param("sid") long sid);

    @Modifying
    @Query("delete from nginx_policy_server_join n where n.nginxPolicyEntity = :policySid and n.nginxServerEntity = :serverSid")
    void delete(@Param("policySid") long policySid, @Param("serverSid") long serverSid);

}