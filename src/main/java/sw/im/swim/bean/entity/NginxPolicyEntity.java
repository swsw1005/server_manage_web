package sw.im.swim.bean.entity;


import lombok.*;
import sw.im.swim.bean.entity.base.EntityBase;
import sw.im.swim.util.number.NumberVaildateUtil;

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

    @Setter
    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 60)
    @Builder.Default
    private int workerProcessed = 8;

    @ManyToOne(optional = false)
    @JoinColumn(name = "domain_info_sid")
    @Setter
    private DomainEntity domainEntity;

    @Column(nullable = false, length = 60)
    @Builder.Default
    private int workerConnections = 768;

    public void setWorkerProcessed(int workerProcessed) {
//        this.workerProcessed = NumberVaildateUtil.validateBetween(workerProcessed, 4, 16);
        this.workerProcessed = workerProcessed;
    }

    public void setWorkerConnections(int workerConnections) {
//        this.workerConnections = NumberVaildateUtil.validateBetween(workerConnections, 128, 1024);
        this.workerConnections = workerConnections;
    }

    public int getWorkerProcessed() {
        return NumberVaildateUtil.validateBetween(workerProcessed, 4, 64);
//        return workerProcessed;
    }

    public int getWorkerConnections() {
        return NumberVaildateUtil.validateBetween(workerConnections, 128, 1024);
//        return workerConnections;
    }

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
