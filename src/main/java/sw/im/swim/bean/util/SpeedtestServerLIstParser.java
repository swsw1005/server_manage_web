package sw.im.swim.bean.util;


public class SpeedtestServerLIstParser {


    public static String[] parse(String input) {
//        String input = "6527) kdatacenter.com (Seoul, South Korea) [2.21 km]";
        String[] arr = input.split("[()]");

        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim();
        }

        String[] arr2 = arr[2].split(",");

        arr[2] = arr2[0].trim();
        arr[3] = arr2[1].trim();

        return arr;
    }


}
