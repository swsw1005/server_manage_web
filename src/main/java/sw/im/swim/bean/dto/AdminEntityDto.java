package sw.im.swim.bean.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sw.im.swim.bean.enums.Authority;
import sw.im.swim.config.GeneralConfig;

import java.io.Serializable;
import java.util.Calendar;


@Getter
@Setter
public class AdminEntityDto implements Serializable {
    private Long sid;
    private Calendar createdAt;
    private Calendar updatedAt;
    private Calendar deletedAt;
    private String name;
    private String password;
    private String email;
    private Authority authority;


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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AdminEntityDto{");
        sb.append("sid=").append(sid);
        sb.append(", createdAt=").append(getCreated());
        sb.append(", updatedAt=").append(getUpdated());
        sb.append(", deletedAt=").append(getDeletedAt());
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", authority=").append(authority);
        sb.append('}');
        return sb.toString();
    }
}
