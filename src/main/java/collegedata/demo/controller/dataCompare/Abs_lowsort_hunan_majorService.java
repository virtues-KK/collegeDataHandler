package collegedata.demo.controller.dataCompare;

import com.csvreader.CsvReader;
import lombok.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * author:pan le
 * Date:2019/2/22
 * Time:10:50
 */
public class Abs_lowsort_hunan_majorService {
    final String FilePath = "";
    final static char separator = ',';

    void sort() throws Exception{
        CsvReader csvReader = new CsvReader(FilePath,separator, Charset.forName("UTF-8"));
        csvReader.readHeaders();
        List<Abs_hunan_major> abs_hunan_majors = new ArrayList<>();
        while (csvReader.readRecord()) {
         Abs_hunan_major abs_hunan_major =   Abs_hunan_major.builder()
                    .year(csvReader.get("Year"))
                    .course("Course")
                    .collegeName(csvReader.get("CollegeName"))
                    .majorCode(csvReader.get("MajorCode"))
                    .absSort(csvReader.get("Abs_lowsort"))
                    .lowSort(csvReader.get("LowSort"))
                    .build();
         abs_hunan_majors.add(abs_hunan_major);
        }
        String lowSort_2015 = null;
        String absSort_2015 = null;
        String lowSort_2016 = null;
        String absSort_2016 = null;
        String lowSort_2017 = null;
        String absSort_2017 = null;
        List<Writer> Writers = new ArrayList<>();
        for (Abs_hunan_major abs_hunan_major : abs_hunan_majors) {
            Writer writer1 = new Writer();
            for (Abs_hunan_major abs_hunan_major1 : abs_hunan_majors) {
                if (
                        abs_hunan_major.getCollegeName().equals(abs_hunan_major1.getCollegeName())
                                && abs_hunan_major.getCollegeName().equals(abs_hunan_major1.getCollegeName())
                ) {
                    if (abs_hunan_major1.getYear().equals("2015")) {
                        lowSort_2015 = abs_hunan_major1.getLowSort();
                        absSort_2015 = abs_hunan_major1.getAbsSort();
                    } else if (abs_hunan_major1.getYear().equals("2016")) {
                        lowSort_2016 = abs_hunan_major1.getLowSort();
                        absSort_2016 = abs_hunan_major1.getAbsSort();
                    } else if (abs_hunan_major1.getYear().equals("2017")) {
                        lowSort_2017 = abs_hunan_major1.getLowSort();
                        absSort_2017 = abs_hunan_major1.getAbsSort();
                    }
                    writer1 = Writer.builder()
                            .absSort_2015(absSort_2015)
                            .absSort_2016(absSort_2016)
                            .absSort_2017(absSort_2017)
                            .collegeName(abs_hunan_major.getCollegeName())
                            .majorCode(abs_hunan_major.getMajorCode())
                            .year(abs_hunan_major.getYear())
                            .build();
                }
            }
            Writers.add(writer1);
        }
    }

}

@Builder
@Getter
class Abs_hunan_major{
    private String year;
    private String course;
    private String collegeName;
    private String majorCode;
    private String lowSort;
    private String absSort;
}
@Builder
@Setter
@NoArgsConstructor
class Writer{
    private String year;
    private String course;
    private String collegeName;
    private String majorCode;
    private String lowSort_2015;
    private String absSort_2015;
    private String lowSort_2016;
    private String absSort_2016;
    private String lowSort_2017;
    private String absSort_2017;
}

