package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sw.im.swim.bean.entity.NginxPolicyServerEntity;

import java.util.List;

public interface NginxPolicyServerEntityRepository extends JpaRepository<NginxPolicyServerEntity, Long> {

    @Modifying
    @Query("delete from nginx_policy_server_join n where n.nginxPolicyEntity.sid = :sid")
    void deleteAllByNginxPolicyEntityEquals(@Param("sid") long sid);

    @Modifying
    @Query("delete from nginx_policy_server_join n where n.nginxServerEntity.sid = :sid")
    void deleteAllByNginxServerEntityEquals(@Param("sid") long sid);

    @Modifying
    @Query("delete from nginx_policy_server_join n where n.nginxPolicyEntity = :policySid and n.nginxServerEntity = :serverSid")
    void delete(@Param("policySid") long policySid, @Param("serverSid") long serverSid);

//    @Query("select npsj, nsi, npi from nginx_policy_server_join npsj " +
//            " left join fetch nginx_server_info nsi " +
//            " left join fetch nginx_policy_info npi " +
//            " where npi = :policySid ")

    @Query("select npsj.nginxServerEntity.sid from nginx_policy_server_join npsj " +
            " where npsj.nginxPolicyEntity.sid = ?1")
    List<Long> getNginxServers(long policySid);
}