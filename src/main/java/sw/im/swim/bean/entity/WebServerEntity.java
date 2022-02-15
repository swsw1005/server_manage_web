package sw.im.swim.bean.entity;

import lombok.*;
import sw.im.swim.bean.entity.base.EntityBase;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "web_server_info")
@Table(name = "web_server_info")
public class WebServerEntity extends EntityBase {

    public WebServerEntity(long sid) {
        this.sid = sid;
    }

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 60)
    @Builder.Default
    private boolean https = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "server_info_sid")
    @Setter
    private ServerInfoEntity serverInfoEntity;

    @Column(nullable = false, length = 60)
    private Integer port;

    @Column(nullable = false, length = 60, name = "health_check_url")
    @Builder.Default
    private String healthCheckUrl = "/";

}
