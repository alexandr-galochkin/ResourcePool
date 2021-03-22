import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuadraticEquation implements Runnable {
    private final int a, b, c;
    private List<Double> solutions;
    private final BufferedWriter outputStream;

    QuadraticEquation(int a, int b, int c, BufferedWriter outputStream) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        execute();
        synchronized (outputStream) {
            try {
                outputStream.write(this.toString() + "\n");
                if (solutions.size() == 0) {
                    outputStream.write("Либо корней нет, либо их бесконечно много.\n");
                } else if (solutions.size() == 1) {
                    outputStream.write("Один корень: " + solutions.get(0).toString() + "\n");
                } else if (solutions.size() == 2) {
                    outputStream.write("Два корня: " + solutions.get(0).toString() + "; " + solutions.get(1) + "\n");
                }
                outputStream.flush();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void execute() {
        solutions = new ArrayList<Double>();
        if (a == 0) {
            if (b == 0) {
                return;
            }
            solutions.add((double) -c / b);
            return;
        }
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return;
        }
        if (discriminant == 0) {
            solutions.add((double) -b / 2 / a);
            return;
        }
        solutions.add((-b - discriminant) / 2 / a);
        solutions.add(((-b + discriminant) / 2 / a));
    }

    @Override
    public String toString() {
        String bToStr = String.format("%+d", b);
        String cToStr = String.format("%+d", c);
        return (a + "*x^2" + bToStr + "*x" + cToStr + "=0");
    }
}
