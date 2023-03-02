package sw.im.swim.bean.dto.nginx;

import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;

/**
 * A DTO for the {@link sw.im.swim.bean.entity.nginx.NginxUpstreamEntity} entity
 */
@Data
public class NginxUpstreamDto implements Serializable {
    private final String id;
    private final Calendar createdAt;
    private final boolean https;
    private final String ip;
    private final Integer port;
    private final boolean activate;
}