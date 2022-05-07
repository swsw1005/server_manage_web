package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sw.im.swim.bean.entity.ServerInfoEntity;

import java.util.List;

public interface ServerInfoEntityRepository extends JpaRepository<ServerInfoEntity, Long> {

    @Query("select si from server_info si where si.deletedAt is null order by si.ip asc ")
    List<ServerInfoEntity> findAll();

}