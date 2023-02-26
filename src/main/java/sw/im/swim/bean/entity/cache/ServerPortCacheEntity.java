package sw.im.swim.bean.entity.cache;

import lombok.*;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_server_port_cache")
public class ServerPortCacheEntity extends AbstractEntityWithStringPK {

    @Column(nullable = false, length = 60)
    private String ip;

    @Column(nullable = false, length = 60)
    private String port;

    @Column(name = "last_connected_at", nullable = false, updatable = true, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar lastConnectedAt = Calendar.getInstance();

}
