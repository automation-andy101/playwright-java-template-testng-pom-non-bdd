package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.common.AppConstants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void homePageTitleTest() {
        String actualTitle = homePage.getHomePageTitle();
        Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
    }

    @Test
    public void homePageUrlTest() {
        String actualUrl = homePage.getHomePageUrl();
        Assert.assertEquals(actualUrl, properties.getProperty("url"));
    }

    @DataProvider
    public Object[][] getProductData() {
        return new Object[][] {
                {"macbook"},
                {"iMac"},
                {"Samsung"}
        };
    }

    @Test(dataProvider = "getProductData")
    public void searchTest(String productName) {
        homePage.performASearch(productName);
        String actualSearchHeaderText = searchResultsPage.getSearchResultHeaderText();
        Assert.assertEquals(actualSearchHeaderText, "Search - " + productName);
    }
}
