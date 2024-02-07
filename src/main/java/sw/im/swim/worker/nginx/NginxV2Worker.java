package sw.im.swim.worker.nginx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;

import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.util.nginx.NginxConfCreateUtil;
import sw.im.swim.util.nginx.NginxConfStringContext;
import sw.im.swim.util.nginx.NginxServiceControlUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <PRE>
 * 1. certbot DNS 방식으로 기존 발급된 인증서 있음
 * 2. 정해진 위치의 pem 파일 이용해서 nginx 작성
 * </PRE>
 */
@Slf4j
@RequiredArgsConstructor
public class NginxV2Worker implements Runnable {

	private final NginxPolicyEntityDto policyEntityDto;
	private final List<NginxServerEntityDto> nginxServerEntityList;
	private final AdminLogService adminLogService;

	@Override
	public void run() {

		log.info("===== START NGINX JOB !!!!!! =====");
		log.warn(GeneralConfig.ADMIN_SETTING.toStringNginxSettings());

		boolean error = true;

		try {
			FileUtils.forceMkdir(new File(NginxConfStringContext.NGINX_CONF_DIR()));
			FileUtils.forceMkdir(new File(NginxConfStringContext.NGINX_EXTRA_SITES_DIR()));

			File oldNginxConf = new File(NginxConfStringContext.NGINX_CONF_FILE_PATH);
			FileUtils.deleteQuietly(oldNginxConf);
			log.error("oldNginxConf.delete() => " + oldNginxConf.getAbsolutePath());

			File newNginxConf = new File(NginxConfStringContext.NGINX_CONF_FILE_PATH);
			FileUtils.touch(newNginxConf);
			log.error("! newNginxConf.touch() => " + newNginxConf.getAbsolutePath());

			// 기본 nginx conf 설정
			List<String> defaultNginxConfText = NginxConfCreateUtil.DEFAULT_NGINX_CONF(policyEntityDto);
			FileUtils.writeLines(newNginxConf, StandardCharsets.UTF_8.name(),
				///////////////////////////////////////////////////////////
				defaultNginxConfText, System.lineSeparator());

			// 기존 sites-enable 삭제
			FileUtils.cleanDirectory(new File(NginxConfStringContext.NGINX_EXTRA_SITES_DIR()));

			// 새로운 sites-enable 작성
			NginxConfCreateUtil.CREATE_SITES_ENABLES(NginxConfStringContext.NGINX_EXTRA_SITES_DIR(),
				nginxServerEntityList);

			log.warn("nginx 재시작 !");

			// 재시작
			NginxServiceControlUtil.NGINX_RESTART();

			// nginx 상태 확인
			final boolean isNginxAlive = NginxServiceControlUtil.checkNginxAlive();

			log.warn("nginx 재시작 후 상태 :: " + isNginxAlive);

			adminLogService.insertLog(AdminLogType.NGINX_UPDATE, "SUCCESS", "NGINX UPDATE END");

			error = false;

		} catch (RuntimeException e) {
			log.error(e.getMessage() + " | " + e.getLocalizedMessage());
		} catch (Exception e) {
			log.error(e.getMessage() + " | " + e.getLocalizedMessage());
		} finally {
			log.info("\n" + "=======================================" + "\n" + "FINALIZE NGINX JOB !!!!!!" + "\n"
				+ "=======================================");

			if (error) {
				// 기존 sites-enable 삭제
				try {
					FileUtils.cleanDirectory(new File(NginxConfStringContext.NGINX_EXTRA_SITES_DIR()));
					log.warn("nginx 재시작 !");
					// 재시작
					NginxServiceControlUtil.NGINX_RESTART();
				} catch (RuntimeException e) {
					log.error("nginx 복구중 에러 :: " + e + "  " + e.getMessage(), e);
				} catch (Exception e) {
					log.error("nginx 복구중 에러 :: " + e + "  " + e.getMessage(), e);
				}
			}

		}

	}

}
