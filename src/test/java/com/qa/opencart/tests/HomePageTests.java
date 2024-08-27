package com.qa.opencart.tests;

import com.microsoft.playwright.Page;
import com.qa.opencart.factory.PlaywrightFactory;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HomePageTests {
    PlaywrightFactory playwrightFactory;
    Page page;
    HomePage homePage;
    SearchResultsPage searchResultsPage;

    @BeforeTest
    public void setUp() {
        playwrightFactory = new PlaywrightFactory();
        page = playwrightFactory.initBrowser("chrome");
        homePage = new HomePage(page);
        searchResultsPage = new SearchResultsPage(page);
    }

    @Test
    public void homePageTitleTest() {
        String actualTitle = homePage.getHomePageTitle();
        Assert.assertEquals(actualTitle, "Your Store");
    }

    @Test
    public void homePageUrlTest() {
        String actualUrl = homePage.getHomePageUrl();
        Assert.assertEquals(actualUrl, "https://naveenautomationlabs.com/opencart/");
    }

    @Test
    public void searchTest() {
        homePage.performASearch("MacBook");
        Assert.assertEquals(searchResultsPage.getSearchResultHeaderText(), "Search - macbook");
    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();
    }
}
