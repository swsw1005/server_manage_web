package sw.im.swim.config;

import kr.swim.util.enc.AesUtils;
import kr.swim.util.enc.EncodingException;
import kr.swim.util.process.ProcessExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sw.im.swim.bean.CronVO;
import sw.im.swim.bean.dto.AdminSettingEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.bean.util.DatabaseServerUtil;
import sw.im.swim.service.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostConstruct {

	private static final String DEFAULT_CRON = "0 0/3 * * * ?";

	@Value("${google.dns.username}")
	private String GOOGLE_DNS_USER_NAME;

	@Value("${google.dns.password}")
	private String GOOGLE_DNS_PASSWORD;

	@Value(value = "${AES_KEY}")
	private String AES_KEY = "";

	private final AdminLogService adminLogService;

	private final AdminSettingService adminSettingService;

	private final NotiService notiService;

	private final SpeedTestService speedTestService;

	private final NginxPolicyService nginxPolicyService;

	private final ServerInfoService serverInfoService;

	private final AdminService adminService;

	@javax.annotation.PostConstruct
	public void INIT() {

		setCronExpression();

		notiService.getAll();

		AdminSettingEntityDto adminSetting = adminSettingService.getSetting();
		adminSettingService.update(adminSetting);

		final String ip = GeneralConfig.PUBLIC_IP_INFO.getIp();

		adminLogService.insertLog(AdminLogType.STARTUP, "IP", ip);

		GeneralConfig.GOOGLE_DNS_USER_NAME = GOOGLE_DNS_USER_NAME;
		GeneralConfig.GOOGLE_DNS_PASSWORD = GOOGLE_DNS_PASSWORD;
		// GeneralConfig.CURRENT_IP = ip;

		try {
			new File(DatabaseServerUtil.DB_DUMP_FILE_TMP).delete();
		} catch (Exception e) {
		}

		try {

			GeneralConfig.ENC_KEY = AES_KEY + AES_KEY + AES_KEY + AES_KEY + AES_KEY;

			String uuid = UUID.randomUUID().toString();

			String encUUID = AesUtils.encrypt(uuid, GeneralConfig.ENC_KEY);

			String decUUID = AesUtils.decrypt(encUUID, GeneralConfig.ENC_KEY);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			System.exit(0);
		}

		serverInfoService.sync();

		nginxPolicyService.ADJUST_NGINX_POLICY();

		try {
			final String[] arr = {"sh", "-c", "speedtest-cli --list"};
			List<String> list = ProcessExecutor.runCommand(arr);
			for (String line : list) {
				speedTestService.saveServer(line);
			}
		} catch (Exception e) {
			log.error(e + " | " + e.getMessage());
		}

		try {
			adminService.getDefaultAdmin();
		} catch (EncodingException e) {
			log.error(e.getMessage(), e);
		}
		adminService.findAll()
			.forEach(admin -> {
				log.info("admin [{}] {} / {} / {}", admin.getSid(), admin.getName(), admin.getEmail(),
					admin.getAuthority());
			});

		log.info("Application START (2/2)!!!!");



		//        List<String> speedTestHostList = speedTestService.getHostList();
		//        List<String> speedTestNameList = speedTestService.getNameList();
		//        List<String> speedTestCountryList = speedTestService.getCountryList();
		//        List<SpeedTestResultDto> list = speedTestService.getList(null, 0, 100);

	}

	private void setCronExpression() {

		ArrayList<CronVO> list = new ArrayList<>();

		CronVO vo;

		vo = new CronVO("0/10 * * * * ?", "!! every 10 second", 0, 0, "");
		list.add(vo);
		vo = new CronVO("0/20 * * * * ?", "!! every 20 second", 0, 0, "");
		list.add(vo);
		vo = new CronVO("0/30 * * * * ?", "!! every 30 second", 0, 0, "");
		list.add(vo);

		vo = new CronVO("0 0/1 * * * ?", "!! every 1 minute", 0, 0, "");
		list.add(vo);
		vo = new CronVO("0 0/5 * * * ?", "!! every 5 minute", 0, 0, "");
		list.add(vo);

		vo = new CronVO("0 0 0/1 * * ?", "every 1 hour", 0, 59, "분");
		list.add(vo);
		vo = new CronVO("0 0 0/2 * * ?", "every 2 hour", 0, 59, "분");
		list.add(vo);
		vo = new CronVO("0 0 0/3 * * ?", "every 3 hour", 0, 59, "분");
		list.add(vo);
		vo = new CronVO("0 0 0/4 * * ?", "every 4 hour", 0, 59, "분");
		list.add(vo);
		vo = new CronVO("0 0 0/6 * * ?", "every 6 hour", 0, 59, "분");
		list.add(vo);
		vo = new CronVO("0 0 0/12 * * ?", "every 12 hour", 0, 59, "분");
		list.add(vo);
		vo = new CronVO("0 0 0 * * ?", "every 24 hour", 0, 23, "시");
		list.add(vo);

		GeneralConfig.CRON_EXPRESSION_LIST.clear();
		GeneralConfig.CRON_EXPRESSION_LIST.addAll(list);

	}

}
