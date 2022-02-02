package sw.im.swim.bean.entity;


import lombok.*;
import sw.im.swim.bean.entity.base.EntityBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "nginx_policy_info")
@Table(name = "nginx_policy_info")
public class NginxPolicyEntity extends EntityBase {

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 60)
    @Builder.Default
    private int workerProcessed = 8;

    @Column(nullable = false, length = 60)
    @Builder.Default
    private int workerConnections = 768;

    @ManyToMany
    @JoinTable(name = "nginx_policy_server_join",
            joinColumns = @JoinColumn(name = "nginx_policy_info_sid"),
            inverseJoinColumns = @JoinColumn(name = "nginx_server_info_sid")
    )
    public List<NginxServerEntity> nginxServerEntities = new ArrayList<>();

    public NginxPolicyEntity(long sid) {
        super();
        this.sid = sid;
    }
}
