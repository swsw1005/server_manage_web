package sw.im.swim.bean.dto;


import lombok.*;

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

}
