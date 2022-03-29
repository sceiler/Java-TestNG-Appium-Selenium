package com.saucelabs.yy.Tests.Sikuli;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Finder;
import org.sikuli.script.Match;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class LoginTests extends TestBase {

    @Test(dataProvider = "browsers")
    public void clickOnBackpackViaLocalImage(String browser, String version, String os, Method method) throws IOException {
        createDriver(browser, version, os, method.getName());
        annotate("Open saucedemo.com inventory");
        getRemoteWebDriver().get("https://www.saucedemo.com/inventory.html");
        annotate("Check if 'Products' is shown");
        Assert.assertTrue(getRemoteWebDriver().findElement(By.cssSelector(".title")).isDisplayed());

        annotate("Find Sauce Labs Backpack element");
        WebElement element = getRemoteWebDriver().findElement(By.cssSelector("img[alt='Sauce Labs Backpack']"));
        Point point = element.getLocation();
        Dimension dimension = element.getSize();
        System.out.println("Center: " + (point.x + (dimension.width / 2)) + "," + (point.y + (dimension.height / 2)));

        annotate("Try to click on element found by image");
        //Thread.sleep(5000);
        tapOnImage("/Users/yimin.yang/Documents/Sikuli/sauce-backpack.jpg");
        //annotate("Sleep for 5s");
        //Thread.sleep(5000);
        annotate("Take a screenshot");
        getRemoteWebDriver().getScreenshotAs(OutputType.FILE);
        Assert.assertTrue(getRemoteWebDriver().findElement(By.cssSelector(".inventory_details_img")).isDisplayed());
    }

    private BufferedImage takeScreenshot() throws IOException {
        BufferedImage bufferedImage = null;

        if (getRemoteWebDriver() != null) {
            File originalScreenshotFile = getRemoteWebDriver().getScreenshotAs(OutputType.FILE);
            BufferedImage originalImage = ImageIO.read(originalScreenshotFile);
            // convert VM screenshot into jpg and remove alpha color
            BufferedImage newScreenshotImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newScreenshotImage.createGraphics().drawImage(originalImage, 0, 0, Color.WHITE, null);
            bufferedImage = newScreenshotImage;
        }

        return bufferedImage;
    }

    /**
     * @param baseImg       Screenshot of the browser in the VM
     * @param targetImgPath the image on the local file system used to find a matching on the VM
     * @return coordinates of the found and matching element
     */
    private Point2D getCoords(BufferedImage baseImg, String targetImgPath) throws IOException {
        Match m;
        Finder f = new Finder(baseImg);
        Point2D coords = new Point2D.Double(-1, -1);
        BufferedImage targetImg = ImageIO.read(new File(targetImgPath));
        ArrayList<Match> listOfMatches = new ArrayList<>();

        if (!f.hasNext()) {
            // if target image is bigger than base image we need to scale it down otherwise Sikuli will throw an error
            if (targetImg.getHeight() > baseImg.getHeight() || targetImg.getWidth() > baseImg.getWidth()) {
                for (int i = 1; i <= 10; i++) {
                    f.find(rescaleImage(targetImg, 1.0 - ((double) i / 10), 1.0 - ((double) i / 10)));

                    // check if there is a match and break loop if so
                    if (f.hasNext()) {
                        break;
                    } else {
                        System.out.println("No match found.");
                    }
                }
            } else {
                f.find(targetImg);
            }
        }

        // if there are multiple matches add them to list
        while (f.hasNext()) {
            listOfMatches.add(f.next());
        }

        m = listOfMatches.stream().max(Comparator.comparing(Match::getScore)).get();
        coords.setLocation(m.getTarget().getX(), m.getTarget().getY());

        return coords;
    }

    private BufferedImage rescaleImage(BufferedImage image, double fx, double fy) throws IOException {
        Mat matImg = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        matImg.put(0, 0, pixels);
        Mat resizeImage = new Mat();
        Imgproc.resize(matImg, resizeImage, resizeImage.size(), fx, fy, Imgproc.INTER_AREA);

        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", resizeImage, mob);

        return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
    }

    private void tapOnImage(String targetImgPath) throws IOException {
        Point2D coords = getCoords(takeScreenshot(), targetImgPath);
        System.out.println("\nClick X: " + coords.getX() + " Y: " + coords.getY());
        if ((coords.getX() >= 0) && (coords.getY() >= 0)) {
            Actions builder = new Actions(getRemoteWebDriver());
            builder.moveByOffset((int) coords.getX(), (int) coords.getY()).click().build().perform();

            // the above does not work for Safari 14 and macOS Big Sur. the below works for sure
            //getRemoteWebDriver().executeScript("el = document.elementFromPoint(" + (int) coords.getX() + "," + (int) coords.getY() + "); el.click();");
        }
    }
}