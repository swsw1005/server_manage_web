package sw.im.swim.bean.entity;

import lombok.*;
import sw.im.swim.bean.enums.Authority;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "admin_info")
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
