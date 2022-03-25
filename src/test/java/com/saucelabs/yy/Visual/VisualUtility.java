package com.saucelabs.yy.Visual;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VisualUtility {

    private File screenshot;
    private CapabilitiesModel capabilitiesModel;

    public VisualUtility() {
        // empty ctor
    }

    public VisualUtility(File screenshot, Capabilities capabilities) {
        this.screenshot = screenshot;
        this.capabilitiesModel = new CapabilitiesModel(capabilities);
    }

    public File getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(File screenshot) {
        this.screenshot = screenshot;
    }

    public boolean init() {
        Path path = Paths.get(Constants.RESULT_DIRECTORY);

        if (Files.exists(path) && Files.isDirectory(path) && Files.isReadable(path)) {
            System.out.println("Result directory " + path.toAbsolutePath() + " exists");
            return true;
        } else {
            if (!Files.isReadable(path)) {
                System.out.println("Directory is not readable/accessible. Check your permissions.");
            } else if (!Files.exists(path) || !Files.isDirectory(path)) {
                System.out.println("Directory not existing, trying to create it");
                try {
                    if (new File(path.toString()).mkdirs()) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Compares the provided screenshot in the constructor with a baseline image. If there is no baseline then the
     * provided screenshot will be set as the baseline.
     *
     * @return true if both screenshots are the same
     */
    public boolean compare() throws IOException {
        File file = new File(Constants.RESULT_DIRECTORY + capabilitiesModel.getDeviceName());

        if (!file.exists()) {
            file.mkdirs();
            createBaseline();
        } else if (file.list().length == 0) {
            createBaseline();
        }

        return true;
    }

    private void createBaseline() throws IOException {
        File directoryPath = new File(Constants.RESULT_DIRECTORY + capabilitiesModel.getDeviceName());

        FileUtils.copyFile(screenshot, new File(Constants.RESULT_DIRECTORY + capabilitiesModel.getDeviceName() + "_baseline.png"));
    }
}
