package sw.im.swim.bean.entity.speedtest;


import lombok.*;
import sw.im.swim.bean.enums.NotiType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

/**
 * <PRE>
 * noti 작업에 필요한 데이터 저장
 * ** nateon팀룸 url,
 * ** slack token, room_id
 * </PRE>
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_speed_test_result")
public class SpeedTestResultEntity {

    @Id
    @Column(name = "id", insertable = true, updatable = false)
    @Builder.Default
    protected String id = UUID.randomUUID().toString().replace("-", "");

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

    @Column(nullable = false, length = 100)
    private Double download;

    @Column(nullable = false, length = 100)
    private Double upload;

    @Column(nullable = false, length = 100)
    private Double ping;

    @Column(name = "client_latitude")
    private Double client_latitude;

    @Column(name = "client_longitude")
    private Double client_longitude;

    @Column(name = "client_ip", nullable = false, length = 200)
    private String client_ip;

    @Column(name = "client_isp", nullable = false, length = 200)
    private String client_isp;

    @Column(name = "client_country", nullable = false, length = 200)
    private String client_country;

    @ManyToOne(optional = false)
    @JoinColumn(name = "speed_test_server_sid")
    @Setter
    private SpeedTestServerEntity speedTestServerEntity;


}
