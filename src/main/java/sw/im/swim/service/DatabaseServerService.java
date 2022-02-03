package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sw.im.swim.bean.dto.DatabaseServerEntityDto;
import sw.im.swim.bean.entity.DatabaseServerEntity;
import sw.im.swim.repository.DatabaseServerEntityRepository;

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

    public List<DatabaseServerEntityDto> getAll() {

        try {
            List<DatabaseServerEntity> list = databaseServerEntityRepository.findAllByDeletedAtIsNullOrderByIpAscPortAscDbTypeAsc();

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

}
