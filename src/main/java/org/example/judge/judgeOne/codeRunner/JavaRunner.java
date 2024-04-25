package org.example.judge.judgeOne.codeRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;


public class JavaRunner implements CodeRunner{
    @Override
    public boolean runCode(String path, String[] sample, String name, int limit) {
        String [] input = sample[0].split(" ");
        int expect = Integer.parseInt(sample[1]);

        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", path, name, input[0], input[1]);
            pb.redirectErrorStream(true); // 将错误输出重定向到标准输出流
            Process process = pb.start();

            boolean finished = process.waitFor(limit, TimeUnit.MILLISECONDS);
            if (!finished) {
                process.destroy(); // 确保杀死进程
                return false;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    if (Integer.parseInt(line) != expect) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;

                }
            }
        } catch (IOException | InterruptedException e) {
            return false;
        }
        return true;
    }
}
