package sw.im.swim.bean.dto;


import lombok.*;
import sw.im.swim.config.GeneralConfig;

import javax.persistence.*;
import java.util.Calendar;

/**
 * <PRE>
 * noti 작업에 필요한 데이터 저장
 * ** nateon팀룸 url,
 * ** slack token, room_id
 * </PRE>
 */
@Getter
@Setter
public class SpeedTestClientDto {

    protected Long sid;
    protected Calendar createdAt = Calendar.getInstance();
    private Double latitude;
    private Double longitude;
    private String ip;
    private String isp;
    private String country;


    public String getCreated() {
        try {
            createdAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(createdAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }

}
