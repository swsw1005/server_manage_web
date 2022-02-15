package sw.im.swim.bean.entity.base;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Builder;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", insertable = false, updatable = false)
    protected Long sid;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

    @Column(name = "updated_at", insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar updatedAt;

    @Column(name = "deleted_at", insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar deletedAt;

    /**
     * 자동으로 update 시간 기록해줌
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Calendar.getInstance();
    }

    /**
     * <PRE>
     * 삭제되지 않았다 true
     * 삭제됨 false
     * </PRE>
     *
     * @return
     */
    public boolean isAlive() {
        if (deletedAt == null) {
            return true;
        }
        return false;
    }

    /**
     * <PRE>
     * 삭제처리
     * </PRE>
     */
    public void delete() {
        this.deletedAt = Calendar.getInstance();
    }

}
