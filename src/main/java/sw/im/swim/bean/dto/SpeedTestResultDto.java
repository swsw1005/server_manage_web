package sw.im.swim.bean.dto;


import lombok.Getter;
import lombok.Setter;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.util.number.NumberVaildateUtil;

import javax.persistence.Column;
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


    private Double client_latitude;
    private Double client_longitude;
    private String client_ip;
    private String client_isp;
    private String client_country;

    private SpeedTestServerEntityDto speedTestServerEntityDto;

    public String getCreated() {
        try {
            createdAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(createdAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }

    public String getDownloadSpeed() {
        return NumberVaildateUtil.humanReadableInt((long) Math.round(this.download));
    }

    public String getUploadSpeed() {
        return NumberVaildateUtil.humanReadableInt((long) Math.round(this.upload));
    }

}
