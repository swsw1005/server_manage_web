package sw.im.swim.bean.entity;

import javax.persistence.*;

import lombok.*;
import sw.im.swim.bean.enums.DbType;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "database_info")
@Table(name = "database_info")
public class DatabaseEntity extends EntityBase {

    @Column(nullable = false, length = 60)
    private String ip;

    @Column(nullable = false, length = 60)
    private Integer port;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_sid")
    @Setter
    private DatabaseOwnerEntity owner;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private DbType dbType;

    @Column(nullable = false)
    @Builder.Default
    @Setter
    private boolean active = true;

}
