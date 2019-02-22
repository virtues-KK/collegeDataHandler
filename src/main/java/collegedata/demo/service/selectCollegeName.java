package collegedata.demo.service;

import com.csvreader.CsvReader;
import org.springframework.stereotype.Service;

import javax.sound.midi.SoundbankResource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:panle
 * Date:2018/12/27
 * Time:15:06
 *  统计所有collegeName 的括号种类
 */
public class selectCollegeName {
    private final String path = "A:\\enroll_college.csv";

    public void get() throws IOException {
        CsvReader csvReader = new CsvReader(path,',', Charset.forName("UTF-8"));
        csvReader.readHeaders();
        Map<String,String> map = new HashMap<>();
        while (csvReader.readRecord()){
            String collegeId = csvReader.get("collegeId");
            String collegeName = csvReader.get("enrollCollegeName");
            // format collegeName
            Matcher matcher = Pattern.compile("(\\(.+\\))+").matcher(collegeName);
            if (matcher.find()){
                matcher.group();
                map.put(matcher.group(),"看看有几种好吧");
            }
        }
        Iterator<String> iterator = map.keySet().iterator();
        System.out.println(map.keySet().size()+"有多少?");
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
