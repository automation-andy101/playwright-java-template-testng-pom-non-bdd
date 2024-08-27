package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private Page page;
    private String emailInput = "#input-email";
    private String passwordInput = "#input-password";
    private String loginButton = "//input[@value='Login']";
    private String forgotPasswordLink = "//div[@class='form-group']//a[normalize-space()='Forgotten Password']";

    public LoginPage(Page page) {
        this.page = page;
    }
    
    public void enterEmail(String email) {
        page.fill(emailInput, email);
    }

    public void enterPassword(String password) {
        page.fill(passwordInput, password);
    }

    public void clickLoginButton() {
        page.click(loginButton);
    }

    public String getLoginPageTitle() {
        return page.title();
    }

    public boolean doesForgotPasswordLinkExist() {
        return page.isVisible(forgotPasswordLink);
    }

    public LandingPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();

        return new LandingPage(page);
    }

}
