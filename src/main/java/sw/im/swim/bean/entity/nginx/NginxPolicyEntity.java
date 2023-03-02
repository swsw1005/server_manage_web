package sw.im.swim.bean.entity.nginx;


import lombok.*;
import org.hibernate.annotations.Comment;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;
import sw.im.swim.bean.enums.ByteType;

import javax.persistence.*;
import java.util.Calendar;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_nginx_policy")
public class NginxPolicyEntity extends AbstractEntityWithStringPK {

    @Comment("nginx worker process")
    @Column(nullable = false, length = 60, name = "worker_processed")
    @Builder.Default
    private int workerProcessed = 8;

    @Comment("nginx conf : allow connection per worker")
    @Column(nullable = false, length = 60, name = "worker_connections")
    @Builder.Default
    private int workerConnections = 768;

    @Column(name = "last_cert_updated_at", insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar lastCertUpdatedAt;


}
