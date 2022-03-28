package com.saucelabs.yy.Tests.Selenium;

public class Constants {
    public enum PLATFORM {
        WINDOWS11("Windows 11"),
        WINDOWS10("Windows 10"),
        WINDOWS81("Windows 8.1"),
        WINDOWS8("Windows 8"),
        WINDOWS7("Windows 7"),
        LINUX("Linux"),
        OSXYOSEMITE("OS X 10.10"),
        OSXELCAPITAN("OS X 10.11"),
        MACOSSIERRA("macOS 10.12"),
        MACOSHIGHSIERRA("macOS 10.13"),
        MACOSMOJAVE("macOS 10.14"),
        MACOSCATALINA("macOS 10.15"),
        MACOSBIGSUR("macOS 11.00"),
        MACOSMONTEREY("macOS 12");

        String label;

        PLATFORM(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }
    }

    public enum BROWSER {
        CHROME("chrome"),
        FIREFOX("firefox"),
        MICROSOFTEDGE("MicrosoftEdge"),
        SAFARI("safari"),
        INTERNETEXPLORER("internet explorer");

        String label;

        BROWSER(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }
    }

    public enum VERSION {
        LATEST("latest"),
        LATEST1("latest-1"),
        LATEST2("latest-2"),
        LATEST3("latest-3"),
        LATEST4("latest-4"),
        BETA("beta"),
        DEV("dev");

        String label;

        VERSION(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }
    }
}
