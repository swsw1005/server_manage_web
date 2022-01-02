package sw.im.swim.bean.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "nginx_server_info")
@Table(name = "nginx_server_info")
public class NginxServerEntity extends EntityBase {

    @Column(nullable = false, length = 60)
    private String serverName;

    @Column(nullable = false, length = 60)
    private String faviconPath;

    @Column(nullable = false, length = 60)
    @Enumerated(EnumType.STRING)
    private HTTP http = HTTP.http;

    @Column(nullable = false, length = 60)
    private String destIp;

    @Column(nullable = false, length = 60)
    private Integer destPort;

    @Column(nullable = false, length = 60)
    @Builder.Default
    private boolean seperateAccessLog = true;

    @Column(nullable = false, length = 60)
    private boolean seperateErrorLog = true;

    public enum HTTP {
        http, https
    }

}
