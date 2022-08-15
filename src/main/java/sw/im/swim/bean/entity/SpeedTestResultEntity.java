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

    @Column(name = "client_latitude")
    private Double client_latitude;

    @Column(name = "client_longitude")
    private Double client_longitude;

    @Column(name = "client_ip",nullable = false, length = 200)
    private String client_ip;

    @Column(name = "client_isp",nullable = false, length = 200)
    private String client_isp;

    @Column(name = "client_country",nullable = false, length = 200)
    private String client_country;


    @Column(name = "server_latitude", nullable = false, length = 200)
    private Double server_latitude;

    @Column(name = "server_longitude", nullable = false, length = 200)
    private Double server_longitude;

    @Column(name = "server_name", nullable = false, length = 200)
    private String server_name;

    @Column(name = "server_country", nullable = false, length = 200)
    private String server_country;

    @Column(name = "server_sponsor", nullable = false, length = 200)
    private String server_sponsor;

    @Column(name = "server_host", nullable = false, length = 200)
    private String server_host;

    @Column(name = "server_url", nullable = false, length = 200)
    private String server_url;

    @Column(name = "server_latency", nullable = false, length = 200)
    private Double server_latency;


}
