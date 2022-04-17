package sw.im.swim.util.nginx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AskiiArtUtil {

    private final static String sharp_bar = "###########################################################################################################\n#";

    public static final List<String> CREATE_NGINX_BANNER(String input) throws Exception {
        String[] arr = input.split("\\.");
        input = " ";
        for (int i = 0; i < arr.length; i++) {
            if (i != 0) {
                input += " . ";
            }
            input += arr[i];
        }
        List<String> list = CREATE_WITH_TEXT(sharp_bar, "#", input, 15);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(list.get(i).trim());
        }
        return result;
    }


    public static final List<String> CREATE_WITH_TEXT(final String PREFIX_BOX, final String PREFIX_LINE, String input, final int height) throws Exception {

        List<String> stringList = new ArrayList<>();

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
            g.setFont(new Font("SansSerif", Font.TRUETYPE_FONT, height - 3));

            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            graphics.drawString(input, 1, height - 1);

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

            if (PREFIX_LINE == null) {
            } else {
                stringList.add(PREFIX_LINE);
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
        return stringList;
    }


    public static void main(String[] args) {

        try {
            List<String> list = AskiiArtUtil.CREATE_NGINX_BANNER("앓옳옳옭앜ㅋㅋ");

            for (int i = 0; i < list.size(); i++) {
                log.debug(list.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
