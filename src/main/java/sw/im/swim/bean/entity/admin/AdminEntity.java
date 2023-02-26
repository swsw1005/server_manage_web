package sw.im.swim.bean.entity.admin;

import lombok.*;
import sw.im.swim.bean.enums.Authority;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_admin_info")
public class AdminEntity  {

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

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, updatable = false, unique = true, length = 90)
    private String email;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Authority authority = Authority.ROLE_WAIT;

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
