package sw.im.swim.bean.entity.admin;

import java.util.Calendar;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sw.im.swim.bean.enums.AdminLogType;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_admin_log")
public class AdminLogEntity {

    @Id
    @Column(name = "id", insertable = true, updatable = false)
    @Builder.Default
    protected String id = UUID.randomUUID().toString().replace("-", "");

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

    @Column(nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private AdminLogType title;

    @Column(nullable = false, length = 60)
    private String message1;

    @Column(nullable = false, length = 600)
    private String message2;

}
