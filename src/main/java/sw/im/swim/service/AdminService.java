package sw.im.swim.service;

import java.util.List;
import java.util.stream.Collectors;

import kr.swim.util.enc.AesUtils;
import kr.swim.util.enc.EncodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.mindrot.jbcrypt.BCrypt;
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

			if (BCrypt.checkpw(password, dto.getPassword())) {
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

	public AdminEntityDto join(final String email, final String name, final String password) throws Exception {
		try {

			log.debug("new admin join  " + " | email : " + email + " | name : " + name + " | password : " + password);

			AdminEntity entity = AdminEntity.builder()
				.email(email.trim())
				.name(name.trim())
				.password(AesUtils.encrypt(password, GeneralConfig.ENC_KEY))
				.build();

			AdminEntity entity2 = adminEntityRepository.save(entity);

			return modelMapper.map(entity2, AdminEntityDto.class);
		} catch (Exception e) {
			throw new Exception("wrong email : " + email + " : " + e + " : " + e.getMessage());
		}

	}

	public List<AdminEntityDto> findAll() {
		return adminEntityRepository.findAll()
			.stream()
			.map((element) -> modelMapper.map(element, AdminEntityDto.class))
			.collect(Collectors.toList());
	}

	public void getDefaultAdmin() throws EncodingException {
		if (adminEntityRepository.count() < 1) {
			AdminEntity adminEntity = AdminEntity.builder()
				.name("admin")
				.email("admin@admin.com")
				.password(BCrypt.hashpw("admin", BCrypt.gensalt()))
				.authority(Authority.ROLE_ADMIN)
				.build();
			adminEntityRepository.save(adminEntity);
		}
	}
}
