package sw.im.swim.bean.dto;

import lombok.Data;
import sw.im.swim.config.GeneralConfig;

import java.io.Serializable;
import java.util.Calendar;

@Data
public class DomainEntityDto implements Serializable {
    private Long sid;
    private Calendar createdAt;
    private String domain;

    public String getCreated() {
        try {
            createdAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(createdAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }

}
