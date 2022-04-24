package sw.im.swim.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.Fail2banLogEntityDto;
import sw.im.swim.bean.entity.Fail2banLogEntity;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.exception.TokenException;
import sw.im.swim.repository.Fail2banLogEntityRepository;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;
import sw.im.swim.worker.noti.NotiProducer;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Fail2banLogService {

    private final ModelMapper modelMapper;

    private final Fail2banLogEntityRepository fail2banLogEntityRepository;

    public final Fail2banLogEntityDto insertLog(Fail2banLogEntityDto dto) throws TokenException {
        try {

            if (!dto.getToken().equals(GeneralConfig.ADMIN_SETTING.getFAIL2BAN_TOKEN())) {
                throw new TokenException("token fail");
            }

            log.debug("fail2ban log : " + new Gson().toJson(dto));

            Fail2banLogEntity entity = Fail2banLogEntity.builder()
                    .country(dto.getCountry())
                    .ip(dto.getIp())
                    .jailType(dto.getJailType())
                    .jobType(dto.getJobType())
                    .server(dto.getServer())
                    .build();

            log.debug("fail2ban entity : " + new Gson().toJson(entity));

            Fail2banLogEntity a = fail2banLogEntityRepository.save(entity);

            String msg = " [ " + dto.getCountry() + " ] " + " | " + dto.getIp() + " | " + dto.getJailType() + " | " + dto.getJobType() + " | " + dto.getServer();

            NotiProducer notiProducer = new NotiProducer(msg, AdminLogType.FAIL2BAN);
            ThreadWorkerPoolContext.getInstance().NOTI_WORKER.execute(notiProducer);

            return modelMapper.map(a, Fail2banLogEntityDto.class);
        } catch (Exception e) {
            throw e;
        }
    }


}
