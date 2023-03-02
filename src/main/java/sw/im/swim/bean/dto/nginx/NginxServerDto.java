package sw.im.swim.bean.dto.nginx;

import lombok.Data;
import sw.im.swim.bean.enums.ByteType;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * A DTO for the {@link sw.im.swim.bean.entity.nginx.NginxServerEntity} entity
 */
@Data
public class NginxServerDto implements Serializable {
    private final String id;
    private final Calendar createdAt;
    private final String subDomain;
    private final String RootDomain;
    private final String description;
    private final boolean activate;
    private final String faviconPath;
    private final boolean seperateLog;
    private final int maxBodySize;
    private final ByteType byteType;

    private final List<NginxLocationDto> nginxLocationList;
}