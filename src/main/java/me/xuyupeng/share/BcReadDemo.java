package me.xuyupeng.share;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 字节码读取示例
 *
 * @author : xuyupeng
 * @date 2020/3/21 13:32
 */
public class BcReadDemo {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        InputStream inputStream = null;
        {
            try {
                inputStream = new FileInputStream(BcReadDemo.class.getResource("").getPath() + "/DemoClazz.class");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (null != inputStream) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            int tempByte;
            while (-1 != (tempByte = bufferedInputStream.read())) {
                System.out.printf("%02x", tempByte);
            }
        }
    }
}
