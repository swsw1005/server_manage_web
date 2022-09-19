package sw.im.swim.bean.entity;


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
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "SpeedTestServer")
@Table(name = "speed_test_server")
public class SpeedTestServerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", insertable = false, updatable = false)
    protected Long sid;

    @Column(name = "server_id", nullable = false, unique = true)
    private int serverId;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "country", nullable = false, length = 200)
    private String country;

    @Column(name = "city", nullable = false, length = 200)
    private String city;

    @Setter
    @Builder.Default
    @Column(name = "latitude", nullable = false, length = 200)
    private double latitude = -1;

    @Setter
    @Builder.Default
    @Column(name = "longitude", nullable = false, length = 200)
    private double longitude = -1;

    @Setter
    @Builder.Default
    @Column(name = "sponsor", nullable = false, length = 200)
    private String sponsor = "";

    @Setter
    @Builder.Default
    @Column(name = "host", nullable = false, length = 200)
    private String host = "";

    @Setter
    @Builder.Default
    @Column(name = "url", nullable = false, length = 200)
    private String url = "";

    @Setter
    @Builder.Default
    @Column(name = "latency", nullable = false, length = 200)
    private double latency = -1;


}
