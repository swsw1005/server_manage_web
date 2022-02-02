package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.*;
import sw.im.swim.bean.entity.NginxPolicyEntity;
import sw.im.swim.bean.entity.NginxPolicyServerEntity;
import sw.im.swim.bean.entity.NginxServerEntity;
import sw.im.swim.repository.NginxPolicyEntityRepository;
import sw.im.swim.repository.NginxPolicyServerEntityRepository;
import sw.im.swim.repository.NginxServerEntityRepository;
import sw.im.swim.worker.NginxWorker;
import sw.im.swim.worker.context.ThreadWorkderPoolContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
@RequiredArgsConstructor
public class NginxPolicyService {

    private final ModelMapper modelMapper;

    private final NginxPolicyEntityRepository nginxPolicyEntityRepository;

    private final NginxServerEntityRepository nginxServerEntityRepository;

    private final NginxPolicyServerEntityRepository nginxPolicyServerEntityRepository;

    private final NginxServerService nginxServerService;

    public NginxPolicyEntityDto insertNew(String name, int workerProcessed, int workerConnections) {

        NginxPolicyEntity entity = NginxPolicyEntity.builder()
                .name(name)
                .workerProcessed(workerProcessed)
                .workerConnections(workerConnections)
                .build();

        NginxPolicyEntity entity_ = nginxPolicyEntityRepository.save(entity);
        return modelMapper.map(entity_, NginxPolicyEntityDto.class);
    }

