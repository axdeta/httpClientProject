import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RepositoryTest {

    @Test
    public void getCount() {
        Repository repository = new Repository();
        Assert.assertEquals(0, repository.getCount());
    }

    @Test
    public void countUp() {
        Repository repository = new Repository();

        int before = repository.getCount(); // COUNT BEFORE
        Assert.assertTrue(repository.countUp());  // CLICK UP
        int after = repository.getCount(); //COUNT AFTER
        Assert.assertEquals(before + 1, after);
    }
}