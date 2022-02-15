package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.entity.DatabaseServerEntity;

import java.util.List;

/**
 * <PRE>
 *     1. left join fetch
 * </PRE>
 */
@Repository
public interface DatabaseServerEntityRepository extends JpaRepository<DatabaseServerEntity, Long> {

    @Query("select di from db_server_info di " +
            " join fetch server_info si on di.serverInfoEntity.sid = si.sid " +
            "where di.deletedAt is null " +
            "order by si.ip asc , di.port asc , di.createdAt desc ")
    List<DatabaseServerEntity> getAll();

    @Query("select di from db_server_info di " +
            " join fetch server_info si on di.serverInfoEntity.sid = si.sid " +
            "where di.deletedAt is null and si.sid = :sid " +
            "order by si.ip asc , di.port asc , di.createdAt desc ")
    List<DatabaseServerEntity> getByServerInfo(@Param(value = "sid") Long sid);
}