package sw.im.swim.util.nginx;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

public class AskiiArtUtil {

    private final static String sharp_bar = "###########################################################################################################\n#";

    public static final String CREATE_NGINX_BANNER(String input) throws Exception {
        String[] arr = input.split("\\.");
        input = " ";
        for (int i = 0; i < arr.length; i++) {
            if (i != 0) {
                input += " . ";
            }
            input += arr[i];
        }
        return CREATE_WITH_TEXT(sharp_bar, "#", input, 11);
    }


    public static final String CREATE_WITH_TEXT(final String PREFIX_BOX, final String PREFIX_LINE, String input, final int height) throws Exception {

        ArrayList<String> stringList = new ArrayList<>();

        if (PREFIX_BOX != null) {
            stringList.add(PREFIX_BOX);
        }

        try {
            int width = 250;

//            char[] chars = input.toCharArray();
//            input = "";
//            for (int i = 0; i < chars.length; i++) {
//                input += chars[i];
//                input += " ";
//            }

            //BufferedImage image = ImageIO.read(new File("/Users/mkyong/Desktop/logo.jpg"));
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setFont(new Font("SansSerif", Font.TRUETYPE_FONT, height));

            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            graphics.drawString(input, 1, height);

            //save this image
            //ImageIO.write(image, "png", new File("/users/mkyong/ascii-art.png"));

            for (int y = 0; y < height; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < width; x++) {

                    sb.append(image.getRGB(x, y) == -16777216 ? " " : "#");

                }

                if (sb.toString().trim().isEmpty()) {
                    continue;
                }

                if (PREFIX_LINE == null) {
                    stringList.add(sb.toString());
                } else {
                    stringList.add(PREFIX_LINE + sb.toString());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        String output = "";
        for (int i = 0; i < stringList.size(); i++) {
            output += stringList.get(i);
            output += "\n";
        }
        return output;
    }


    public static void main(String[] args) {

        try {
            System.out.println(AskiiArtUtil.CREATE_NGINX_BANNER("192.168.125.253"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
