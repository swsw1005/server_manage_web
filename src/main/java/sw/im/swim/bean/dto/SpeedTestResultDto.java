package sw.im.swim.bean.dto;


import lombok.Getter;
import lombok.Setter;

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
public class SpeedTestResultDto {
    protected Long sid;
    protected Calendar createdAt = Calendar.getInstance();
    private Double download;
    private Double upload;
    private Double ping;

    private SpeedTestClientDto speedTestClientDto;
    private SpeedTestServerDto speedTestServerDto;

}
