package sw.im.swim.bean.dto;

import lombok.Data;
import sw.im.swim.bean.enums.JobType;

import java.io.Serializable;

@Data
public class NotiSettingEntityDto implements Serializable {
    private Long sid;
    private String key;
    private JobType jobType;
    private String value;
}
