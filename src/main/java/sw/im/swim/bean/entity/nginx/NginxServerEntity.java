package sw.im.swim.bean.entity.nginx;


import lombok.*;
import org.hibernate.annotations.Comment;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;
import sw.im.swim.bean.enums.ByteType;
import sw.im.swim.bean.enums.nginx.SameSite;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_nginx_server_info")
public class NginxServerEntity extends AbstractEntityWithStringPK {

    @Comment("sub domain")
    @Column(nullable = true, length = 100, name = "sub_domain")
    private String subDomain;

    @Comment("root 도메인")
    @Column(nullable = false, length = 100, name = "root_domain")
    private String RootDomain;

    @Comment("접속 포트")
    @Column(name = "port")
    @Builder.Default
    private int port = 443;

    @Comment("해당 도메인 설명")
    @Column(nullable = true, length = 60)
    private String description;

    @Comment("해당 도메인 활성화 여부")
    @Column(nullable = false, length = 60)
    @Builder.Default
    private boolean activate = true;

    @Comment("favicon 파일 경로")
    @Column(nullable = true, name = "favicon_path", length = 60)
    @Builder.Default
    private String faviconPath = null;

    @Column(nullable = false, length = 60)
    @Builder.Default
    private boolean seperateLog = true;

    @Column(name = "max_body_size")
    @Builder.Default
    private int maxBodySize = 100;

    @Column(name = "byte_type")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ByteType byteType = ByteType.MB;

    @Column(name = "samesite_option")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private SameSite sameSite = SameSite.lax;

}
