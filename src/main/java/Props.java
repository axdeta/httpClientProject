import java.io.FileInputStream;
import java.io.IOException;

public class Props {
    public static String getProperties(String param) {
        java.util.Properties props = new java.util.Properties();
        try {
            props.load(new FileInputStream("src\\config.properties"));
        } catch (IOException e) {
            System.out.println("Error load params");
            e.printStackTrace();
            return null;
        }
        return props.getProperty(param);
    }
}
