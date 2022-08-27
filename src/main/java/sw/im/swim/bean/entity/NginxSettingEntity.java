package sw.im.swim.bean.entity;

import lombok.*;
import sw.im.swim.bean.entity.base.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "nginx_setting")
@Table(name = "nginx_setting")
public class NginxSettingEntity extends EntityBase {

    @Column(nullable = false, length = 60, unique = true)
    private String key;

    @Setter
    @Column(nullable = false, length = 200)
    private String value;
}
