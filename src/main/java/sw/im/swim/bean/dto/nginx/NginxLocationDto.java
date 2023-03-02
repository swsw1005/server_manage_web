package sw.im.swim.bean.dto.nginx;

import lombok.Data;
import sw.im.swim.bean.enums.LBType;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * A DTO for the {@link sw.im.swim.bean.entity.nginx.NginxLocationEntity} entity
 */
@Data
public class NginxLocationDto implements Serializable {
    private final String id;
    private final Calendar createdAt;
    private final int priority;
    private final String location;
    private final LBType lbType;
    private final boolean activate;

    private final List<NginxUpstreamDto> upstreamList;
}