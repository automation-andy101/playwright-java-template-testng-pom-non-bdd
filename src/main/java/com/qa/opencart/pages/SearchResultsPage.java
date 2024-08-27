package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class SearchResultsPage {
    private Page page;
    private String searchResultsHeader = "div#content h1";

    public SearchResultsPage(Page page) {
        this.page = page;
    }

    public String getSearchResultPageTitle() {
        return page.title();
    }

    public String getSearchResultHeaderText() {
        return page.textContent(searchResultsHeader);
    }

}
