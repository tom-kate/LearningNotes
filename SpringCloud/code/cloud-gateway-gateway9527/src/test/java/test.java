import java.time.ZonedDateTime;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/7 16:57
 */
public class test {
    public static void main(String[] args) {
        //获取地区时区时间信息
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
        //2021-01-07T16:58:35.812+08:00[Asia/Shanghai]
    }
}
