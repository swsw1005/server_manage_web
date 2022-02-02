package sw.im.swim.bean.entity;


import lombok.*;
import sw.im.swim.bean.entity.base.EntityBase;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "nginx_server_info")
@Table(name = "nginx_server_info")
public class NginxServerEntity extends EntityBase {

    @Column(nullable = false, length = 60)
    private String name;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "nginx_policy_sid")
//    @Setter
//    private NginxPolicyEntity nginxPolicyEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "domain_info_sid")
    @Setter
    private DomainEntity domainEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "web_server_info_sid")
    @Setter
    private WebServerEntity webServerEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "favicon_info_sid")
    @Setter
    private FaviconEntity faviconEntity;

    @Column(nullable = false, length = 60)
    @Builder.Default
    private boolean seperateLog = true;

    public NginxServerEntity(long sid) {
        super();
        this.sid = sid;
    }
}
