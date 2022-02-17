package sw.im.swim.bean.dto;

import lombok.Data;
import sw.im.swim.config.GeneralConfig;

import java.io.Serializable;
import java.util.Calendar;

@Data
public class NginxServerEntityDto implements Serializable {
    private Long sid;
    private Calendar createdAt;
    private Calendar updatedAt;
    private Calendar deletedAt;
    private String name;
    private DomainEntityDto domainEntity;
    private WebServerEntityDto webServerEntity;
    private FaviconEntityDto faviconEntity;
    private boolean seperateLog;

    private boolean selected = false;

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
