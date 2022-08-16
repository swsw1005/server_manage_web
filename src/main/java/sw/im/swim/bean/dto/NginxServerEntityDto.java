package sw.im.swim.bean.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.Assert;
import sw.im.swim.bean.enums.ByteType;
import sw.im.swim.config.GeneralConfig;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    private int maxBodySize = 100;
    private ByteType byteType = ByteType.MB;

    private boolean selected = false;

    public String getBodySize() {
        try {
            Assert.isTrue(maxBodySize > 10, "body size too small");
            return maxBodySize + byteType.getSuffix();
        } catch (Exception e) {
        }
        return 100 + ByteType.MB.getSuffix();
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
