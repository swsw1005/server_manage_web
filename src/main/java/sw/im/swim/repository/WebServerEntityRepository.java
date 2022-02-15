package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sw.im.swim.bean.entity.WebServerEntity;

import java.util.List;

public interface WebServerEntityRepository extends JpaRepository<WebServerEntity, Long> {

    @Query("select wsi from web_server_info wsi " +
            " join fetch server_info si on wsi.serverInfoEntity.sid = si.sid " +
            "where wsi.deletedAt is null " +
            "order by si.ip asc , wsi.port asc , wsi.createdAt desc ")
    List<WebServerEntity> getAll();

    @Query("select wsi from web_server_info wsi " +
            " join fetch server_info si on wsi.serverInfoEntity.sid = si.sid " +
            "where wsi.deletedAt is null and si.sid = :sid  " +
            "order by si.ip asc , wsi.port asc , wsi.createdAt desc ")
    List<WebServerEntity> getByServerInfo(@Param(value = "sid") Long sid);
}