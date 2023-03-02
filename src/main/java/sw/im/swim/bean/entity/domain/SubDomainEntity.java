package sw.im.swim.bean.entity.domain;


import lombok.*;
import org.hibernate.annotations.Comment;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_sub_domain_info")
public class SubDomainEntity extends AbstractEntityWithStringPK {

    @Comment("subdomain ex) https://blog.XXXX >> blog")
    @Column(nullable = false, length = 60, unique = true)
    private String subDomain;

}
