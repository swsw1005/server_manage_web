package sw.im.swim.util;

import java.util.Calendar;

import sw.im.swim.bean.entity.AdminEntity;

public class Test {

    public static void main(String[] args) {

        AdminEntity entity = AdminEntity.builder().email("swsw1005").build();

        Calendar a = entity.getCreatedAt();

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println(a);

    }
}
