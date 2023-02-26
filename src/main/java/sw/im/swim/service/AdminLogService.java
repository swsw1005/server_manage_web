package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.AdminLogEntityDto;
import sw.im.swim.bean.entity.admin.AdminLogEntity;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.repository.*;
import sw.im.swim.repository.AdminEntityRepository;
import sw.im.swim.repository.AdminSettingEntityRepository;
import sw.im.swim.repository.DatabaseServerEntityRepository;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;
import sw.im.swim.worker.noti.NotiProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminLogService {

    private final ModelMapper modelMapper;

    private final AdminLogRepository adminLogRepository;
    private final AdminRepository adminRepository;
    private final AdminSettingRepository adminSettingRepository;
    private final DatabaseServerRepository databaseServerRepository;
    private final Fail2banLogRepository fail2banLogRepository;
    private final FaviconRepository faviconRepository;
    private final NginxLocationRepository nginxLocationRepository;
    private final NginxServerRepository nginxServerRepository;

    public void insertLog(
            AdminLogType title,
            String message1,
            String message2) {

        if (message1 == null) {
            message1 = "null";
        }

        if (message2 == null) {
            message2 = "null";
        }

        String msg = title + " / " + message1 + " / " + message2;
        log.info("title / message1 / message2 || " + msg);

        AdminLogEntity adminLogEntity = AdminLogEntity.builder()
                .title(title)
                .message1(message1)
                .message2(message2)
                .build();
        adminLogRepository.save(adminLogEntity);

        NotiProducer notiProducer = new NotiProducer(msg, AdminLogType.STARTUP);

        ThreadWorkerPoolContext.getInstance().NOTI_WORKER.execute(notiProducer);

    }


    public List<AdminLogEntityDto> listAll() {
        return listAll(9999);
    }

    public List<AdminLogEntityDto> listAll(final int limit) {

        List<AdminLogEntityDto> result = new ArrayList<>();

        try {
            List<AdminLogEntity> list = adminLogRepository.allLog();

            for (int i = 0; i < list.size(); i++) {
                AdminLogEntity temp = list.get(i);
                result.add(modelMapper.map(temp, AdminLogEntityDto.class));
                if (i >= limit) {
                    break;
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    public void deleteByIds(Set<Long> ids) {
        adminLogRepository.deleteAllById(ids);
    }


}
