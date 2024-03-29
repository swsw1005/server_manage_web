package sw.im.swim.bean.dto;

import lombok.Data;
import sw.im.swim.config.GeneralConfig;

import java.io.Serializable;
import java.util.Calendar;

@Data
public class WebServerEntityDto implements Serializable {
    private Long sid;
    private Calendar createdAt;
    private Calendar updatedAt;
    private Calendar deletedAt;
    private String name;
    private boolean https;
    private ServerInfoEntityDto serverInfoEntity;
    private Integer port;
    private String healthCheckUrl = "/";

    public String getAddress() {
        return serverInfoEntity.getIp() + ":" + port;
    }

    public String HTTPS_PREFIX() {
        if (https) {
            return "https://";
        } else {
            return "http://";
        }
    }

    public String getCreated() {
        try {
            createdAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(createdAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }

    public String getUpdated() {
        try {
            updatedAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(updatedAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }

    public String getDeleted() {
        try {
            deletedAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(deletedAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }

    public boolean isAlive() {
        if (deletedAt == null) {
            return true;
        }
        return false;
    }

}
