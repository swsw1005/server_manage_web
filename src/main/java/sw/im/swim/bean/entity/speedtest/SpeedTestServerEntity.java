package sw.im.swim.bean.entity.speedtest;


import lombok.*;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;

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
@Entity
@Table(name = "tb_speed_test_server")
public class SpeedTestServerEntity extends AbstractEntityWithStringPK {

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
