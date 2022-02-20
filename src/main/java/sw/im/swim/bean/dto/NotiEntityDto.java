package sw.im.swim.bean.dto;

import lombok.Builder;
import lombok.Data;
import sw.im.swim.bean.enums.NotiType;
import sw.im.swim.config.GeneralConfig;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Calendar;

@Data
public class NotiEntityDto implements Serializable {
    private Long sid;
    private Calendar createdAt;
    private NotiType notiType;
    private String column1;
    private String column2;
    private String name;
    private Boolean active = true;

    public String getCreated() {
        try {
            createdAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(createdAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }

}
