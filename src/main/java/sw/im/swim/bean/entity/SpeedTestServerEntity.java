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
@Entity(name = "speed_test_server")
@Table(name = "speed_test_server")
public class SpeedTestServerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", insertable = false, updatable = false)
    protected Long sid;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

    @Column(nullable = false, length = 200)
    private Long id;

    @Column(nullable = false, length = 200)
    private Double latitude;

    @Column(nullable = false, length = 200)
    private Double longitude;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 200)
    private String country;

    @Column(nullable = false, length = 200)
    private String cc;

    @Column(nullable = false, length = 200)
    private String sponsor;

    @Column(nullable = false, length = 200)
    private String host;

    @Column(nullable = false, length = 200)
    private String url;

    @Column(nullable = false, length = 200)
    private Double latency;


}