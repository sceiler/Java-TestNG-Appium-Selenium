package com.saucelabs.yy.Tests.Appium.SauceConnect;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class SauceConnectHelper {

    private ThreadLocal<Process> process = new ThreadLocal<>();
    private ThreadLocal<Thread> thread = new ThreadLocal<>();

    public Process getProcess() {
        return process.get();
    }

    public Thread getThread() {
        return thread.get();
    }

    public void stopSauceConnect() {
        //getProcess().destroy();
        getThread().stop();
    }

    public void startSauceConnect() {
        runSauceConnect();
        getThread().start();
    }

    private void runSauceConnect() {
        thread.set(new Thread(() -> {
            File pathToExecutable = new File("src/test/resources/sc");
            ProcessBuilder builder = new ProcessBuilder(pathToExecutable.getAbsolutePath(), "-i", "myTunnel", "-x", "https://eu-central-1.saucelabs.com/rest/v1");
            builder.directory(new File("src/test/resources").getAbsoluteFile()); // this is where you set the root folder for the executable to run with
            builder.redirectErrorStream(true);
            try {
                process.set(builder.start());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scanner s = new Scanner(getProcess().getInputStream());
            StringBuilder text = new StringBuilder();
            while (s.hasNextLine()) {
                text.append(s.nextLine());
                text.append("\n");
            }
            s.close();

            //int result = getProcess().waitFor();

            //System.out.printf("Process exited with result %d and output %s%n", result, text);
        }));
    }
}
