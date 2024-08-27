package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class LandingPage {
    private Page page;

    public LandingPage(Page page) {
        this.page = page;
    }

    public String getLandingPageTitle() {
        return page.title();
    }

}
