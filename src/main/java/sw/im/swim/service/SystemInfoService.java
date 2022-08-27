package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.repository.DatabaseServerEntityRepository;
import sw.im.swim.repository.NginxServerEntityRepository;
import sw.im.swim.repository.ServerInfoEntityRepository;
import sw.im.swim.repository.WebServerEntityRepository;

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
