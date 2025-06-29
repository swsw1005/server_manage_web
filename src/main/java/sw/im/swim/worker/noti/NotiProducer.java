package sw.im.swim.worker.noti;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.NotiEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.bean.enums.NotiType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
public class NotiProducer implements Runnable {

	private final String msg;

	private final AdminLogType NOTI_TYPE;

	private final String NOTI_TYPE_PREFIX = "NOTI_";

	@Override
	public void run() {

		boolean FLAG_MASTER = false;
		HashMap<NotiType, Boolean> notiMap = new HashMap<>();
		NotiType[] arr = NotiType.values();

		for (int i = 0; i < arr.length; i++) {
			notiMap.put(arr[i], false);
		}

		final String masterFlag = NOTI_TYPE_PREFIX + NOTI_TYPE.name();

		try {
			FLAG_MASTER = GeneralConfig.NOTI_SETTING_MAP.get(masterFlag);
		} catch (NullPointerException e) {
			log.error("해당하는 flag가 없습니다. \t" + masterFlag + "\t" + FLAG_MASTER + " => " + e.getMessage());
		} catch (Exception e) {
			log.error(masterFlag + "\t" + FLAG_MASTER + " => " + e.getMessage(), e);
		}

		for (NotiType key : notiMap.keySet()) {
			try {
				final String subFlag = NOTI_TYPE_PREFIX + NOTI_TYPE.name() + "_" + key.name();
				notiMap.put(key, GeneralConfig.NOTI_SETTING_MAP.get(subFlag));
			} catch (Exception e) {
				log.error("[" + key + "] " + e.getMessage(), e);
			}
		}

		if (!FLAG_MASTER) {
			return;
		}

		try {
			ThreadWorkerPoolContext WORKER = ThreadWorkerPoolContext.getInstance();
			HashMap<Long, NotiEntityDto> map = new HashMap<>(GeneralConfig.NOTI_DTO_MAP);

			if (map == null || map.isEmpty()) {
				return;
			}

			map.forEach((k, v) -> {
				Boolean b = notiMap.get(v.getNotiType());
				if (b) {
					WORKER.NOTI_WORKER.execute(new NotiWorker(v, msg));
				}
			});
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}
