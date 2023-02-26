package sw.im.swim.bean.entity.database;

import lombok.*;
import sw.im.swim.bean.enums.DbType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_db_server_info")
public class DatabaseServerEntity {

    @Id
    @Column(name = "id", insertable = true, updatable = false)
    @Builder.Default
    protected String id = UUID.randomUUID().toString().replace("-", "");

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

    @Column(nullable = false, length = 60)
    private String ip;

    @Column(nullable = false, length = 60)
    private Integer port;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 60, name = "id")
    private String dbId;

    @Column(nullable = false, length = 200, name = "password")
    private String dbPassword;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private DbType dbType;

}
