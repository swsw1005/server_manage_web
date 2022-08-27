package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.entity.NginxSettingEntity;

@Repository
public interface NginxSettingEntityRepository extends JpaRepository<NginxSettingEntity, Long> {

    @Query("select a from nginx_setting a where a.key = :key")
    NginxSettingEntity findByKeyEquals(@Param(value = "key") String key);

}