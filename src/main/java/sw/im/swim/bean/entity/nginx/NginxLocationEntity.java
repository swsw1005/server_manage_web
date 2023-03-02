package sw.im.swim.bean.entity.nginx;


import lombok.*;
import org.hibernate.annotations.Comment;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;
import sw.im.swim.bean.enums.ByteType;
import sw.im.swim.bean.enums.LBType;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_nginx_location")
public class NginxLocationEntity extends AbstractEntityWithStringPK {

    @Comment("location 간의 우선순위. 숫자가 작을수록 우선순위")
    @Column(nullable = false, name = "priority_weight")
    @Builder.Default
    protected int priority = 9999;

    @Comment("location 경로. 맨앞에")
    @Column(nullable = false, length = 100)
    @Builder.Default
    protected String location = "/";

    @Comment("location 내부 로드밸런싱 정책")
    @Column(nullable = false, length = 60)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    protected LBType lbType = LBType.NONE;

    @Comment("location 활성화 여부")
    @Column(nullable = false, length = 60)
    @Builder.Default
    private boolean activate = true;

    @Comment("[FK] location 소속 tb_nginx_server.id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "nginx_server_id")
    @Setter
    protected NginxServerEntity nginxServer;


}
