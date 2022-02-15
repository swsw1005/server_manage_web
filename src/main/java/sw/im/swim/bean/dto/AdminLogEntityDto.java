package sw.im.swim.bean.dto;

import lombok.Data;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.config.GeneralConfig;

import java.io.Serializable;
import java.util.Calendar;

@Data
public class AdminLogEntityDto implements Serializable {
    protected Long sid;
    protected Calendar createdAt = Calendar.getInstance();
    private AdminLogType title;
    private String message1;
    private String message2;

    public String getCreated() {
        try {
            createdAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(createdAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }


}
