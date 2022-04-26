package sw.im.swim.bean.entity;


import lombok.*;
import sw.im.swim.bean.enums.JailType;
import sw.im.swim.bean.enums.JobType;

import javax.persistence.*;
import java.util.Calendar;

/**
 * <PRE>
 * fail2ban 로그 저장
 * </PRE>
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "fail2ban_log")
@Table(name = "fail2ban_log")
public class Fail2banLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", insertable = false, updatable = false)
    protected Long sid;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private JailType jailType;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(nullable = true, length = 100)
    private String ip;

    @Column(nullable = true, length = 100)
    private String server;

    @Column(nullable = true, length = 20)
    private String country;

}
