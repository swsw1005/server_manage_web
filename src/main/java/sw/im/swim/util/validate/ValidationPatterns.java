package sw.im.swim.util.validate;

import lombok.Getter;

import java.util.regex.Pattern;

public enum ValidationPatterns {

    /**
     * <PRE>
     * email
     * </PRE>
     */
    EMAIL(Pattern.compile("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b")),

    /**
     * <PRE>
     * ipv4 address
     * </PRE>
     */
    IPADDRESS(Pattern.compile("\\b(?:(?:2(?:[0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\.){3}(?:(?:2([0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9]))\\b"));

    @Getter
    private Pattern pattern;

    ValidationPatterns(Pattern pattern) {
        this.pattern = pattern;
    }
}
