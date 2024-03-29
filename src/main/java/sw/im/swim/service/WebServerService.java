package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.bean.dto.WebServerEntityDto;
import sw.im.swim.bean.entity.DomainEntity;
import sw.im.swim.bean.entity.NginxServerEntity;
import sw.im.swim.bean.entity.ServerInfoEntity;
import sw.im.swim.bean.entity.WebServerEntity;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.repository.NginxPolicyServerEntityRepository;
import sw.im.swim.repository.NginxServerEntityRepository;
import sw.im.swim.repository.ServerInfoEntityRepository;
import sw.im.swim.repository.WebServerEntityRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WebServerService {

    private final WebServerEntityRepository webServerEntityRepository;

    private final NginxServerEntityRepository nginxServerEntityRepository;

    private final NginxPolicyServerEntityRepository nginxPolicyServerEntityRepository;

    private final ServerInfoEntityRepository serverInfoEntityRepository;

    private final ModelMapper modelMapper;

    private final NginxServerService nginxServerService;

    public List<WebServerEntityDto> getAll() {
        List<WebServerEntity> list = webServerEntityRepository.getAll();
        List<WebServerEntityDto> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            result.add(modelMapper.map(list.get(i), WebServerEntityDto.class));
        }
        return result;
    }

    public WebServerEntityDto insertNew(String name, boolean https, Integer port, Long serverSid, String healthCheckUrl) throws Exception {

        try {
            ServerInfoEntity server = serverInfoEntityRepository.getById(serverSid);

            WebServerEntity entity = WebServerEntity.builder()
                    .serverInfoEntity(server)
                    .name(name)
                    .https(https)
                    .port(port)
                    .healthCheckUrl(healthCheckUrl)
                    .build();
            WebServerEntity entity_ = webServerEntityRepository.save(entity);
            entity_.getCreatedAt();
            return modelMapper.map(entity_, WebServerEntityDto.class);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    /**
     * <PRE>
     * 웹서버 삭제
     * -> 웹서버 엮인 nginx서버 삭제
     * -> 해당 nginx-policy에서 삭제
     * </PRE>
     *
     * @param sid
     * @return
     */
    public boolean delete(final long sid) {
        try {
            WebServerEntity entity = webServerEntityRepository.getById(sid);
            entity.delete();
            webServerEntityRepository.save(entity);

            // 1. nginx server list get
            List<NginxServerEntity> nginxServerList = nginxServerEntityRepository.getAllByWebServer(sid);
            for (int i = 0; i < nginxServerList.size(); i++) {
                NginxServerEntity tempNginxServerEntity = nginxServerList.get(i);
                // nginxServerSidSet.add(tempNginxServerEntity.getSid());
                nginxServerService.delete(tempNginxServerEntity.getSid());
            }

            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }


}
