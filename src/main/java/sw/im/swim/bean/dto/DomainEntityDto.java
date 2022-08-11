package sw.im.swim.bean.dto;

import lombok.Data;
import org.springframework.util.Assert;
import sw.im.swim.config.GeneralConfig;

import java.io.Serializable;
import java.util.Calendar;

@Data
public class DomainEntityDto implements Serializable {
    private Long sid;
    private Calendar createdAt;
    private String domain;

    public String getCreated() {
        try {
            createdAt.setTimeZone(GeneralConfig.TIME_ZONE);
            return GeneralConfig.SIMPLE_DATE_FORMAT.format(createdAt.getTime());
        } catch (Exception e) {
        }
        return "-";
    }

    private Boolean individualCert = false;

    public String getAbsoluteDomain() {
        try {
            final String ROOT_DOMAIN_NAME = GeneralConfig.ADMIN_SETTING.getROOT_DOMAIN();

            Assert.isTrue(ROOT_DOMAIN_NAME.length() > 4, "root domain too short");

            String DOMAIN = String.valueOf(this.domain).trim();

            final boolean isDomainContain = DOMAIN.contains(ROOT_DOMAIN_NAME);

            if (isDomainContain) {
                final int idx = DOMAIN.indexOf(ROOT_DOMAIN_NAME);
                final String pre = DOMAIN.substring(0, idx);
                DOMAIN = pre + ROOT_DOMAIN_NAME;
            } else {
                DOMAIN += "." + ROOT_DOMAIN_NAME;
            }
            return DOMAIN;
        } catch (Exception e) {
        }
        return domain.trim();
    }

    public String getCertDomain() {
        final String ROOT_DOMAIN_NAME = GeneralConfig.ADMIN_SETTING.getROOT_DOMAIN();
        if (this.individualCert == null) {
            return "*." + ROOT_DOMAIN_NAME;
        } else if (this.individualCert == true) {
            return getAbsoluteDomain();
        } else {
            return "*." + ROOT_DOMAIN_NAME;
        }
    }

}
