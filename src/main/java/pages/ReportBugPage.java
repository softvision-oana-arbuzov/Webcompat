package pages;

import helpers.WebElementHelper;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Date;

import static helpers.WebElementHelper.*;

public class ReportBugPage extends BasePage {

    public final static String URL = "https://staging.webcompat.com/issues/new";

    @FindBy(id = "url")
    private WebElement siteURL;

    @FindBy(xpath = "/html/body/main/section[2]/form/div[1]/div[2]/div/div/button")
    private WebElement confirmURLButton;

    @FindBy(className = "next-step step-3 issue-btn")
    private WebElement confirmSomethingElseButton;

    @FindBy(className = "page-heading")
    private WebElement pageHeading;

    @FindBy(className = "input-description")
    private WebElement typeURLDescription;

    @FindBy(xpath = "/html/body/main/section[2]/form/div[3]/div[1]/div/div/div/label")
    private WebElement pageProblem;

    @Override
    protected boolean isCurrent() {
        return areVisible(siteURL);
    }

    @Override
    protected boolean isValid() {
        return areVisible(siteURL);
    }

    public ReportBugPage(WebDriver driver) {
        super(driver);
    }

    public boolean getPageHeading() {
        return areVisible(pageHeading);
    }

    public boolean getTypeURLDescription() {
        return areVisible(typeURLDescription);
    }

    public void typeSiteURL(String website) {
        setFieldValue(siteURL, website);
    }

    public void clickConfirmURLButton() {
        waitForElementToAppear(confirmURLButton);
        confirmURLButton.click();
    }

    public boolean getPageProblem() {
        return isElementDisplayed(pageProblem);
    }

    public void open() {
        openUrl(URL);
    }

    public void setBrowserCookie() {
        Cookie newDesign = new Cookie.Builder("exp", "form-v2")
                .domain("staging.webcompat.com")
                .expiresOn(new Date(2020, 10, 28))
                .isHttpOnly(false)
                .isSecure(false)
                .path("/")
                .build();
        getDriver().manage().addCookie(newDesign);
    }

}
