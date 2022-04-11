package sw.im.swim.util.number;

import java.text.DecimalFormat;

public class NumberVaildateUtil {

    public static final int validateBetween(int input, int min, int max) {
        if (input < min) {
            return min;
        }
        if (input > max) {
            return max;
        }
        return input;
    }


    private static DecimalFormat oneDecimal = new DecimalFormat("0.0");

    public static String humanReadableInt(long number) {
        long absNumber = Math.abs(number);
        double result = number;
        String suffix = "";
        if (absNumber < 1024) {
            // nothing
        } else if (absNumber < 1024 * 1024) {
            result = number / 1024.0;
            suffix = "k";
        } else if (absNumber < 1024 * 1024 * 1024) {
            result = number / (1024.0 * 1024);
            suffix = "m";
        } else {
            result = number / (1024.0 * 1024 * 1024);
            suffix = "g";
        }
        return oneDecimal.format(result) + suffix;
    }


}
