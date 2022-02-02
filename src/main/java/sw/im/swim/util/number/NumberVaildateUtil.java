package sw.im.swim.util.number;

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


}
