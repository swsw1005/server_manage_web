package sw.im.swim.bean.entity.cache;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_server_info")
public class ServerInfoEntity {

    @Id
    @Column(name = "id", insertable = true, updatable = false)
    @Builder.Default
    protected String id = UUID.randomUUID().toString().replace("-", "");

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

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
