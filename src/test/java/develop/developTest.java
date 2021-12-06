package develop;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static com.google.common.io.Resources.getResource;

public class developTest {
    @Test
    //find way to set the location for uploading pdf file
    public void run(){
        String l=getResource("").getPath();
        System.out.println(l);
        l=l.substring(1);
        System.out.println(l);
        l+="static/download/";
        System.out.println(l);

        String serverPath=Thread.currentThread().getContextClassLoader().getResource("").toString();
        System.out.println(serverPath);
    }
}
