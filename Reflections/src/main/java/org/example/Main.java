package org.example;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String codeTemplate = "package org.example; public class DynamicEval {public  Object getResult(){return %s;}}";

        Scanner sc = new Scanner(System.in);
        String userExpression = sc.nextLine();

        String codeWithUserExpression = String.format(codeTemplate, userExpression);

        String filePath = "./target/classes/org/example/DynamicEval.java";

        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(filePath));

            writer.println(codeWithUserExpression);
            writer.close();

            com.sun.tools.javac.Main.compile(new String[]{filePath});

            Class<?> dynamicEvalClass = Class.forName("org.example.DynamicEval");

            Object dynamicEvalInstance = dynamicEvalClass.getDeclaredConstructor().newInstance();

            Object result = dynamicEvalClass.getMethod("getResult").invoke(dynamicEvalInstance);

            System.out.println("Результат выполнения: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}