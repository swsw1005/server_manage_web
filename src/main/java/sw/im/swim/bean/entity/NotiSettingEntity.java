package sw.im.swim.bean.entity;

import lombok.*;
import sw.im.swim.bean.enums.JobType;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "noti_setting_info")
@Table(name = "noti_setting_info",
        uniqueConstraints = @UniqueConstraint(columnNames = {"key", "job_type"})
)
public class NotiSettingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", insertable = false, updatable = false)
    protected Long sid;

    @Column(name = "key", nullable = false, length = 60)
    private String key;

    @Column(name = "job_type", nullable = false, length = 60)
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(name = "value", nullable = false, length = 200)
    private String value;

}
