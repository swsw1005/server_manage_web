package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sw.im.swim.bean.entity.NginxPolicyEntity;
import sw.im.swim.repository.NginxPolicyEntityRepository;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NginxPolicySubService {

    private final ModelMapper modelMapper;

    private final NginxPolicyEntityRepository nginxPolicyEntityRepository;

    public void updateLastCertTime() {

        try {
            NginxPolicyEntity nginxPolicyEntity = nginxPolicyEntityRepository.getAllByDeletedAtIsNull().get(0);
            nginxPolicyEntity.certUpdate();
            nginxPolicyEntityRepository.save(nginxPolicyEntity);
        } catch (Exception e) {
        }
    }
}