package pages;

import helpers.PageNotCurrentException;
import helpers.PageNotValidException;
import helpers.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class WebUiPage {

  protected WebDriver driver;
  private static final int    TIMEOUT = 10;
  private static final Logger LOG     = Logger.getLogger(Class.class.getName());
  private static final int DEFAULT_LOAD_TIME_SEC                      = 10;
  private static final int WAIT_TIME_BEFORE_IS_CURRENT_CHECKS_MILLIS  = 500;
  private static final int WAIT_TIME_BETWEEN_IS_CURRENT_CHECKS_MILLIS = 500;
  private static final String DOCUMENT_READY_STATE_GET      = "return document.readyState";
  private static final String DOCUMENT_READY_STATE_COMPLETE = "complete";

  public WebUiPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public WebDriver getDriver() {
      return this.driver;
  }

  public void waitForElementToAppear(WebElement element){
      waitFor(ExpectedConditions.visibilityOf(element), TIMEOUT);
  }

  public void waitForElementToDisappear(WebElement element){
      waitFor(ExpectedConditions.invisibilityOf(element), TIMEOUT);
  }

  public void waitForElementToAppear(By element){
      waitFor(ExpectedConditions.visibilityOfElementLocated(element), TIMEOUT);
  }

  public void waitForElementToDisappear(By element){
      waitFor(ExpectedConditions.invisibilityOfElementLocated(element), TIMEOUT);
  }

  /**
   * Wait until an expected condition is met
   *
   * @param expectedCondition the condition we expect
   * @param secondsToWait the maximum time in seconds to wait
   * @return true if the expected condition was met within <code>secondsToWait</code>, false
   *         otherwise
   */
  public boolean waitFor(final ExpectedCondition<?> expectedCondition, final int secondsToWait) {
      final WebDriverWait wait = new WebDriverWait(getDriver(), secondsToWait);
      try {
          wait.until(expectedCondition);
          return true;
      }
      catch (final TimeoutException e) {
          return false;
      }
  }

  public void verify() {
    verify(true);
  }

  protected void verify(final boolean executeIsValid) {
    final int waitTime = getMaximumLoadTime();
    final long waitTimeMillis = TimeUnit.SECONDS.toMillis(waitTime);
    final long startTime = System.currentTimeMillis();
    final String pageName = getName();

    // wait a split of a second to make sure the browser has time to start loading the new state
    Utilities.sleep(WAIT_TIME_BEFORE_IS_CURRENT_CHECKS_MILLIS, true);

    boolean browserFinishedLoading = false;
    long timeLeft = waitTimeMillis;
    do {
      if (!browserFinishedLoading) {
        LOG.info(String.format("Waiting for browser to finish loading page %s ...", pageName));
        browserFinishedLoading = isBrowserDoneLoading();
        if (browserFinishedLoading) {
          LOG.info(String.format("Browser finished loading page %s", pageName));
        }
      }

      if (browserFinishedLoading) {
        LOG.info(String.format("Checking whether page %s is current ...", pageName));
        if (isCurrent()) {
          if (!executeIsValid) {
            LOG.info(String.format("Page %s is current now. Skipping check whether it's valid", pageName));
          }
          else {
            LOG.info(String.format("Page %s is current now. Checking whether it's valid ...", pageName));
            if (!isValid()) {
              throw new PageNotValidException(pageName + " page loaded, but is not valid");
            }
            LOG.info(String.format("Page %s is valid", pageName));
          }
          return;
        }
      }

      timeLeft = waitTimeMillis - (System.currentTimeMillis() - startTime);
      // only sleep if we're still within the allowed time limit
      if (timeLeft > 0) {
        int sleepTime = WAIT_TIME_BETWEEN_IS_CURRENT_CHECKS_MILLIS;
        // Only sleep full time if the time left for the page verification to complete is
        // bigger than the sleep
        // timeout. This makes sure the page would not have loaded 1/2 the wait time before
        // the cut off and we
        // "slept through it", then exploded although the page loaded in time
        if (timeLeft < WAIT_TIME_BETWEEN_IS_CURRENT_CHECKS_MILLIS) {
          // adjust sleepTime to half of the time left to make sure we still get another
          // check in
          sleepTime = (int) Math.floorDiv(timeLeft, 2);
        }
        Utilities.sleep(sleepTime, true);
      }
    } while (timeLeft > 0);

    final long totalWaitTime = System.currentTimeMillis() - startTime;
    throw new PageNotCurrentException(pageName + " page did not load within " + waitTime + " seconds (the exact wait time was: " + totalWaitTime + "ms)");
  }

    /**
     * Returns whether the page currently displayed in the browser is this page
     * <p>
     * NOTE: This method should NOT CONTAIN ANY WAITS. It should simply check for the minimum amount
     * of elements that uniquely identify the page / state in the browser to be present
     * <p>
     * This method should also not perform any type of document ready state checks, or spinners not
     * being present etc. This is done from within {@link #isBrowserDoneLoading()}
     * <p>
     * It will be called from within {@link #verify()} inside of a time-based loop after the page
     * document finished loading
     *
     * @return true when the page is current, false otherwise
     */
    protected abstract boolean isCurrent();

    /**
     * Returns whether this page is currently displayed in the browser in a valid / correct way
     * <p>
     * NOTE: This method should NOT CONTAIN ANY WAITS. It should simply check whether all elements
     * currently displayed on the page are valid / correct
     * <p>
     * This method will be called after {@link #isCurrent()} returned true, so you can assume the
     * browser is on the correct page, and the page finished loading
     *
     * @return true when the page is valid / correctly displayed, false otherwise
     */
    protected abstract boolean isValid();

    protected void openUrl(String url){
        driver.get(url);
    }

  /**
   * Returns whether the html document / page finished loading and no active jQuery or animation
   *
   * @return true when ready / done loading, false otherwise
   */
  private boolean isBrowserDoneLoading() {
    return Objects.equals(Utilities.executeScript(driver, DOCUMENT_READY_STATE_GET), DOCUMENT_READY_STATE_COMPLETE);
  }

    private int getMaximumLoadTime() {
        return DEFAULT_LOAD_TIME_SEC;
    }

    private String getName() {
        return getClass().getSimpleName();
    }
}
