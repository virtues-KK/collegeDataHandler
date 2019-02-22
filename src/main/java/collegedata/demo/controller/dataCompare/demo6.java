package collegedata.demo.controller.dataCompare;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:panle
 * Date:2018/12/6
 * Time:9:14
 */
public class demo6 {

    public static void main(String[] args) {
//        String[] aa = {"1","2"};
//        String[] str = new String[aa.length +1];
//
//        System.arraycopy(aa,0, str,1,aa.length);
//        str[0] = "index";
//        System.out.println(str[0]);

        String a = "文科sss(文科)(理科)(理科)";
        Matcher matcher1 = Pattern.compile("^.+?(?=\\(.*?\\))+|.+").matcher(a);
        if (matcher1.find()){
            System.out.println(matcher1.group());
        }



    }




}
