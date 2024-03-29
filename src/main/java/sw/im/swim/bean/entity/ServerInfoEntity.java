package sw.im.swim.bean.entity;

import lombok.*;
import sw.im.swim.bean.entity.base.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "server_info")
@Table(name = "server_info")
public class ServerInfoEntity extends EntityBase {

    public ServerInfoEntity(long sid) {
        this.sid = sid;
    }

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 60, name = "ssh_id")
    private String id;

    @Column(nullable = false, length = 60, name = "ssh_password")
    private String password;

    @Column(nullable = false, length = 60)
    private String ip;

    @Column(nullable = true, length = 10, name = "ssh_port")
    private Integer sshPort;

}
