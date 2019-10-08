package pages;

import helpers.WebElementHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static helpers.WebElementHelper.areVisible;
import static helpers.WebElementHelper.setFieldValue;

public class ReportBugPage extends BasePage {

    public final static String URL = "https://webcompat-ui-redesign.herokuapp.com/";

    @FindBy(id = "url")
    private WebElement siteURL;

    @FindBy(className = "next-step step-1 issue-btn")
    private WebElement confirmURLButton;

    @FindBy(className = "next-step step-3 issue-btn")
    private WebElement confirmSomethingElseButton;

    @FindBy(className = "page-heading")
    private WebElement pageHeading;

    @FindBy(className = "input-description")
    private WebElement typeURLDescription;

    @FindBy(css = "section.js-ReportForm.grid.is-closed:nth-child(2) form.form.grid-row.js-loader.loader.issue-form div.step-container.step2.col.open:nth-child(4) div.row:nth-child(1) div.input-control div.form-radio.choice-control.form-element.js-Form-group.is-validated.js-no-error div.js-Form-information.form-label-message:nth-child(1) > label.form-label")
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
        confirmURLButton.click();
    }

    public boolean getPageProblem() {
        return areVisible(pageProblem);
    }

    public void open() {
        openUrl(URL);
    }

}
