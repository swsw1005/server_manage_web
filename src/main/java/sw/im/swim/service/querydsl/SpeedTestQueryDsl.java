package sw.im.swim.service.querydsl;

import com.google.gson.Gson;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.dto.SpeedTestResultDto;
import sw.im.swim.bean.entity.QSpeedTestResultEntity;
import sw.im.swim.bean.entity.SpeedTestResultEntity;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SpeedTestQueryDsl {

    private final JPAQueryFactory queryFactory;

    private final QSpeedTestResultEntity qSpeedTestResultEntity = QSpeedTestResultEntity.speedTestResultEntity;

    public List<String> getCountryList() {
        List<String> list = queryFactory.select(qSpeedTestResultEntity.server_country)
                .from(qSpeedTestResultEntity)
                .groupBy(qSpeedTestResultEntity.server_country)
                .fetch();
        return list;
    }

    public List<String> getHostList() {
        List<String> list = queryFactory.select(qSpeedTestResultEntity.server_host)
                .from(qSpeedTestResultEntity)
                .groupBy(qSpeedTestResultEntity.server_host)
                .fetch();
        return list;
    }

    public List<String> getNameList() {
        List<String> list = queryFactory.select(qSpeedTestResultEntity.server_name)
                .from(qSpeedTestResultEntity)
                .groupBy(qSpeedTestResultEntity.server_name)
                .fetch();
        return list;
    }


    public List<SpeedTestResultEntity> getListByLimitAndSearch(SpeedTestResultDto speedTestResultDto, Pageable pageable) {

        List<SpeedTestResultEntity> list =
                queryFactory.selectFrom(qSpeedTestResultEntity)
                        .where(
                                countryEq(speedTestResultDto),
                                hostEq(speedTestResultDto),
                                nameEq(speedTestResultDto)
                        )
                        .orderBy(qSpeedTestResultEntity.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        List<Long> countQuery =
                queryFactory.select(qSpeedTestResultEntity.sid)
                        .from(qSpeedTestResultEntity)
                        .where(
                                countryEq(speedTestResultDto),
                                hostEq(speedTestResultDto),
                                nameEq(speedTestResultDto)
                        )
                        .fetch();

        return list;
    }


    private BooleanExpression countryEq(SpeedTestResultDto speedTestResultDto) {
        try {
            String val = speedTestResultDto.getServer_country();
            val.length();
            return qSpeedTestResultEntity.server_country.eq(val);
        } catch (Exception e) {
            return null;
        }
    }

    private BooleanExpression hostEq(SpeedTestResultDto speedTestResultDto) {
        try {
            String val = speedTestResultDto.getServer_host();
            val.length();
            return qSpeedTestResultEntity.server_host.eq(val);
        } catch (Exception e) {
            return null;
        }
    }

    private BooleanExpression nameEq(SpeedTestResultDto speedTestResultDto) {
        try {
            String val = speedTestResultDto.getServer_name();
            val.length();
            return qSpeedTestResultEntity.server_name.eq(val);
        } catch (Exception e) {
            return null;
        }
    }


}