package sw.im.swim.bean.entity.admin;


import lombok.*;
import sw.im.swim.bean.enums.NotiType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

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
@Table(name = "tb_noti_info")
public class NotiEntity {

    @Id
    @Column(name = "id", insertable = true, updatable = false)
    @Builder.Default
    protected String id = UUID.randomUUID().toString().replace("-", "");

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

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

}
