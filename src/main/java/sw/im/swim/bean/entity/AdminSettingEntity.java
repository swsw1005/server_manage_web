package sw.im.swim.bean.entity;

import lombok.*;
import sw.im.swim.bean.entity.base.EntityBase;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "admin_setting")
@Table(name = "admin_setting")
public class AdminSettingEntity extends EntityBase {

    @Column(nullable = false, length = 60, unique = true)
    private String key;

    @Setter
    @Column(nullable = false, length = 200)
    private String value;
}
