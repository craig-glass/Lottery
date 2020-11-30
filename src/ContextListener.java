/**
 * Destroys any existing files in the encryption folder, upon
 * initialization of server and upon shutdown
 *
 * @author Craig Glass
 * @version 1.0
 * @since 2020-11-05
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class ContextListener implements ServletContextListener {

    // delete files if from directory if any exist
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

    // delete files if from directory if any exist
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
