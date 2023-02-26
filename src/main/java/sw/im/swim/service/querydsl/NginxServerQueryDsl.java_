package sw.im.swim.service.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.dto.SpeedTestResultDto;
import sw.im.swim.bean.entity.*;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class NginxServerQueryDsl {

    private final JPAQueryFactory queryFactory;

//    private final EntityManager entityManager;

    public List<NginxServerEntity> getAll() {

        QNginxServerEntity qNginxServerEntity = QNginxServerEntity.nginxServerEntity;
        QWebServerEntity qWebServerEntity = QWebServerEntity.webServerEntity;
        QDomainEntity qDomainEntity = QDomainEntity.domainEntity;
        QFaviconEntity qFaviconEntity = QFaviconEntity.faviconEntity;

        List<NginxServerEntity> list = queryFactory.selectFrom(qNginxServerEntity)
                .leftJoin(qNginxServerEntity.webServerEntity, qWebServerEntity).fetchJoin()
                .leftJoin(qNginxServerEntity.domainEntity, qDomainEntity).fetchJoin()
                .leftJoin(qNginxServerEntity.faviconEntity, qFaviconEntity).fetchJoin()
                .where(
                        qNginxServerEntity.deletedAt.isNull(),
                        qNginxServerEntity.webServerEntity.deletedAt.isNull()
                )
                .orderBy(qWebServerEntity.serverInfoEntity.ip.asc(), qWebServerEntity.port.asc())
                .fetch();

        return list;
    }


}