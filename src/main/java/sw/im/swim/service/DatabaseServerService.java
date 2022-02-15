package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sw.im.swim.bean.dto.DatabaseServerEntityDto;
import sw.im.swim.bean.entity.DatabaseServerEntity;
import sw.im.swim.bean.entity.ServerInfoEntity;
import sw.im.swim.bean.entity.WebServerEntity;
import sw.im.swim.bean.enums.DbType;
import sw.im.swim.repository.DatabaseServerEntityRepository;
import sw.im.swim.repository.ServerInfoEntityRepository;
import sw.im.swim.repository.WebServerEntityRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DatabaseServerService {

    private final ModelMapper modelMapper;

    private final DatabaseServerEntityRepository databaseServerEntityRepository;

    private final ServerInfoEntityRepository serverInfoEntityRepository;

    private final WebServerEntityRepository webServerEntityRepository;

    public List<DatabaseServerEntityDto> getAll() {

        try {
            List<DatabaseServerEntity> list = databaseServerEntityRepository.getAll();

            try {
                List<ServerInfoEntity> serverList = serverInfoEntityRepository.findAll();
                ServerInfoEntity entity = serverList.get(0);

                List<WebServerEntity> webServerEntityList = webServerEntityRepository.getByServerInfo(entity.getSid());
                log.warn("webServerEntityList => " + webServerEntityList.size());
                List<DatabaseServerEntity> databaseServerEntityList = databaseServerEntityRepository.getByServerInfo(entity.getSid());
                log.warn("databaseServerEntityList => " + databaseServerEntityList.size());
            } catch (Exception e) {
            }



            List<DatabaseServerEntityDto> result = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                result.add(modelMapper.map(list.get(i), DatabaseServerEntityDto.class));
            }
            return result;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean delete(final long sid) {
        try {
            DatabaseServerEntity entity = databaseServerEntityRepository.getById(sid);
            entity.delete();
            databaseServerEntityRepository.save(entity);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public DatabaseServerEntityDto insertNew(String name, Integer port, String id, String password, DbType dbType, Long serverSid) throws Exception {

        try {
            ServerInfoEntity server = serverInfoEntityRepository.getById(serverSid);

            DatabaseServerEntity entity = DatabaseServerEntity.builder()
                    .serverInfoEntity(server)
                    .name(name)
                    .id(id)
                    .password(password)
                    .dbType(dbType)
                    .port(port)
                    .build();
            DatabaseServerEntity entity_ = databaseServerEntityRepository.save(entity);
            entity_.getCreatedAt();
            return modelMapper.map(entity_, DatabaseServerEntityDto.class);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
}
