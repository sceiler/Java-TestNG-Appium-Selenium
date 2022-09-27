# Java-TestNG-Appium-Selenium

A privately owned and maintained repo with test scripts that run on [Sauce Labs](https://saucelabs.com/).
This repo is organized with a tree structure starting mainly with Appium and Selenium but also has examples using
Cucumber
and Sikuli and more.

## Table of Contents

| Appium                                                                                                                  | Selenium                                                                                              |
|-------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| [My Demo App Real Device Android](src/test/java/com/saucelabs/yy/Tests/Appium/MyDemoApp/RealDevice/Android/README.md)   | [Selenium](src/test/java/com/saucelabs/yy/Tests/Selenium/README.md)                                   |
| [My Demo App Real Device iOS](src/test/java/com/saucelabs/yy/Tests/Appium/MyDemoApp/RealDevice/iOS/README.md)           | [Sauce Performance](src/test/java/com/saucelabs/yy/Tests/Selenium/Performance/README.md)              |
| [My Demo App EmuSim Android](src/test/java/com/saucelabs/yy/Tests/Appium/MyDemoApp/EmulatorSimulator/Android/README.md) | [Sauce Visual Testing](src/test/java/com/saucelabs/yy/Tests/VisualTesting/README.md)                  |
| [My Demo App EmuSim iOS](src/test/java/com/saucelabs/yy/Tests/Appium/MyDemoApp/EmulatorSimulator/iOS/README.md)         | [Sikuli](src/test/java/com/saucelabs/yy/Tests/Sikuli/README.md)                                       |
| [Mobile Web Real Device Android](src/test/java/com/saucelabs/yy/Tests/Appium/MobileWeb/RealDevice/Android/README.md)    | [Sauce Extended Debugging](src/test/java/com/saucelabs/yy/Tests/Selenium/ExtendedDebugging/README.md) |
| [Mobile Web Real Device iOS](src/test/java/com/saucelabs/yy/Tests/Appium/MobileWeb/RealDevice/iOS/README.md)            |                                                                                                       |
| [Mobile Web EmuSim Android](src/test/java/com/saucelabs/yy/Tests/Appium/MobileWeb/EmulatorSimulator/Android/README.md)  |                                                                                                       |
| [Mobile Web EmuSim iOS](src/test/java/com/saucelabs/yy/Tests/Appium/MobileWeb/EmulatorSimulator/iOS/README.md)          |                                                                                                       |
| [Cucumber/BDD Real Device](src/test/java/com/saucelabs/yy/cucumber/README.md)                                           |                                                                                                       |

In general, tests can be run by executing the respective config xml file found [here](src/test/resources).
Another way to run these tests as part of a CI/CD pipeline is shown by using GitHub Actions and
Workflows ([Workflows](.github/workflows).)