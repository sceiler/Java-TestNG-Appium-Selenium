package com.saucelabs.yy.Tests;

public class Constants {
    enum PLATFORM
    {
        WINDOWS10("Windows 10"),
        WINDOWS81("Windows 8.1"),
        WINDOWS8("Windows 8"),
        WINDOWS7("Windows 7"),
        LINUX("Linux"),
        MACOSCATALINA("macOS 10.15");

        String notation;

        PLATFORM(String notation) {
            this.notation = notation;
        }

        public String getNotation() { return notation; }
    }

    enum BROWSER
    {
        CHROME("chrome"),
        FIREFOX("firefox"),
        EDGE("MicrosoftEdge"),
        SAFARI("safari"),
        INTERNETEXPLORER("internet explorer");

        String notation;

        BROWSER(String notation) {
            this.notation = notation;
        }

        public String getNotation() { return notation; }
    }

    enum VERSION
    {
        LATEST("latest"),
        LATEST1("latest-1"),
        BETA("beta"),
        DEV("dev");

        String notation;

        VERSION(String notation) {
            this.notation = notation;
        }

        public String getNotation() { return notation; }
    }
}
