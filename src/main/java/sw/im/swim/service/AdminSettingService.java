package sw.im.swim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.AdminEntityDto;
import sw.im.swim.bean.dto.AdminSettingEntityDto;
import sw.im.swim.bean.entity.AdminEntity;
import sw.im.swim.bean.entity.AdminSettingEntity;
import sw.im.swim.bean.enums.Authority;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.repository.AdminEntityRepository;
import sw.im.swim.repository.AdminSettingEntityRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminSettingService {

    private final ModelMapper modelMapper;

    private final AdminSettingEntityRepository adminSettingEntityRepository;

    public AdminSettingEntityDto getSetting() {

        AdminSettingEntityDto dto = new AdminSettingEntityDto();

        try {
            List<AdminSettingEntity> list = adminSettingEntityRepository.findAll();

            HashMap<String, String> map = new HashMap<>();

            for (int i = 0; i < list.size(); i++) {
                AdminSettingEntity temp = list.get(i);
                map.put(temp.getKey(), temp.getValue());
            } // for list end

            Field[] fields = dto.getClass().getDeclaredFields();

            for (int j = 0; j < fields.length; j++) {
                Field tempField = fields[j];
                Class<?> type = tempField.getType();
                final String fieldName = tempField.getName();
                final String typeName = type.getSimpleName().toLowerCase();

                final String newFieldValue = (map.get(fieldName) == null ? "" : map.get(fieldName));


                log.debug("" + "fieldName : " + fieldName
                        + " \t " + "typeName : " + typeName
                        + " \t " + "newFieldValue : " + newFieldValue
                );

                Field updateField = dto.getClass()
                        .getDeclaredField(fieldName);
                updateField.setAccessible(true);

                try {
                    switch (typeName) {
                        case "boolean":
                            updateField.setBoolean(dto, Boolean.parseBoolean(newFieldValue));
                            break;

                        case "int":
                            updateField.setInt(dto, Integer.parseInt(newFieldValue));
                            break;

                        case "double":
                            updateField.setDouble(dto, Double.parseDouble(newFieldValue));
                            break;

                        case "float":
                            updateField.setFloat(dto, Float.parseFloat(newFieldValue));
                            break;

                        case "long":
                            updateField.setLong(dto, Long.parseLong(newFieldValue));
                            break;

                        default:
                            updateField.set(dto, newFieldValue);
                            break;
                    } // switch case end

                } catch (Exception e) {
                } // try catch end

            } // for i end

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        GeneralConfig.ADMIN_SETTING = dto;

        return dto;
    }

    public synchronized void update(AdminSettingEntityDto dto) {

        try {
            List<AdminSettingEntity> list = adminSettingEntityRepository.findAll();

            HashMap<String, AdminSettingEntity> entityHashMap = new HashMap<>();

            list.stream().forEach(v -> entityHashMap.put(v.getKey(), v));

            log.error(" keySet 사이즈  " + entityHashMap.size());

            Field[] fields = dto.getClass().getDeclaredFields();

            Method[] methods = dto.getClass().getMethods();

            HashMap<String, Method> methodMap = new HashMap<>();

            for (int i = 0; i < methods.length; i++) {
                Method temp_ = methods[i];
                methodMap.put(temp_.getName().toLowerCase(), temp_);

                System.out.println("\t\t ============== = " + temp_.getName().toLowerCase());

            }

            System.out.println("methodMap.size() = " + methodMap.size());

            for (int j = 0; j < fields.length; j++) {
                Field tempField = fields[j];
                Class<?> type = tempField.getType();
                final String fieldName = tempField.getName();
                final String typeName = type.getSimpleName().toLowerCase();

                final boolean isExist = entityHashMap.keySet().contains(fieldName);

                Field updateField = dto.getClass()
                        .getDeclaredField(fieldName);

                log.debug("" + "fieldName : " + fieldName
                        + " \t " + "typeName : " + typeName
                        + " \t " + "isExist : " + isExist
                );

                Method getter = methodMap.get("get" + fieldName.toLowerCase());

                if (getter == null) {
                    getter = methodMap.get("is" + fieldName.toLowerCase());
                }

                System.out.println("getter = " + getter);

                String updateValue = null;

                for (int i = 0; i < 1; i++) {
                    try {
                        updateValue = String.valueOf(updateField.get(dto));
                    } catch (Exception e) {
                    }
                    try {
                        updateValue = String.valueOf(getter.invoke(dto));
                    } catch (Exception e) {
                    }
                }

                log.debug("" + "fieldName : " + fieldName
                        + " \t " + "typeName : " + typeName
                        + " \t " + "isExist : " + isExist
                        + " \t " + "updateValue : " + updateValue
                );

                AdminSettingEntity entity;
                if (isExist) {
                    entity = entityHashMap.get(fieldName);
                    entity.setValue(updateValue);
                } else {
                    entity = AdminSettingEntity.builder()
                            .key(fieldName)
                            .value(updateValue)
                            .build();
                }
                adminSettingEntityRepository.save(entity);

            } // for i end

        } catch (Exception e) {
            log.error(e.getMessage() + "-----------------\n", e);
        }
        getSetting();

    }


}
