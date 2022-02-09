package sw.im.swim.bean.dto;

import lombok.Data;
import sw.im.swim.config.GeneralConfig;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Data
public class NginxPolicyEntityDto implements Serializable {
    private Long sid;
    private Calendar createdAt;
    private Calendar updatedAt;
    private Calendar deletedAt;
    private String name;
    private DomainEntityDto domainEntity;
    private int workerProcessed;
    private int workerConnections;
    private List<NginxServerEntityDto> nginxServerEntities;

    private String nginxServerSids = "";

    public String getNginxServerSids() {
        if (nginxServerSids == null) {
            this.nginxServerSids = "";
        }
        return nginxServerSids;
    }

    public void setNginxServerSids(String nginxServerSids) {
        this.nginxServerSids = nginxServerSids;
        if (nginxServerSids == null) {
            this.nginxServerSids = "";
        }
    }

    public String getCreated() {
        try {
            createdAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(createdAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }

    public String getUpdated() {
        try {
            updatedAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(updatedAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }

    public String getDeleted() {
        try {
            deletedAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(deletedAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }


}
