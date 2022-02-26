package sw.im.swim.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sw.im.swim.bean.dto.*;
import sw.im.swim.bean.entity.DomainEntity;
import sw.im.swim.bean.entity.NginxPolicyEntity;
import sw.im.swim.bean.entity.NginxPolicyServerEntity;
import sw.im.swim.bean.entity.NginxServerEntity;
import sw.im.swim.repository.*;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;
import sw.im.swim.worker.nginx.NginxWorker;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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