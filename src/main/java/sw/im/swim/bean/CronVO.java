package sw.im.swim.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CronVO {
    private String cron;
    private String name;
    private int start;
    private int length;
    private String suffix;
}
