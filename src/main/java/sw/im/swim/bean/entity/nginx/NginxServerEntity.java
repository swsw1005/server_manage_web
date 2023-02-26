package sw.im.swim.bean.entity.nginx;


import lombok.*;
import sw.im.swim.bean.enums.ByteType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_nginx_server_info")
public class NginxServerEntity {

    @Id
    @Column(name = "id", insertable = true, updatable = false)
    @Builder.Default
    protected String id = UUID.randomUUID().toString().replace("-", "");

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

    @Column(nullable = true, length = 60)
    private String description;

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

}
