import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropsTest {

    @Test
    public void getProperties() {
        Assert.assertEquals("jdbc:postgresql://localhost:5432/counter", Props.getProperties("DB_URL"));
        Assert.assertEquals("user", Props.getProperties("USER"));
        Assert.assertEquals("password", Props.getProperties("PASS"));
        Assert.assertNull(Props.getProperties("wrong_param"));
    }
}