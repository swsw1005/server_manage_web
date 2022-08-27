package sw.im.swim.util.server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class PublicIpInfo {

    // cpu
    private String ip;
    private String city;
    private String region;
    private String country;
    private String loc;
    private String org;
    private String timezone;
    private String postal;



}
