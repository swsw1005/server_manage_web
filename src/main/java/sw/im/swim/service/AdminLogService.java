package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.entity.AdminLogEntity;
import sw.im.swim.repository.AdminLogRepository;

import javax.persistence.Column;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminLogService {

    private final AdminLogRepository adminLogRepository;

    public void insertLog(
            String title,
            String message1,
            String message2) {

        if (title == null) {
            title = "null";
        }

        if (message1 == null) {
            message1 = "null";
        }

        if (message2 == null) {
            message2 = "null";
        }

        log.info("title / message1 / message2 || " + title + " / " + message1 + " / " + message2);

        AdminLogEntity adminLogEntity = AdminLogEntity.builder()
                .title(title)
                .message1(message1)
                .message2(message2)
                .build();
        adminLogRepository.save(adminLogEntity);
    }


}
