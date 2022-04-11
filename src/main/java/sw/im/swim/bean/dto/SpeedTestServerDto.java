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
public class SpeedTestServerDto {

    protected Long sid;
    protected Calendar createdAt = Calendar.getInstance();
    private Long id;
    private Double latitude;
    private Double longitude;
    private String name;
    private String country;
    private String cc;
    private String sponsor;
    private String host;
    private String url;
    private Double latency;

    public String getCreated() {
        try {
            createdAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(createdAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }

}
