package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.entity.NginxServerEntity;

import java.util.Calendar;
import java.util.List;

@Repository
public interface NginxServerEntityRepository extends JpaRepository<NginxServerEntity, Long> {

    @Query(
            "select nsi from nginx_server_info nsi " +
                    "left join fetch web_server_info wse " +
                    "left join fetch domain_info di " +
                    "left join fetch favicon_info fi " +
                    "where nsi.deletedAt is null "
    )
    List<NginxServerEntity> getAll();

    @Query(
            "select nsi from nginx_server_info nsi " +
                    " left join fetch web_server_info wse " +
                    " left join fetch domain_info di " +
                    " left join fetch favicon_info fi " +
                    " where nsi.deletedAt is null " +
                    " and wse.sid = :sid"
    )
    List<NginxServerEntity> getAllByWebServer(@Param("sid") long sid);

//    List<NginxServerEntity> list = queryFactory.selectFrom(qNginxServerEntity)
//            .leftJoin(qNginxServerEntity.webServerEntity, qWebServerEntity).fetchJoin()
//            .leftJoin(qNginxServerEntity.domainEntity, qDomainEntity).fetchJoin()
//            .leftJoin(qNginxServerEntity.faviconEntity, qFaviconEntity).fetchJoin()
//            .where(qNginxServerEntity.deletedAt.isNull())
//            .fetch();

}