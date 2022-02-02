package sw.im.swim.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.bean.entity.*;
import sw.im.swim.bean.entity.NginxServerEntity;
import sw.im.swim.repository.NginxPolicyServerEntityRepository;
import sw.im.swim.repository.NginxServerEntityRepository;
import sw.im.swim.repository.NginxServerEntityRepository;
import sw.im.swim.service.querydsl.NginxServerQueryDsl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NginxServerService {

    private final NginxServerEntityRepository nginxServerEntityRepository;

    private final NginxPolicyServerEntityRepository nginxPolicyServerEntityRepository;

    private final NginxServerQueryDsl nginxServerQueryDsl;

    private final ModelMapper modelMapper;

    public List<NginxServerEntityDto> getAll() {
        List<NginxServerEntity> list = nginxServerQueryDsl.getAll();

        List<NginxServerEntityDto> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            result.add(modelMapper.map(list.get(i), NginxServerEntityDto.class));
        }
        return result;
    }

    public NginxServerEntity insertNew(String name, boolean seperateLog, long domainInfoSid, long faviconInfoSid, long webServerInfoSid) throws Exception {

        try {
            DomainEntity domainEntity = new DomainEntity(domainInfoSid);
            FaviconEntity faviconEntity = new FaviconEntity(faviconInfoSid);
            WebServerEntity webServerEntity = new WebServerEntity(webServerInfoSid);

            NginxServerEntity entity = NginxServerEntity.builder()
                    .name(name)
                    .seperateLog(seperateLog)
                    .domainEntity(domainEntity)
                    .faviconEntity(faviconEntity)
                    .webServerEntity(webServerEntity)
                    .build();
            NginxServerEntity entity_ = nginxServerEntityRepository.save(entity);
            entity_.getCreatedAt();
            return entity_;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    public void delete(final long sid) throws Exception {
        try {
            NginxServerEntity entity = nginxServerEntityRepository.getById(sid);
            entity.delete();
            nginxServerEntityRepository.save(entity);

            nginxPolicyServerEntityRepository.deleteAllByNginxServerEntityEquals(sid);
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
