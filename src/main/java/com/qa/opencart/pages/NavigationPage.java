package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class NavigationPage {
    private Page page;
    private String myAccountMenuOption = "//span[normalize-space()='My Account']";
    private String loginMenuOption = "a:text('Login')";

    public NavigationPage(Page page) {
        this.page = page;
    }

    public void clickMyAccountMenuOption() {
        page.click(myAccountMenuOption);
    }

    public void clickLoginMenuOption() {
        page.click(loginMenuOption);
    }

    public LoginPage navigateToLoginPage() {
        clickMyAccountMenuOption();
        clickLoginMenuOption();
        return new LoginPage(page);
    }


}
