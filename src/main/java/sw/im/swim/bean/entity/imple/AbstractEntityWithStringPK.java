package sw.im.swim.bean.entity.imple;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@MappedSuperclass
public class AbstractEntityWithStringPK {

    @Comment("[PK] uuid string pk")
    @Id
    @Column(name = "id", insertable = true, updatable = false, length = 100)
    @Builder.Default
    protected String id = UUID.randomUUID().toString().replace("-", "");

//    @Comment("[PK] uuid string pk")
//    @Id
//    @Column(name = "id", insertable = false, updatable = false)
//    protected String id;

    @Comment("해당 row insert 시간")
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE default now()")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();
}
