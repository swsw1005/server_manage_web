package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.bean.dto.WebServerEntityDto;
import sw.im.swim.bean.entity.DomainEntity;
import sw.im.swim.bean.entity.WebServerEntity;
import sw.im.swim.repository.WebServerEntityRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebServerService {

    private final WebServerEntityRepository webServerEntityRepository;

    private final ModelMapper modelMapper;

    public List<WebServerEntityDto> getAll() {
        List<WebServerEntity> list = webServerEntityRepository.getAllByDeletedAtIsNullOrderByIpAscPortAscHttpsAscCreatedAtDesc();
        List<WebServerEntityDto> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            result.add(modelMapper.map(list.get(i), WebServerEntityDto.class));
        }
        return result;
    }

    public WebServerEntity insertNew(String name, boolean https, String ip, Integer port) throws Exception {

        try {
            WebServerEntity entity = WebServerEntity.builder()
                    .name(name)
                    .https(https)
                    .ip(ip)
                    .port(port)
                    .build();
            WebServerEntity entity_ = webServerEntityRepository.save(entity);
            entity_.getCreatedAt();
            return entity_;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public boolean delete(final long sid) {
        try {
            WebServerEntity entity = webServerEntityRepository.getById(sid);
            entity.delete();
            webServerEntityRepository.save(entity);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }


}
