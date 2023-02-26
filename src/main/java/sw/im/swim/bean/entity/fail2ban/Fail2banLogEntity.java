package sw.im.swim.bean.entity.fail2ban;


import lombok.*;
import sw.im.swim.bean.enums.JailType;
import sw.im.swim.bean.enums.JobType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

/**
 * <PRE>
 * fail2ban 로그 저장
 * </PRE>
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_fail2ban_log")
public class Fail2banLogEntity {

    @Id
    @Column(name = "id", insertable = true, updatable = false)
    @Builder.Default
    protected String id = UUID.randomUUID().toString().replace("-", "");

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
