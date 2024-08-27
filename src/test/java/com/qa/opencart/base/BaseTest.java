package com.qa.opencart.base;

import com.microsoft.playwright.Page;
import com.qa.opencart.factory.PlaywrightFactory;
import com.qa.opencart.pages.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.Properties;

public class BaseTest {
    PlaywrightFactory playwrightFactory;
    Page page;
    protected Properties properties;

    protected HomePage homePage;
    protected LandingPage landingPage;
    protected LoginPage loginPage;
    protected NavigationPage navigationPage;
    protected SearchResultsPage searchResultsPage;


    @BeforeTest
    public void setUp() {
        playwrightFactory = new PlaywrightFactory();
        properties = playwrightFactory.initProperties();
        page = playwrightFactory.initBrowser(properties);
        homePage = new HomePage(page);
        landingPage = new LandingPage(page);
        loginPage = new LoginPage(page);
        navigationPage = new NavigationPage(page);
        searchResultsPage = new SearchResultsPage(page);
    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();
    }
}
