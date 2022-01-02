package sw.im.swim.bean.entity;

import lombok.*;
import sw.im.swim.bean.enums.Authority;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "setting_info")
@Table(name = "setting_info")
public class SettingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", insertable = false, updatable = false)
    protected Long sid;

    @Column(nullable = false, length = 60)
    private String key;

    @Column(nullable = false, length = 200)
    private String value;

}
