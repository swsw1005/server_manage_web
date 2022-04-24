package sw.im.swim.bean.dto;

import lombok.Getter;
import lombok.Setter;
import sw.im.swim.bean.enums.JailType;
import sw.im.swim.bean.enums.JobType;

import java.io.Serializable;
import java.util.Calendar;

@Getter
@Setter
public class Fail2banLogEntityDto implements Serializable {
    private Long sid;
    private Calendar createdAt = null;
    private JailType jailType;
    private JobType jobType;
    private String ip;
    private String server;
    private String country;

    private String token = "token";
}
