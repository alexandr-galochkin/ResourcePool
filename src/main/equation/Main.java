package main.equation;

import main.myResourcePool.MyResourcePool;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        File params = new File("src//main//resources//paramsForEquations.txt");
        File solutions = new File("src//main//resources//solutions.txt");
        MyResourcePool<QuadraticEquation> resourcePool= new MyResourcePool<>(10);
        try {
            BufferedReader readerParams = new BufferedReader(new FileReader(params));
            BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(solutions)));
            for (int i = 0; i < 1000; i ++){
                String[] paramsArr = readerParams.readLine().split(" ");
                int a = Integer.parseInt(paramsArr[0]);
                int b = Integer.parseInt(paramsArr[1]);
                int c = Integer.parseInt(paramsArr[2]);
                QuadraticEquation newEquation = new QuadraticEquation(a, b, c, outputStream);
                resourcePool.execute(newEquation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
