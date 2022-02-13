package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.AdminEntityDto;
import sw.im.swim.bean.entity.AdminEntity;
import sw.im.swim.bean.enums.Authority;
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

            if (dto.getPassword().equals(password) == false
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
            throw new Exception("login fail");
        }
    }

    public AdminEntityDto findByEmail(final String email) throws Exception {
        try {
            AdminEntity entity = adminEntityRepository.findByEmailEquals(email);
            return modelMapper.map(entity, AdminEntityDto.class);
        } catch (Exception e) {
            throw new Exception("wrong email");
        }
    }


}
