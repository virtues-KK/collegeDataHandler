package collegedata.demo.controller;

import org.aspectj.util.FileUtil;

import java.io.File;

/**
 * author:panle
 * Date:2018/11/20
 * Time:16:52
 */
public class demo {
    public static void main(String[] args) {
        File dir = new File("A:\\learn-project\\大学数据处理\\src\\main\\resources");
        for (File file : dir.listFiles()){
            System.out.println(file.getName());
            if (file.getName().endsWith("result")){
                file.renameTo(new File(file.getPath().replace("result","")));
            }else {
                file.delete();
            }
        }
    }
}
