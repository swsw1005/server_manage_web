package sw.im.swim.bean.entity.admin;

import lombok.*;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_admin_setting")
public class AdminSettingEntity extends AbstractEntityWithStringPK {

    @Column(nullable = false, length = 60, unique = true)
    private String key;

    @Setter
    @Column(nullable = false, length = 200)
    private String value;
}
