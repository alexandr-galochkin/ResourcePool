import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class MyResourcePoolTest {
    @Test
    public void onCreating(){
        MyResourcePool<QuadraticEquation> resourcePool= new MyResourcePool<>(10, 10);
        Assert.assertEquals(10, resourcePool.getNumberOfResources());
    }

    @Test
    public void increasePoolCapacity(){
        MyResourcePool<QuadraticEquation> resourcePool= new MyResourcePool<>(10, 10);
        Assert.assertEquals(10, resourcePool.getNumberOfResources());
        resourcePool.increasePoolCapacity(3);
        Assert.assertEquals(13, resourcePool.getNumberOfResources());
    }

    @Test
    public void execute(){
        MyResourcePool<QuadraticEquation> resourcePool= new MyResourcePool<>(10, 10);
        File solutions = new File("src//main//test//testResources//solutions.txt");
        try {
            BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(solutions)));
            for (int i = 0; i < 100; i ++){
                QuadraticEquation newEquation = new QuadraticEquation(1, -2, 1, outputStream);
                resourcePool.execute(newEquation);
            }
            resourcePool.waitAllResources();
            BufferedReader readerSolutions = new BufferedReader(new FileReader(solutions));
            for(int i = 0; i < 100; i++){
                String nextEquals = readerSolutions.readLine();
                String nextSolutions = readerSolutions.readLine();
                Assert.assertEquals("1*x^2-2*x+1=0", nextEquals);
                Assert.assertEquals("Один корень: 1.0", nextSolutions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
