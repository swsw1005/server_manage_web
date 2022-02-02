package sw.im.swim.bean.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Calendar;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "domain_info")
@Table(name = "domain_info")
public class DomainEntity {

    public DomainEntity(Long sid) {
        this.sid = sid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", insertable = false, updatable = false)
    protected Long sid;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

    @Column(nullable = false, length = 60, unique = true)
    private String domain;

}
