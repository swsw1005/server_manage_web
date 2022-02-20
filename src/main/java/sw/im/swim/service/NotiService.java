package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sw.im.swim.bean.dto.NotiEntityDto;
import sw.im.swim.bean.dto.NotiEntityDto;
import sw.im.swim.bean.entity.DatabaseServerEntity;
import sw.im.swim.bean.entity.NotiEntity;
import sw.im.swim.bean.entity.NotiEntity;
import sw.im.swim.bean.entity.WebServerEntity;
import sw.im.swim.bean.enums.NotiType;
import sw.im.swim.repository.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotiService {

    private final NotiEntityRepository notiEntityRepository;

    private final ModelMapper modelMapper;

    public List<NotiEntityDto> getAll() {
        List<NotiEntity> list = notiEntityRepository.findAll();

        List<NotiEntityDto> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            result.add(modelMapper.map(list.get(i), NotiEntityDto.class));
        }
        return result;
    }

    public NotiEntityDto insertNew(String name, String column1, String column2, NotiType notiType) throws Exception {

        try {
            NotiEntity entity = NotiEntity.builder()
                    .name(name)
                    .column1(column1)
                    .column2(column2)
                    .notiType(notiType)
                    .build();
            NotiEntity entity_ = notiEntityRepository.save(entity);
            entity_.getCreatedAt();
            return modelMapper.map(entity_, NotiEntityDto.class);
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
    public void toggle(final long sid) throws Exception {
        try {
            NotiEntity entity = notiEntityRepository.getById(sid);
            entity.setActive(!entity.getActive());
            notiEntityRepository.save(entity);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception();
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
            NotiEntity entity = notiEntityRepository.getById(sid);
            notiEntityRepository.delete(entity);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception();
        }
    }

}
