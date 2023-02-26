package sw.im.swim.bean.entity.domain;


import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_domain_info")
public class DomainEntity {

    @Id
    @Column(name = "id", insertable = true, updatable = false)
    @Builder.Default
    protected String id = UUID.randomUUID().toString().replace("-", "");

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

    @Column(nullable = false, length = 60, unique = true)
    private String domain;

    @Column(name = "individual_cert_flag",
            nullable = true)
    @Builder.Default
    private Boolean individualCert = false;

}
