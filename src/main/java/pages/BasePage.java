package pages;


import helpers.WebElementHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class BasePage extends WebUiPage {

  protected WebDriver driver;

  @FindBy (id = "svg-search2")
  protected WebElement searchButton;

  @FindBy (id = "svg-users")
  protected WebElement contributeButton;

  @FindBy (id = "svg-hash")
  protected WebElement allIssuesButton;

  @FindBy(className = "js-login-link nav-link")
  protected WebElement loginButton;

  @FindBy(className = "hero-section grid color-first-bg")
  protected WebElement headerContainer;

  @FindBy(className = "hero-claim heading js-hero-title")
  protected WebElement headerContainerText;

  @FindBy(className = "hero-illustration-bug bug-animated")
  protected WebElement headerContainerAnimation;

  @FindBy(id = "js-ReportBug")
  protected WebElement reportBugButton;


// ---------------------Report bug form-----------------------




// -----------------------------------------------------------

  @FindBy(id = "footer")
  protected WebElement footerContainer;

  @FindBy(css = ".active > .footer-item-link")
  protected WebElement footerHomeButton;

  @FindBy(css = ".footer-item:nth-child(2) > .footer-item-link")
  protected WebElement footerListOfIssuesButton;

  @FindBy(css = ".footer-item:nth-child(3) > .footer-item-link")
  protected WebElement footerAboutButton;

  @FindBy(css = ".footer-item:nth-child(4) > .footer-item-link")
  protected WebElement footerContributeButton;

  @FindBy(css = ".footer-item:nth-child(5) > .footer-item-link")
  protected WebElement footerContactButton;

  @FindBy(css = ".footer-item:nth-child(6) > .footer-item-link")
  protected WebElement footerPrivacyPolicyButton;

  @FindBy(css = ".footer-item:nth-child(7) > .footer-item-link")
  protected WebElement footerCodeOfConductButton;

  @FindBy(id = "svg-twitter")
  protected WebElement footerTwitterButton;

  @FindBy(id = "svg-github2")
  protected WebElement footerGithubButton;

  public BasePage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  @Override
  protected boolean isCurrent() {
      return WebElementHelper.areVisible(headerContainer, footerContainer);
  }

  @Override
  protected boolean isValid() {
      return WebElementHelper.areVisible(searchButton, headerContainer);
  }
}
