package sw.im.swim.service;

import com.caffeine.lib.enc.AesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.AdminEntityDto;
import sw.im.swim.bean.entity.AdminEntity;
import sw.im.swim.bean.enums.Authority;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.repository.AdminEntityRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final ModelMapper modelMapper;

    private final AdminEntityRepository adminEntityRepository;

    public AdminEntityDto login(final String email, final String password) throws Exception {
        try {
            AdminEntityDto dto = findByEmail(email);

//            log.debug(" password => " + password);

            final String encPassword = AesUtils.encrypt(password, GeneralConfig.ENC_KEY);

            log.debug(" encPassword       => " + encPassword);
            log.debug(" dto.getPassword() => " + dto.getPassword());

            if (encPassword.equals(dto.getPassword()) == false
                    || password == null) {
                throw new Exception("wrong password");
            }

            if (dto.getAuthority().equals(Authority.ROLE_ADMIN) == false) {
                throw new Exception("wrong authority");
            }

            if (dto.getDeletedAt() != null) {
                throw new Exception("is deleted");
            }

            return dto;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("login fail");
        }
    }

    public AdminEntityDto findByEmail(final String email) throws Exception {
        try {
            AdminEntity entity = adminEntityRepository.findByEmailEquals(email);
            return modelMapper.map(entity, AdminEntityDto.class);
        } catch (Exception e) {
            throw new Exception("wrong email : " + email + " : " + e + " : " + e.getMessage());
        }
    }


}
