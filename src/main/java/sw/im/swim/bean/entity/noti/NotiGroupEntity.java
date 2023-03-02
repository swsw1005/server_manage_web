package sw.im.swim.bean.entity.noti;


import lombok.*;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;
import sw.im.swim.bean.enums.NotiType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.*;

/**
 * <PRE>
 * noti 작업에 필요한 데이터 저장
 * ** nateon팀룸 url,
 * ** slack token, room_id
 * </PRE>
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_noti_group")
public class NotiGroupEntity extends AbstractEntityWithStringPK {

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private NotiType notiType;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 200)
    private String column1;

    @Column(nullable = false, length = 100)
    private String column2;

    @Setter
    @Column(nullable = false, length = 100)
    @Builder.Default
    private Boolean active = true;

    @OneToMany(mappedBy = "notiGroup")
    private List<NotiGroupNodeJoinEntity> groupNodeJoinEntities = new ArrayList<>();

}
