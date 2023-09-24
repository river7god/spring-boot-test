package com.rozwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import com.rozwork.eor.service.mkitpay.MkitPayResultListener;

/**
 * 启动程序
 * 
 * @author rozwork
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RozWorkApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication springApplication = new SpringApplication(RozWorkApplication.class);
       //@tempcam
        // springApplication.addListeners(new MkitPayResultListener());
        SpringApplication.run(RozWorkApplication.class, args);
        
        System.out.println("MKIT  启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}
