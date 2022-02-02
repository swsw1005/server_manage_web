package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.bean.entity.NginxPolicyEntity;
import sw.im.swim.bean.entity.NginxPolicyServerEntity;
import sw.im.swim.bean.entity.NginxServerEntity;
import sw.im.swim.repository.NginxPolicyEntityRepository;
import sw.im.swim.repository.NginxPolicyServerEntityRepository;
import sw.im.swim.util.date.DateFormatUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Service
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
@RequiredArgsConstructor
public class NginxPolicyService {

    private final ModelMapper modelMapper;

    private final NginxPolicyEntityRepository nginxPolicyEntityRepository;

    private final NginxPolicyServerEntityRepository nginxPolicyServerEntityRepository;

    public NginxPolicyEntityDto insertNew() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
        final String tempName = "temp_name_" + DateFormatUtil.DATE_FORMAT_yyyyMMdd_HHmmss.format(cal.getTime());

        NginxPolicyEntity entity = NginxPolicyEntity.builder()
                .name(tempName)
                .build();

        NginxPolicyEntity entity_ = nginxPolicyEntityRepository.save(entity);
        return modelMapper.map(entity_, NginxPolicyEntityDto.class);
    }

    public void addNginxServer(final long policySid, final long nginxServerSid, final boolean isAdd) throws Exception {
        try {
            NginxPolicyEntity entity = nginxPolicyEntityRepository.getById(policySid);

            NginxPolicyServerEntity nginxPolicyServerEntity = NginxPolicyServerEntity.builder()
                    .nginxPolicyEntity(new NginxPolicyEntity(policySid))
                    .nginxServerEntity(new NginxServerEntity(nginxServerSid))
                    .build();

            NginxPolicyServerEntity nginxPolicyServerEntity_ = nginxPolicyServerEntityRepository.save(nginxPolicyServerEntity);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public void delete(final long policySid) throws Exception {
        try {
            NginxPolicyEntity entity = nginxPolicyEntityRepository.getById(policySid);
            entity.delete();
            nginxPolicyEntityRepository.save(entity);

            nginxPolicyServerEntityRepository.deleteAllByNginxPolicyEntityEquals(policySid);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<NginxPolicyEntityDto> getAll() {
        List<NginxPolicyEntity> list = nginxPolicyEntityRepository.getAllByDeletedAtIsNull();
        List<NginxPolicyEntityDto> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(modelMapper.map(list.get(i), NginxPolicyEntityDto.class));
        }
        return result;
    }

    public NginxPolicyEntityDto getAll(final long sid) throws Exception {

        try {
            NginxPolicyEntity entity = nginxPolicyEntityRepository.getById(sid);
            return modelMapper.map(entity, NginxPolicyEntityDto.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
}
