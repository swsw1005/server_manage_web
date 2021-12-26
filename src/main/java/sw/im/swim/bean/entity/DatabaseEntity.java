package sw.im.swim.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sw.im.swim.bean.enums.DbType;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "database_info")
public class DatabaseEntity {

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, updatable = false, unique = true, length = 90)
    private String email;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private DbType dbType;

}
