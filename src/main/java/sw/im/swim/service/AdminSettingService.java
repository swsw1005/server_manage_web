package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.AdminSettingEntityDto;
import sw.im.swim.util.date.DateFormatUtil;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminSettingService implements InitializingBean {

    private final Scheduler defaultScheduler;

//    private final AdminSettingEntityRepository adminSettingEntityRepository;

    public AdminSettingEntityDto getSetting() {

//        AdminSettingEntityDto dto = new AdminSettingEntityDto();
//
//        try {
//            List<AdminSettingEntity> list = adminSettingEntityRepository.findAll();
//
//            HashMap<String, String> map = new HashMap<>();
//
//            for (int i = 0; i < list.size(); i++) {
//                AdminSettingEntity temp = list.get(i);
//                map.put(temp.getKey(), temp.getValue());
//            } // for list end
//
//            Field[] fields = dto.getClass().getDeclaredFields();
//
//            for (int j = 0; j < fields.length; j++) {
//                Field tempField = fields[j];
//                Class<?> type = tempField.getType();
//                final String fieldName = tempField.getName();
//                final String typeName = type.getSimpleName().toLowerCase();
//
//                final String newFieldValue = (map.get(fieldName) == null ? "" : map.get(fieldName));
//
//                log.debug("" + "fieldName : " + fieldName
//                        + " \t " + "typeName : " + typeName
//                        + " \t " + "newFieldValue : " + newFieldValue
//                );
//
//                Field updateField = dto.getClass().getDeclaredField(fieldName);
//                updateField.setAccessible(true);
//
//                try {
//                    switch (typeName) {
//                        case "boolean":
//                            updateField.setBoolean(dto, Boolean.parseBoolean(newFieldValue));
//                            GeneralConfig.NOTI_SETTING_MAP.put(fieldName, Boolean.parseBoolean(newFieldValue));
//                            break;
//
//                        case "int":
//                            updateField.setInt(dto, Integer.parseInt(newFieldValue));
//                            break;
//
//                        case "double":
//                            updateField.setDouble(dto, Double.parseDouble(newFieldValue));
//                            break;
//
//                        case "float":
//                            updateField.setFloat(dto, Float.parseFloat(newFieldValue));
//                            break;
//
//                        case "long":
//                            updateField.setLong(dto, Long.parseLong(newFieldValue));
//                            break;
//
//                        default:
//                            if (fieldName.contains("TOKEN") && (newFieldValue == null || newFieldValue.length() < 3)) {
//                                updateField.set(dto, UUID.randomUUID().toString().substring(0, 20));
//                            } else if (fieldName.contains("NGINX_LOG_FORMAT") && (newFieldValue == null || newFieldValue.length() < 3)) {
//                                updateField.set(dto, GeneralConfig.NGINX_LOG_FORMAT_DEFAULT);
//                            } else {
//                                updateField.set(dto, newFieldValue);
//                            }
//
//                            break;
//                    } // switch case end
//
//                } catch (Exception e) {
//                    if (log.isDebugEnabled()) {
//                        log.error(e + "  " + e.getMessage() + "__________", e);
//                    } else {
//                        log.warn(e + "  " + e.getMessage() + "__");
//                    }
//                } // try catch end
//
//            } // for i end
//
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//
//        GeneralConfig.ADMIN_SETTING = dto;
//
//        return dto;

        return null;
    }

    public synchronized void update(AdminSettingEntityDto dto) {

//        AdminSettingEntityDto newDto = null;
//
//        try {
//            List<AdminSettingEntity> list = adminSettingEntityRepository.findAll();
//
//            HashMap<String, AdminSettingEntity> entityHashMap = new HashMap<>();
//
//            list.stream().forEach(v -> entityHashMap.put(v.getKey(), v));
//
//            Field[] fields = dto.getClass().getDeclaredFields();
//
//            Method[] methods = dto.getClass().getMethods();
//
//            HashMap<String, Method> methodMap = new HashMap<>();
//
//            for (int i = 0; i < methods.length; i++) {
//                Method temp_ = methods[i];
//                methodMap.put(temp_.getName().toLowerCase(), temp_);
//            }
//
//            for (int j = 0; j < fields.length; j++) {
//                Field tempField = fields[j];
//                Class<?> type = tempField.getType();
//                final String fieldName = tempField.getName();
//                final String typeName = type.getSimpleName().toLowerCase();
//
//                final boolean isExist = entityHashMap.keySet().contains(fieldName);
//
//                Field updateField = dto.getClass().getDeclaredField(fieldName);
//
//                Method getter = methodMap.get("get" + fieldName.toLowerCase());
//
//                if (getter == null) {
//                    getter = methodMap.get("is" + fieldName.toLowerCase());
//                }
//
//                String updateValue = null;
//
//                for (int i = 0; i < 1; i++) {
//                    try {
//                        updateValue = String.valueOf(updateField.get(dto));
//                    } catch (Exception e) {
//                    }
//                    try {
//                        updateValue = String.valueOf(getter.invoke(dto));
//                    } catch (Exception e) {
//                    }
//                }
//
//                log.debug("" + "fieldName : " + fieldName
//                        + " \t " + "typeName : " + typeName
//                        + " \t " + "isExist : " + isExist
//                        + " \t " + "updateValue : " + updateValue
//                );
//
//                AdminSettingEntity entity;
//                if (isExist) {
//                    entity = entityHashMap.get(fieldName);
//                    entity.setValue(updateValue);
//                } else {
//                    entity = AdminSettingEntity.builder().key(fieldName).value(updateValue).build();
//                }
//                adminSettingEntityRepository.save(entity);
//            } // for i end
//
//        } catch (Exception e) {
//            log.error(e.getMessage() + "-----------------\n", e);
//        }
//        getSetting();
//
//        newDto = GeneralConfig.ADMIN_SETTING;
//
//        cronSetting("DB_BACKUP", newDto.getDB_BACKUP_CRON(), DatabaseBackupJob.class);
//        cronSetting("ADMIN_LOG_MAIL", newDto.getADMIN_LOG_MAIL_CRON(), AdminLogMailJob.class);
//        cronSetting("INTERNET_TEST", newDto.getINTERNET_TEST_CRON(), InternetTestJob.class);
//        cronSetting("CERT_CHECK", newDto.getCERT_NOTI_CRON(), CheckCertJob.class);
    }

    public void cronSetting(final String CRON_PREFIX, final String cron, Class<? extends Job> jobClass) {
        try {
            log.warn(CRON_PREFIX + " => try cron => " + cron);
            if (cron == null || cron.length() < 10) {
                log.warn("no cron ,bye");
                return;
            }
            CronScheduleBuilder cronSchedule = null;
            cronSchedule = cronSchedule(cron);

            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity("jobName", CRON_PREFIX + "_JOB").build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerName", CRON_PREFIX + "_TRIGGER").withSchedule(cronSchedule).build();

            if (defaultScheduler.checkExists(jobDetail.getKey())) {
                defaultScheduler.deleteJob(jobDetail.getKey());
            }

            Date time = defaultScheduler.scheduleJob(jobDetail, trigger);
            log.warn("NEXT [" + CRON_PREFIX + "_JOB]  TIME => " + DateFormatUtil.DATE_FORMAT_yyyyMMdd_T_HHmmssXXX.format(time));
            defaultScheduler.start();

        } catch (RuntimeException e) {
            log.error("Maybe  [" + CRON_PREFIX + "]  Cron Expression ERROR.... [" + cron + "]");
        } catch (Exception e) {
            log.error(e.toString() + "\t" + e.getMessage() + " =====", e);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
