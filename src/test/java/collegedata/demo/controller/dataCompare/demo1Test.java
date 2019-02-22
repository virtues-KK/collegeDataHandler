package collegedata.demo.controller.dataCompare;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class demo1Test {

//    @Autowired
//    private demo1 demo1;

    @Autowired
    private demo2 demo2;


//    @Autowired
//    private demo8 demo8;
//
//    @Autowired
//    private demo10 demo10;

    @Autowired
    private demo12 demo12;

//    @Test
//    public void test() throws Exception {
//        demo1.read();
//    }

    @Test
    public void TEST() throws Exception {
        //demo2.read();
        //new demo4().deleteOthers();
        //demo5.demo5();
       // demo11.test();
        demo12.test();
    }

    @Test
    public void Test2() throws Exception {
    }

}