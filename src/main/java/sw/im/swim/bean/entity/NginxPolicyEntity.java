package sw.im.swim.bean.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sw.im.swim.bean.entity.base.EntityBase;
import sw.im.swim.util.number.NumberVaildateUtil;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "nginx_policy_info")
@Table(name = "nginx_policy_info")
public class NginxPolicyEntity extends EntityBase {

    @Setter
    @Column(nullable = false, length = 60)
    @Builder.Default
    private String name = "기본정책";

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

//    @ManyToMany
//    @JoinTable(name = "nginx_policy_server_join",
//            joinColumns = @JoinColumn(name = "nginx_policy_info_sid"),
//            inverseJoinColumns = @JoinColumn(name = "nginx_server_info_sid")
//    )
//    public List<NginxServerEntity> nginxServerEntities = new ArrayList<>();

    public NginxPolicyEntity(long sid) {
        super();
        this.sid = sid;
    }
}
