package sw.im.swim.bean.entity;

import lombok.*;
import sw.im.swim.bean.entity.base.EntityBase;
import sw.im.swim.bean.enums.Authority;

import javax.persistence.*;
import java.util.Calendar;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "admin_log")
@Table(name = "admin_log")
public class AdminLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", insertable = false, updatable = false)
    protected Long sid;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 60)
    private String message1;

    @Column(nullable = false, length = 600)
    private String message2;

}
