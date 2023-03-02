package sw.im.swim.bean.entity.domain;


import lombok.*;
import org.hibernate.annotations.Comment;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;

import javax.persistence.*;
import java.util.Calendar;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_domain_info")
public class RootDomainEntity extends AbstractEntityWithStringPK {

    @Comment("rootDomain ex) https://XXX.swim.com >> swim.com")
    @Column(nullable = false, length = 100, unique = true)
    private String rootDomain;

    @Comment("인증서 발급 일자")
    @Column(name = "cert_started_at", insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar certStartedAt = null;

    @Comment("인증서 만료 기간")
    @Column(name = "cert_expired_at", insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar certExpiredAt = null;


}
