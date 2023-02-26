package sw.im.swim.bean.entity.cache;


import lombok.*;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_favicon_info")
public class FaviconEntity extends AbstractEntityWithStringPK {

    @Column(nullable = false, length = 60, unique = true)
    private String path;

}
