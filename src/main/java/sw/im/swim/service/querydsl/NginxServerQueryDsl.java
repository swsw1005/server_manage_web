package sw.im.swim.service.querydsl;

import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.bean.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
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
                .where(qNginxServerEntity.deletedAt.isNull())
                .orderBy(qWebServerEntity.serverInfoEntity.ip.asc(), qWebServerEntity.port.asc())
                .fetch();

        return list;
    }
}