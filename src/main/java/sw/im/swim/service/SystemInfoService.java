package sw.im.swim.service;

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
import sw.im.swim.repository.NginxServerEntityRepository;
import sw.im.swim.repository.ServerInfoEntityRepository;
import sw.im.swim.repository.WebServerEntityRepository;
import sw.im.swim.util.AesUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SystemInfoService {

    private final ServerInfoEntityRepository serverInfoEntityRepository;
    private final WebServerEntityRepository webServerEntityRepository;
    private final NginxServerEntityRepository nginxServerEntityRepository;
    private final DatabaseServerEntityRepository databaseServerEntityRepository;

    private final ModelMapper modelMapper;


}
