package com.saucelabs.yy.Tests.Appium.Devices;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;

import java.io.File;

import static org.bytedeco.leptonica.global.lept.pixRead;

public class OCR {

    public String getText(File screenshotFile) {
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();

        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(this.getClass().getClassLoader().getResource("").getPath(), "eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        api.SetDebugVariable("debug_file", "/dev/null");

        // Open input image with leptonica library
        PIX image = pixRead(screenshotFile.getAbsolutePath());
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        String result = outText.getString();
        //System.out.println("OCR output:\n" + outText.getString());

        // Destroy used object and release memory
        api.End();
        outText.deallocate();

        return result;
    }
}
