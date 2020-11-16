import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0){
        File dir = new File("C:\\Users\\cglas\\ComputerScience\\Stage_2\\" +
                "Security\\Assignment\\CSC2031 Coursework\\" +
                "LotteryWebApp\\EncryptedFiles");

        if(dir.exists()){
            for(File file : dir.listFiles()){
                if(!file.isDirectory()){
                    file.delete();
                }

            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0){

        File dir = new File("C:\\Users\\cglas\\ComputerScience\\Stage_2\\" +
                "Security\\Assignment\\CSC2031 Coursework\\" +
                "LotteryWebApp\\EncryptedFiles");

        if(dir.exists()){
            for(File file : dir.listFiles()){
                if(!file.isDirectory()){
                    file.delete();
                }

            }
        }
    }
}
