package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.entity.AdminSettingEntity;

@Repository
public interface AdminSettingEntityRepository extends JpaRepository<AdminSettingEntity, Long> {

    @Query("select a from admin_setting a where a.key = :key")
    AdminSettingEntity findByKeyEquals(@Param(value = "key") String key);

}