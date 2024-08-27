package com.qa.opencart.factory;

import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.LaunchOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PlaywrightFactory {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    Properties properties;

    /**
     * This method is used to initialise the browser used to test the application
     *
     * @param properties the content of the config.properties file
     * @return a Page object
     */
    public Page initBrowser(Properties properties) {
        System.out.println("Browser name is : " + properties.getProperty("browser"));

        playwright = Playwright.create();

        switch(properties.getProperty("browser").toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(properties.getProperty("headless"))));
                break;
            case "chrome":
                browser = playwright.chromium().launch(new LaunchOptions().setChannel("chrome").setHeadless(Boolean.parseBoolean(properties.getProperty("headless"))));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(properties.getProperty("headless"))));
                break;
            case "safari":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(properties.getProperty("headless"))));
                break;
            default:
                System.out.println("Please pass a supported browser type......");
                break;
        }

        browserContext = browser.newContext();
        page = browserContext.newPage();
        page.navigate(properties.getProperty("url"));

        return page;
    }

    /**
     * This method is used to initialise the properties from the config file
     *
     * @return the content of the properties file
     */
    public Properties initProperties() {
        try {
            FileInputStream fileInputStream = new FileInputStream("./src/test/resources/config/config.properties");
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;

    }
}
