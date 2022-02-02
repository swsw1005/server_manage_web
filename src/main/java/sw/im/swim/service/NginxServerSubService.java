package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.bean.dto.FaviconEntityDto;
import sw.im.swim.bean.entity.DomainEntity;
import sw.im.swim.bean.entity.FaviconEntity;
import sw.im.swim.repository.DomainEntityRepository;
import sw.im.swim.repository.FaviconEntityRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NginxServerSubService {

    private final FaviconEntityRepository faviconEntityRepository;

    private final DomainEntityRepository domainEntityRepository;

//    private final WebServerEntityRepository webServerEntityRepository;

    private final ModelMapper modelMapper;

    public List<DomainEntityDto> getAllDomains() {
        List<DomainEntity> list = domainEntityRepository.getAllDomains();
        List<DomainEntityDto> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            result.add(modelMapper.map(list.get(i), DomainEntityDto.class));
        }
        return result;
    }

    public DomainEntity insertDomain(String domain) throws Exception {
        try {

            domain = domain.replace(" ", "");

            if (domain.length() > 4) {
            } else {
                throw new Exception();
            }

            DomainEntity entity = DomainEntity.builder()
                    .domain(domain)
                    .build();
            DomainEntity newEntity = domainEntityRepository.save(entity);
            return newEntity;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    public boolean deleteDomain(final long sid) {
        try {
            DomainEntity entity = domainEntityRepository.getById(sid);
            domainEntityRepository.delete(entity);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public List<FaviconEntityDto> getAllFavicons() {
        List<FaviconEntity> list = faviconEntityRepository.findAll();
        List<FaviconEntityDto> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            result.add(modelMapper.map(list.get(i), FaviconEntityDto.class));
        }
        return result;
    }

    public FaviconEntity insertFavicon(final String path, String description) throws Exception {
        try {

            if (description == null) {
                description = "";
            }

            if (path.length() > 8 && description.length() >= 0) {
            } else {
                throw new Exception();
            }

            FaviconEntity entity = FaviconEntity.builder()
                    .path(path)
                    .description(description)
                    .build();
            FaviconEntity newEntity = faviconEntityRepository.save(entity);
            return newEntity;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    public boolean deleteFavicon(final long sid) {
        try {
            FaviconEntity entity = faviconEntityRepository.getById(sid);
            faviconEntityRepository.delete(entity);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }


}
