package collegedata.demo.controller.dataCompare;

import collegedata.demo.entity.PassProbabilityMajorMetadata;
import collegedata.demo.entity.PassProbabilityMetadata;
import collegedata.demo.repository.PassProbabilityMajorMetadataRepo;
import collegedata.demo.repository.PassProbabilityMetadataRepo;
import com.csvreader.CsvReader;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * author:panle
 * Date:2018/12/12
 * Time:15:11
 * 处理大学录取概率中的batchId数据
 */
@Component
public class demo12 {
    @Autowired
    private PassProbabilityMetadataRepo passProbabilityMetadataRepo;

    @Autowired
    private PassProbabilityMajorMetadataRepo passProbabilityMajorMetadataRepo;

    final static String FileName = "C:\\Users\\sunwukong\\Desktop\\test.csv";
    void test() throws Exception{
        List<enrollBatch> list = new ArrayList<>();
        CsvReader Reader = new CsvReader(FileName,',', Charset.forName("GBK"));
        Reader.readHeaders();
        while (Reader.readRecord()) {
                enrollBatch eBatch = enrollBatch.builder().provinceId(Reader.get("province_id"))
                        .enrollBatchId(Reader.get("enrollbatch"))
                        .ppmetadata_batchId(Reader.get("batch_id"))
                        .build();
                list.add(eBatch);
        }
        System.out.println(list.size());

        List<PassProbabilityMetadata> passProbabilityMetadataList =passProbabilityMetadataRepo.findAll();
        System.out.println(passProbabilityMetadataList.size());
        list.forEach(enrollBatch -> {
        //for (int i = 0; i < list.size(); i++) {
           // int finalI = i;
            String provinceId = enrollBatch.getProvinceId();
            String enrollBatchId = enrollBatch.getEnrollBatchId();
            String Ppmetadata_batchId = enrollBatch.getPpmetadata_batchId();
            passProbabilityMetadataList.forEach(passProbabilityMetadata -> {
                if (passProbabilityMetadata.getProvince().getId().toString().equals(provinceId)
                        && passProbabilityMetadata.getBatchId().toString().equals(Ppmetadata_batchId)
                ) {
                    passProbabilityMetadata.setBatchId(Long.valueOf(enrollBatchId));
                    passProbabilityMetadata.setBatchSequence(Integer.valueOf(Ppmetadata_batchId));
                }
            });
        });
        System.out.println(1);
        //passProbabilityMetadataRepo.saveAll(passProbabilityMetadataList);




    }








}
@Data
@Builder
class enrollBatch{
    private String provinceId;
    private String enrollBatchId;
    private String ppmetadata_batchId;
}
