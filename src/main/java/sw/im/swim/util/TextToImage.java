package sw.im.swim.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * @since 2018. 03. 20
 * @author sunghs.tistory.com, blog.naver.com/sunghs9071
 *
 */
public class TextToImage {

    /**
     * List의 내용을 찍고, 이미지로 저장함
     * 
     * @param text       이미지에 찍을 List 컨텐츠
     * @param fileName   이미지 파일이 저장 될 위치
     * @param fontFamily 찍을 폰트
     * @param fontSize   폰트 크기
     * @throws Exception
     */
    public void convert(List<String> text, String fileName, String fontFamily, int fontSize) throws Exception {

        if (text == null || fileName == null || fontFamily == null) {
            System.out.println("Request Parameter Error");
            return;
        }

        // Declare
        Graphics2D graphics = null;
        BufferedImage bufferedImage = null;
        Font font = null;
        FileOutputStream fos = null;

        // font에 따라 자간이 다르므로 한 글자가 차지하는 픽셀 크기도 다름. fontSize/2라는 수치는 한글자가 갖는 픽셀 추정값인데
        // font 별로 다를 수 있음
        int width = new TextToImage().getMaxLengthTextLine(text) * (fontSize / 1);
        // fontSize와 코드 line 수 만큼 늘어나므로 font 별로 다르지 않음. +3은 line 별 위아래 여유를 주기위해
        int height = (text.size() + 0) * fontSize + 1;

        System.out.println("Image Width(폭-가로) : " + width);
        System.out.println("Image Height(높이-세로) : " + height);

        // Init
        font = new Font(fontFamily, Font.BOLD, fontSize);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        fos = new FileOutputStream(new File(fileName));
        graphics = bufferedImage.createGraphics();

        // Draw - Canvas
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // Draw - Text
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < text.size(); i++) {
            String contents = text.get(i);
            int horizonStart = 2; // 가로 시작위치, 0이 아닌 이유는 여백 주려고
            int verticalStart = (i + 1) * fontSize; // 세로 시작위치, 라인이 넘어갈때마다 fontSize 만큼 아래로 내려가서 찍음, +2는 좀더 여유롭게

            graphics.drawString(contents, horizonStart, verticalStart);
        }

        // Write - Image
        ImageIO.write(bufferedImage, "PNG", fos);
        System.out.println(fileName + " to Image Convert Result : SUCCESS ");
    }

    /**
     * List 컨텐츠에서 가장 긴 줄의 사이즈를 알아냄 <br>
     * 한글의 경우 영어보다 차지하는 픽셀이 크므로, 한글을 발견하면 size를 2 늘림
     * 
     * @param text List 내용
     * @return Integer 제일 긴 라인 사이즈
     */
    private int getMaxLengthTextLine(List<String> text) {

        int maxSize = 0;
        for (int i = 0; i < text.size(); i++) {

            int size = 0;
            for (int j = 0; j < text.get(i).length(); j++) {
                // 한글이면 글자값을 사이즈를 2 더하고, 다른거면 1을 더함
                if (Character.getType(text.get(i).charAt(j)) == Character.OTHER_LETTER)
                    size = size + 2;
                else
                    size = size + 1;
            }
            if (maxSize < size)
                maxSize = size;
        }
        System.out.println("Max Size : " + maxSize);
        return maxSize;
    }

    /**
     * dir내의 모든 파일 리스트를 반환함
     * 
     * @param parentDir 탐색할 폴더위치
     * @return 탐색 폴더 내 모든 파일
     * @throws Exception
     */
    private String[] dirFileList(String parentDir) throws Exception {

        File dir = new File(parentDir);
        File[] fileList = dir.listFiles();

        if (fileList.length >= 1) {
            String[] pathList = new String[fileList.length];

            for (int i = 0; i < fileList.length; i++) {
                String path = fileList[i].getParent();
                String name = fileList[i].getName();
                pathList[i] = path + "\\" + name;
            }
            return pathList;
        } else
            return null;
    }

    /**
     * 파일을 읽어 List에 담아 반환
     * 
     * @param fileName 파일 경로
     * @return 파일 내용 List
     * @throws Exception
     */
    private List<String> readFile(String fileName) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String contents = new String();
        List<String> list = new ArrayList<String>();

        while ((contents = br.readLine()) != null) {
            // drawString 시 tab 문자를 구분하지 못하는 것 같아 SPACE 4칸으로 치환
            contents = contents.replaceAll("\t", "    ");
            list.add(contents);
            System.out.println(contents);
        }
        br.close();
        return list;
    }

    public static void main(String[] args) throws Exception {

        TextToImage converter = new TextToImage();
        String path = "D:/test";

        String[] fileList = converter.dirFileList(path);

        if (fileList != null) {

            for (int i = 0; i < fileList.length; i++) {
                List<String> contents = converter.readFile(fileList[i]);
                String output = fileList[i].split("\\.")[0] + ".png";

                // fontFamily중 한글이 안나오는 폰트가 있으므로 한글이 있다면 한글 지원 되는지 테스트 해보고 써야함
                converter.convert(contents, output, "바탕", 25);
            }
        }
    }

}