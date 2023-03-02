package sw.im.swim.bean.entity.noti;


import lombok.*;
import sw.im.swim.bean.entity.imple.AbstractEntityWithStringPK;
import sw.im.swim.bean.enums.NotiType;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

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
@Table(name = "tb_noti_group_node_join")
public class NotiGroupNodeJoinEntity extends AbstractEntityWithStringPK {

    @ManyToOne
    @JoinColumn(name = "noti_node_id")
    private NotiNodeEntity notiNode;

    @ManyToOne
    @JoinColumn(name = "noti_group_id")
    private NotiGroupEntity notiGroup;

}
