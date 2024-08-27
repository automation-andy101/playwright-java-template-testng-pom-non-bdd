package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.common.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test(priority = 1)
    public void loginPageNavigationTest() {
        loginPage = navigationPage.navigateToLoginPage();
        Assert.assertEquals(loginPage.getLoginPageTitle(), AppConstants.LOGIN_PAGE_TITLE);
    }

    @Test(priority = 2)
    public void forgotPasswordLinkExistsTest() {
        loginPage = navigationPage.navigateToLoginPage();
        Assert.assertTrue(loginPage.doesForgotPasswordLinkExist());
    }

    @Test(priority = 3)
    public void loginUserTest() {
        loginPage = navigationPage.navigateToLoginPage();
        landingPage = loginPage.login(properties.getProperty("username").trim(), properties.getProperty("password").trim());
        Assert.assertEquals(landingPage.getLandingPageTitle(), AppConstants.LANDING_PAGE_TITLE);
    }
}
