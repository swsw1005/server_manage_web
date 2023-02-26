package sw.im.swim.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sw.im.swim.bean.dto.Fail2banLogEntityDto;
import sw.im.swim.bean.entity.fail2ban.Fail2banLogEntity;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.exception.TokenException;
import sw.im.swim.repository.Fail2banLogEntityRepository;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;
import sw.im.swim.worker.noti.NotiProducer;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional(rollbackOn = Exception.class, value = Transactional.TxType.REQUIRED)
@RequiredArgsConstructor
public class Fail2banLogService {

    private final Fail2banLogEntityRepository fail2banLogEntityRepository;

    private final ModelMapper modelMapper;

//    public Fail2banLogService(@NonNull Fail2banLogEntityRepository fail2banLogEntityRepository, @NonNull ModelMapper modelMapper) {
//
//        log.error("\n\n\n\t 갸악 \n\n");
//        log.error("fail2banLogEntityRepository    " + fail2banLogEntityRepository);
//        log.error("modelMapper    " + modelMapper);
//
//        this.fail2banLogEntityRepository = fail2banLogEntityRepository;
//        this.modelMapper = modelMapper;
//
//        log.error("fail2banLogEntityRepository    " + this.fail2banLogEntityRepository);
//        log.error("modelMapper    " + this.modelMapper);
//
//    }

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

            log.error("fail2banLogEntityRepository   " + fail2banLogEntityRepository);
            log.error("this.fail2banLogEntityRepository   " + this.fail2banLogEntityRepository);

            Fail2banLogEntity a = this.fail2banLogEntityRepository.save(entity);

            String msg = " [ " + dto.getCountry() + " ] " + " | " + dto.getIp() + " | " + dto.getJailType() + " | " + dto.getJobType() + " | " + dto.getServer();

            log.debug(msg);

            NotiProducer notiProducer = new NotiProducer(msg, AdminLogType.FAIL2BAN);
            ThreadWorkerPoolContext.getInstance().NOTI_WORKER.execute(notiProducer);

            return modelMapper.map(a, Fail2banLogEntityDto.class);
        } catch (Exception e) {
            throw e;
        }
    }


}
