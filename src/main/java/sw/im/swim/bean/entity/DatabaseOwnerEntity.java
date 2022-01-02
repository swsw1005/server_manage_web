package sw.im.swim.bean.entity;

import lombok.*;
import sw.im.swim.bean.enums.DbType;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "database_owner_info")
@Table(name = "database_owner_info")
public class DatabaseOwnerEntity extends EntityBase {

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 60)
    private String id;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = true, length = 200)
    private String description;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private DbType dbType;

}
