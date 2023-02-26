package sw.im.swim.bean.entity.domain;


import lombok.*;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_domain_info")
public class RootDomainEntity extends AbstractEntityWithStringPK {

    @Column(nullable = false, length = 100, unique = true)
    private String rootDomain;

}
