package sw.im.swim.bean.entity.database;

import lombok.*;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;
import sw.im.swim.bean.enums.DbType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_db_server_info")
public class DatabaseServerEntity extends AbstractEntityWithStringPK {

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
