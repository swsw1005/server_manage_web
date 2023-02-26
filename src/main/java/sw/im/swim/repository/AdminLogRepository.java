package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.entity.admin.AdminLogEntity;

import java.util.List;

@Repository
public interface AdminLogRepository extends JpaRepository<AdminLogEntity, Long> {

    @Query("select al from admin_log al order by al.createdAt desc")
    List<AdminLogEntity> allLog();
}