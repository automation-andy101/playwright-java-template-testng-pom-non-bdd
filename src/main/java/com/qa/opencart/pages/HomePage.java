package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class HomePage {
    private Page page;
    private String searchInput = "input[name='search']";
    private String searchButton = "div#search button";

    public HomePage(Page page) {
        this.page = page;
    }

    public String getHomePageTitle() {
        return page.title();
    }

    public String getHomePageUrl() {
        return page.url();
    }

    public void performASearch(String searchTerm) {
        page.fill(searchInput, searchTerm);
        page.click(searchButton);
    }

}
