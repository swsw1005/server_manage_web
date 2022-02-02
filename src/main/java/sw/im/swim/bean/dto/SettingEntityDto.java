package sw.im.swim.bean.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SettingEntityDto implements Serializable {
    private Long sid;
    private String key;
    private String value;
}
