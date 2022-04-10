package sw.im.swim.service.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.dto.SpeedTestResultDto;
import sw.im.swim.bean.entity.*;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SpeedTestQueryDsl {

    private final JPAQueryFactory queryFactory;

    private final QSpeedTestResultEntity qSpeedTestResultEntity = QSpeedTestResultEntity.speedTestResultEntity;
    private final QSpeedTestClientEntity qSpeedTestClientEntity = QSpeedTestClientEntity.speedTestClientEntity;
    private final QSpeedTestServerEntity qSpeedTestServerEntity = QSpeedTestServerEntity.speedTestServerEntity;

    public List<SpeedTestResultEntity> getListByLimitAndSearch(SpeedTestResultDto speedTestResultDto, Pageable pageable) {

        List<SpeedTestResultEntity> list =
                queryFactory.selectFrom(qSpeedTestResultEntity)
                        .innerJoin(qSpeedTestResultEntity.speedTestClientEntity, qSpeedTestClientEntity)
                        .innerJoin(qSpeedTestResultEntity.speedTestServerEntity, qSpeedTestServerEntity)
                        .where(
                                countryEq(speedTestResultDto),
                                hostEq(speedTestResultDto),
                                nameEq(speedTestResultDto)
                        )
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        log.error("list.size   " + list.size());

        List<Long> countQuery =
                queryFactory.select(qSpeedTestResultEntity.sid)
                        .from(qSpeedTestResultEntity)
                        .innerJoin(qSpeedTestResultEntity.speedTestClientEntity, qSpeedTestClientEntity)
                        .innerJoin(qSpeedTestResultEntity.speedTestServerEntity, qSpeedTestServerEntity)
                        .where(
                                countryEq(speedTestResultDto),
                                hostEq(speedTestResultDto),
                                nameEq(speedTestResultDto)
                        )
                        .fetch();

        log.error("countQuery.size   " + countQuery.size());

        return list;
    }


    private BooleanExpression countryEq(SpeedTestResultDto speedTestResultDto) {
        try {
            String val = speedTestResultDto.getSpeedTestServerDto().getCountry();
            val.length();
            return qSpeedTestResultEntity.speedTestServerEntity.country.eq(val);
        } catch (Exception e) {
            return null;
        }
    }

    private BooleanExpression hostEq(SpeedTestResultDto speedTestResultDto) {
        try {
            String val = speedTestResultDto.getSpeedTestServerDto().getHost();
            val.length();
            return qSpeedTestResultEntity.speedTestServerEntity.host.eq(val);
        } catch (Exception e) {
            return null;
        }
    }

    private BooleanExpression nameEq(SpeedTestResultDto speedTestResultDto) {
        try {
            String val = speedTestResultDto.getSpeedTestServerDto().getName();
            val.length();
            return qSpeedTestResultEntity.speedTestServerEntity.name.eq(val);
        } catch (Exception e) {
            return null;
        }
    }


}