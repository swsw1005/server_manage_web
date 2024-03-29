package sw.im.swim.service;

import kr.swim.util.enc.AesUtils;
import kr.swim.util.system.SystemInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.ServerInfoEntityDto;
import sw.im.swim.bean.entity.DatabaseServerEntity;
import sw.im.swim.bean.entity.ServerInfoEntity;
import sw.im.swim.bean.entity.WebServerEntity;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.repository.DatabaseServerEntityRepository;
import sw.im.swim.repository.NginxPolicyServerEntityRepository;
import sw.im.swim.repository.ServerInfoEntityRepository;
import sw.im.swim.repository.WebServerEntityRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerInfoService {

    private final ServerInfoEntityRepository serverInfoEntityRepository;
    private final WebServerEntityRepository webServerEntityRepository;
//    private final NginxServerEntityRepository nginxServerEntityRepository;
    private final DatabaseServerEntityRepository databaseServerEntityRepository;
    private final NginxPolicyServerEntityRepository nginxPolicyServerEntityRepository;

    private final NotiService notiService;

    private final ModelMapper modelMapper;

    public void sync() {
        List<ServerInfoEntityDto> list = getAll();
        SystemInfo systemInfo = GeneralConfig.SERVER_INFO;
        list.forEach(serverInfoEntityDto -> {
//            if (serverInfoEntityDto.getIp().equals(systemInfo.())) {
//                GeneralConfig.CURRENT_SERVER_INFO = serverInfoEntityDto;
//            }
        });

        notiService.getAll();
    }

    public List<ServerInfoEntityDto> getAll() {
        List<ServerInfoEntity> list = serverInfoEntityRepository.findAll();

        List<ServerInfoEntityDto> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            result.add(modelMapper.map(list.get(i), ServerInfoEntityDto.class));
        }
        return result;
    }

    public ServerInfoEntityDto insertNew(String name, String id, String password, String ip, Integer sshPort) throws Exception {

        try {
            final String encPassword = AesUtils.encrypt(password, GeneralConfig.ENC_KEY);

            ServerInfoEntity entity = ServerInfoEntity.builder()
                    .name(name)
                    .id(id)
                    .password(encPassword)
                    .ip(ip)
                    .sshPort(sshPort)
                    .build();
            ServerInfoEntity entity_ = serverInfoEntityRepository.save(entity);
            entity_.getCreatedAt();
            return modelMapper.map(entity_, ServerInfoEntityDto.class);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    /**
     * <PRE>
     * </PRE>
     *
     * @param sid
     * @return
     */
    public void delete(final long sid) throws Exception {
        try {
            ServerInfoEntity entity = serverInfoEntityRepository.getById(sid);
            entity.delete();
            serverInfoEntityRepository.save(entity);

            /**
             * 웹서버 삭제
             */
            List<WebServerEntity> webServerEntityList = webServerEntityRepository.getByServerInfo(entity.getSid());

            for (WebServerEntity webServerEntity : webServerEntityList) {
                webServerEntity.delete();
                webServerEntityRepository.save(webServerEntity);
                nginxPolicyServerEntityRepository.deleteAllByNginxServerEntityEquals(webServerEntity.getSid());
            }


            /**
             * DB서버 삭제
             */
            List<DatabaseServerEntity> databaseServerEntityList = databaseServerEntityRepository.getByServerInfo(entity.getSid());

            for (DatabaseServerEntity databaseServerEntity : databaseServerEntityList) {
                databaseServerEntity.delete();
                databaseServerEntityRepository.save(databaseServerEntity);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception();
        }
    }

    public ServerInfoEntityDto getBySid(long sid) throws Exception {
        try {
            ServerInfoEntity entity = serverInfoEntityRepository.getById(sid);
            ServerInfoEntityDto dto = modelMapper.map(entity, ServerInfoEntityDto.class);
            try {
                dto.setPassword(AesUtils.decrypt(dto.getPassword(), GeneralConfig.ENC_KEY));
            } catch (Exception e) {
            }
            return dto;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception();
        }
    }
}
