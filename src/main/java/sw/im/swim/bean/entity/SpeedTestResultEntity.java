package sw.im.swim.bean.entity;


import lombok.*;
import sw.im.swim.bean.enums.NotiType;

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
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "speed_test_result")
@Table(name = "speed_test_result")
public class SpeedTestResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", insertable = false, updatable = false)
    protected Long sid;

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

    @Column()
    private Double client_latitude;

    @Column()
    private Double client_longitude;

    @Column(nullable = false, length = 200)
    private String client_ip;

    @Column(nullable = false, length = 200)
    private String client_isp;

    @Column(nullable = false, length = 200)
    private String client_country;


    @Column(nullable = false, length = 200)
    private Double server_latitude;

    @Column(nullable = false, length = 200)
    private Double server_longitude;

    @Column(nullable = false, length = 200)
    private String server_name;

    @Column(nullable = false, length = 200)
    private String server_country;

    @Column(nullable = false, length = 200)
    private String server_sponsor;

    @Column(nullable = false, length = 200)
    private String server_host;

    @Column(nullable = false, length = 200)
    private String server_url;

    @Column(nullable = false, length = 200)
    private Double server_latency;


}
