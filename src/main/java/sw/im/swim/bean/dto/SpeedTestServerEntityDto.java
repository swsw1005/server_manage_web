package sw.im.swim.bean.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SpeedTestServerEntityDto implements Serializable {
    private Long sid;
    private int serverId;
    private String name;
    private String country;
    private String city;
    private double latitude;
    private double longitude;
    private String sponsor;
    private String host;
    private String url;
    private double latency;
}
