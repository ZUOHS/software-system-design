package org.example.judge.judgeOne.codeRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JavaRunner implements CodeRunner{
    @Override
    public boolean runCode(String path, List<String []> samples, String name) {
        for (String[] sample : samples) {
            String [] input = sample[0].split(" ");
            int expect = Integer.parseInt(sample[1]);
            System.out.println(path);

            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", path, name, input[0], input[1]);
                pb.redirectErrorStream(true); // 将错误输出重定向到标准输出流
                Process process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (Integer.parseInt(line) != expect) {
                        return false;
                    }
                    System.out.println(line);
                }
                int exitCode = process.waitFor();
                System.out.println("Exited with code: " + exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
