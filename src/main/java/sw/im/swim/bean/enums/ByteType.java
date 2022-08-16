package sw.im.swim.bean.enums;

import lombok.Getter;

public enum ByteType {
    KB("K"),
    MB("M"),
    GB("G");

    @Getter
    private String suffix;

    ByteType(String suffix) {
        this.suffix = suffix;
    }
}