    public NginxPolicyEntityDto update(String name, int workerProcessed, int workerConnections, String nginxServerSidString, long sid) throws Exception {

        try {
            log.warn("000 == " + "시작");

            NginxPolicyEntity entity = nginxPolicyEntityRepository.getById(sid);

            log.warn("111 == " + entity.getSid() + "  " + entity.getName());

            if (name != null && name.length() > 2) {
                entity.setName(name);
            }

            entity.setWorkerProcessed(workerProcessed);
            entity.setWorkerConnections(workerConnections);

            NginxPolicyEntity entity_ = nginxPolicyEntityRepository.save(entity);

            log.warn("222 == update == " + entity.getSid() + "  " + entity.getName());

            nginxPolicyServerEntityRepository.deleteAllByNginxPolicyEntityEquals(sid);

            String[] nginxServerSidArr = nginxServerSidString.split(",");

            log.warn("333 ==   " + nginxServerSidString + "    " + nginxServerSidArr.length);

            List<NginxPolicyServerEntity> n_p_s_e_list = new ArrayList<>();
            List<NginxServerEntity> nginxServerEntities = new ArrayList<>();
            for (int i = 0; i < nginxServerSidArr.length; i++) {

                String tempNginxServerSid = nginxServerSidArr[i].trim();

                NginxServerEntity tempNginxServer = nginxServerEntityRepository.getById(Long.parseLong(tempNginxServerSid));

                tempNginxServer.getName();

                NginxPolicyServerEntity npse = NginxPolicyServerEntity.builder()
                        .nginxPolicyEntity(entity_)
                        .nginxServerEntity(tempNginxServer)
                        .build();

                nginxServerEntities.add(tempNginxServer);
                n_p_s_e_list.add(npse);
            }
            log.warn("444 ==  list.size()  " + n_p_s_e_list.size());

            nginxPolicyServerEntityRepository.saveAll(n_p_s_e_list);

            return modelMapper.map(entity_, NginxPolicyEntityDto.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    public void delete(final long policySid) throws Exception {
        try {
            NginxPolicyEntity entity = nginxPolicyEntityRepository.getById(policySid);
            entity.delete();
            nginxPolicyEntityRepository.save(entity);

            nginxPolicyServerEntityRepository.deleteAllByNginxPolicyEntityEquals(policySid);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<NginxPolicyEntityDto> getAll() {
        List<NginxPolicyEntity> list = nginxPolicyEntityRepository.getAllByDeletedAtIsNull();
        List<NginxPolicyEntityDto> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(modelMapper.map(list.get(i), NginxPolicyEntityDto.class));
        }
        return result;
    }

    public NginxPolicyEntityDto getAll(final long sid) throws Exception {

        try {
            NginxPolicyEntity entity = nginxPolicyEntityRepository.getById(sid);
            return modelMapper.map(entity, NginxPolicyEntityDto.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }


    public List<Long> getNginxServers(long parseLong) {
        List<Long> list = nginxPolicyServerEntityRepository.getNginxServers(parseLong);
        return list;
    }

    public NginxPolicyEntityDto get(long parseLong) {
        NginxPolicyEntity entity_ = nginxPolicyEntityRepository.getById(parseLong);
        return modelMapper.map(entity_, NginxPolicyEntityDto.class);
    }


    /**
     * <PRE>
     * nginx policy 적용
     * // TODO 개발중
     * </PRE>
     *
     * @param policySid
     * @return
     */
    public String ADJUST_NGINX_POLICY(long policySid) {
        String msg = "";
        try {

            log.error("============================================");
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
            }

            msg = "NO NGINX_POLICY";

            // 기본 nginx_policy 구하기
            NginxPolicyEntity nginxPolicyEntity = nginxPolicyEntityRepository.getById(policySid);
            nginxPolicyEntity.getName();

            /**
             * ------------------
             */
            NginxPolicyEntityDto policyEntityDto = modelMapper.map(nginxPolicyEntity, NginxPolicyEntityDto.class);


            log.error("============================================");
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
            }
            msg = "NO NGINX_SERVERS";

            List<Long> linkedNginxServerList = getNginxServers(policySid);
            List<NginxServerEntityDto> dtos = nginxServerService.getAll();
            Set<Long> nginxServerSet = new HashSet<>(linkedNginxServerList);


            log.error("============================================");
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
            }

            /**
             * -----------------------
             */
            List<NginxServerEntityDto> nginxServerEntityList = new ArrayList<>();
            for (int i = 0; i < dtos.size(); i++) {
                NginxServerEntityDto dto = dtos.get(i);
                if (nginxServerSet.contains(dto.getSid())) {
                    nginxServerEntityList.add(dto);
                }
            }

            HashSet<String> DOMAIN_SET = new HashSet<>();
            HashSet<String> FAVICON_SET = new HashSet<>();
            HashSet<String> IP_SET = new HashSet<>();

            // 설정 중복 없는지 체크
            for (int i = 0; i < nginxServerEntityList.size(); i++) {
                NginxServerEntityDto nginxServer = nginxServerEntityList.get(i);
                DomainEntityDto domainInfo = nginxServer.getDomainEntity();
                FaviconEntityDto faviconInfo = nginxServer.getFaviconEntity();
                WebServerEntityDto webServerInfo = nginxServer.getWebServerEntity();

                String domain_ = domainInfo.getDomain();
                String address_ = webServerInfo.getAddress();
                String favicon_ = faviconInfo.getPath();

                log.error("\n" + "\t favicon_ = " + favicon_ + "\t domain_ = " + domain_ + "\t address_ = " + address_);

                boolean isDomainExcist = DOMAIN_SET.contains(domain_);
                boolean isAddressExcist = IP_SET.contains(address_);

                log.error("\n" + "\t isDomainExcist = " + isDomainExcist + "\t isAddressExcist = " + isAddressExcist);

                if (isDomainExcist) {
                    msg = "domain conflict :: " + domain_;
                    throw new Exception();
                }

                if (isAddressExcist) {
                    msg = "ip + port conflict :: " + address_;
                    throw new Exception();
                }

                DOMAIN_SET.add(domain_);
                IP_SET.add(address_);
                FAVICON_SET.add(favicon_);
            } // for i end

            NginxWorker nginxWorker = new NginxWorker(policyEntityDto, nginxServerEntityList);
            ThreadWorkderPoolContext.getInstance().NGINX_WORKER.execute(nginxWorker);

        } catch (Exception e) {
        }
        return msg;
    }


}
