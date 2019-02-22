package collegedata.demo.controller.dataCompare;

/**
 * author:panle
 * Date:2018/12/4
 * Time:9:13
 */
public class demo3 {
    public void demo3(){
        String date = "2015";
        String time = "2015";
        if (Integer.valueOf(date)>Integer.valueOf(time)){
            System.out.println(1);
        }
    }

    public static void main(String[] args) {
        new demo3().demo3();
    }
}
