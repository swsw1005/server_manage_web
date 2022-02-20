package sw.im.swim.bean.entity;

import javax.persistence.*;

import lombok.*;
import sw.im.swim.bean.entity.base.EntityBase;
import sw.im.swim.bean.enums.DbType;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "db_server_info")
@Table(name = "db_server_info")
public class DatabaseServerEntity extends EntityBase {

    @Column(nullable = false, length = 60)
    private Integer port;

    @Column(nullable = false, length = 60)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "server_info_sid")
    @Setter
    private ServerInfoEntity serverInfoEntity;

    @Column(nullable = false, length = 60, name = "id")
    private String dbId;

    @Column(nullable = false, length = 200, name = "password")
    private String dbPassword;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private DbType dbType;

}
