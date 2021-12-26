package sw.im.swim.bean.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sw.im.swim.bean.enums.Authority;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "admin_info")
public class AdminEntity extends EntityBase {

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, updatable = false, unique = true, length = 90)
    private String email;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Authority authority = Authority.ROLE_WAIT;

    @Transient
    @Setter
    private String HASHCODE;

    // @Column(name = "hash",
    // nullable = false,
    // updatable = false,
    // unique = true
    // )
    // @Builder.Default
    // private String hash = UUID.randomUUID().toString().substring(0, 10);

    ///////////////////////////////////////////////////

    public void updatePassword(String password) {
        this.password = password;
    }

    public void update(String contact, String email, String name) {
        this.email = email;
        this.name = name;
    }

    public void updateAuthority(Authority authority) {
        this.authority = authority;
    }

}
