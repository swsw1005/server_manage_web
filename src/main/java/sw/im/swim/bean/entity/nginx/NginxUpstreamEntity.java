package sw.im.swim.bean.entity.nginx;


import lombok.*;
import org.hibernate.annotations.Comment;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_nginx_upstream")
public class NginxUpstreamEntity extends AbstractEntityWithStringPK {

    @Comment("upstream https 사용 여부")
    @Column(nullable = false, name = "is_https")
    @Builder.Default
    private boolean https = false;


    @Comment("upstream ip")
    @Column(nullable = false, length = 60)
    private String ip;


    @Comment("upstream port")
    @Column(nullable = false, length = 60)
    private Integer port;


    @Comment("upstream 활성화 여부")
    @Column(nullable = false, length = 60)
    @Builder.Default
    private boolean activate = true;


    @Comment("[FK] upstream 소속 tb_nginx_location.id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "nginx_location_id")
    @Setter
    protected NginxLocationEntity nginxLocation;


}
