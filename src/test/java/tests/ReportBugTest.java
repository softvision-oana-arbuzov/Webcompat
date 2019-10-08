package tests;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.ReportBugPage;

import static helpers.Utilities.getPropertyFromAppProp;

public class ReportBugTest extends BaseTest {
    private ReportBugPage reportBugPage = null;

    private String siteURL;

    public ReportBugTest (){
        this.siteURL = getPropertyFromAppProp("siteURL");
    }

    @BeforeMethod
    void beforeMethod(){
        this.reportBugPage = new ReportBugPage(getDriver());
    }

    @Test(description = "[Happy Flow] Test type in siteURL\n"
            + "Steps:\n"
            + "1.Navigate to ReportBug page URL: https://webcompat-ui-redesign.herokuapp.com/\n"
            + "2.Fill in the siteURL: https://www.google.com/\n"
            + "3.Click Confirm button\n"
            + "4.Verify that next section becomes active - What is wrong with the page at siteURL? -\n")
    public void testSiteURLSection(){
        this.reportBugPage.open();
        this.reportBugPage.verify();
        Assert.assertTrue(this.reportBugPage.getPageHeading(), "Filing a web compatibility issue");

        Assert.assertTrue(this.reportBugPage.getTypeURLDescription(), "Thank you for filing a web compatibility issue.\n" +
                "Do we have the correct site?\n");

        this.reportBugPage.typeSiteURL(this.siteURL);

        this.reportBugPage.clickConfirmURLButton();

        Assert.assertTrue(this.reportBugPage.getPageProblem(), "What is wrong with the page at" + siteURL + " ?");
    }
}
