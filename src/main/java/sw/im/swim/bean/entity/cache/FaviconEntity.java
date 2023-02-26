package sw.im.swim.bean.entity.cache;


import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_favicon_info")
public class FaviconEntity {

    @Id
    @Column(name = "id", insertable = true, updatable = false)
    @Builder.Default
    protected String id = UUID.randomUUID().toString().replace("-", "");

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    protected Calendar createdAt = Calendar.getInstance();

    @Column(nullable = false, length = 60, unique = true)
    private String path;

    @Column(nullable = true, length = 90)
    private String description;

    @Column(nullable = true)
    @Builder.Default
    private Boolean exist = true;

}
