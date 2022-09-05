package sw.im.swim.util.validate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.InvalidParameterException;
import java.util.regex.Matcher;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationHelper {

//    public static String REGEX_EMAIL = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";

//    public static String REGEX_IPADDRESS = "\\b(?:(?:2(?:[0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\.){3}(?:(?:2([0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9]))\\b";


    public static boolean isMatch(final String input, final ValidationPatterns validationPatterns) {
        nullCheck(input, validationPatterns);
        Matcher matcher = validationPatterns.getPattern().matcher(input);
        final boolean var1 = matcher.matches();
        return var1;
    }


    public static boolean isContain(final String input, final ValidationPatterns validationPatterns) {
        nullCheck(input, validationPatterns);
        Matcher matcher = validationPatterns.getPattern().matcher(input);
        final boolean var1 = matcher.find();
        return var1;
    }

    public static String extract(final String input, final ValidationPatterns validationPatterns) {
        nullCheck(input, validationPatterns);
        Matcher matcher = validationPatterns.getPattern().matcher(input);
        final boolean var1 = matcher.find();
        if (var1) {
            return input.substring(matcher.start(), matcher.end());
        }
        return null;
    }

    private static void nullCheck(String input, ValidationPatterns validationPatterns) {
        if (input == null) {
            throw new InvalidParameterException("ValidationHelper : input is null");
        }
        if (validationPatterns == null) {
            throw new InvalidParameterException("vValidationHelper : alidationPatterns is null");
        }
        log.debug("pattern :: " + validationPatterns.name());
    }


}
