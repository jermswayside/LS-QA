package Tests;
import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.Objects;

public class CreateCourse {
    public static void createCourse() throws InterruptedException {
        long startTime = System.nanoTime();
        clickNewCourseButton();
        clickCreateCourseButton();

        WebElement createCoursePopup = getCreateCoursePopup();
        Thread.sleep(2000);
        checkMessageBox(createCoursePopup, null, null, null);

        WebElement courseTitle = getCourseTitle();
        Thread.sleep(2000);
        checkMessageBox(createCoursePopup, courseTitle,null,null);

        WebElement startDate = getStartDate();
        Thread.sleep(2000);
        checkMessageBox(createCoursePopup, null, startDate, null);

        WebElement termsBox = getTermsBox();
        Thread.sleep(2000);
        checkMessageBox(createCoursePopup, null, null, termsBox);


        Thread.sleep(2000);
        checkMessageBox(createCoursePopup, courseTitle, startDate, null);


        Thread.sleep(2000);
        checkMessageBox(createCoursePopup,courseTitle,null,termsBox);

        Thread.sleep(2000);
        checkMessageBox(createCoursePopup,null,startDate,termsBox);

        Thread.sleep(2000);
        checkMessageBox(createCoursePopup,courseTitle,startDate,termsBox);
        UINavigation.clickSkip();

        try {
            WebElement cog = getCog();
            System.out.println("Course creation finished.");

        }

        catch (NoSuchElementException e) {
            System.out.println("Course creation finished, but it did not redirect to expected page.  Contact administrator.");
        }
        long endTime = System.nanoTime();
        Utility.nanoToReadableTime(startTime, endTime);
    }

    private static WebElement getCog() throws InterruptedException {
        return Utility.waitForElementToExistByXpath(CommonResources.cssSelectorCogWheel);
    }

    private static WebElement getTermsBox() {
        try{
            return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorTermsBox));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static WebElement getStartDate() {
        try{
            return CommonResources.browserDriver.findElement(By.xpath(CommonResources.cssXpathStartDate));
        }
        catch (NoSuchElementException n){
            return null;
        }
    }
    private static WebElement getCourseTitle() {
        try {
            return CommonResources.browserDriver.findElement(
                    By.cssSelector(CommonResources.cssSelectorCourseTitle));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }
    private static WebElement getCreateCoursePopup() {
        try {
            return CommonResources.browserDriver.findElement(
                    By.cssSelector(CommonResources.cssSelectorCreateCoursePopUp));
        }
        catch(NoSuchElementException n) {
            return null;
        }
    }
    private static WebElement getCreateCourseButton() {
        try {
            return CommonResources.browserDriver.findElement(By.cssSelector(
                    CommonResources.cssSelectorCreateCourse));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static void clickCreateCourseButton() throws InterruptedException{
        WebElement createCourseButton = getCreateCourseButton();
        createCourseButton.click();
        Thread.sleep(2000);
    }

    private static WebElement getNewCourseButton() {
        try {
            return CommonResources.browserDriver.findElement(
                    By.cssSelector(CommonResources.cssSelectorNewCourse));
        }
        catch (NoSuchElementException n){
            return null;
        }
    }

    private static void clickNewCourseButton() throws InterruptedException{
        WebElement newCourseButton = getNewCourseButton();
        newCourseButton.click();
        Thread.sleep(2000);
    }

    private static void checkMessageBox(WebElement create, WebElement title, WebElement date, WebElement terms) throws InterruptedException{
        WebElement box = null;
        WebElement t = getCourseTitle();
        WebElement d = getStartDate();
        WebElement tm = getTermsBox();

        if (title == null && date == null && terms == null) {
            create.click();
        }

        else if (title != null && date == null && terms == null) {
            title.sendKeys(CommonResources.courseTitle);
            create.click();
        }

        else if (title == null && date != null && terms == null) {
            t.clear();
            date.click();
            UINavigation.clickCurrDay();
            Thread.sleep(1000);
            create.click();
        }

        else if (title == null && date == null && terms != null) {
            t.clear();
            d.clear();
            t.click();
            Thread.sleep(1000);
            terms.click();
            create.click();

        }

        else if (title != null && date != null && terms == null) {
            tm.click();
            title.sendKeys(CommonResources.courseTitle);
            date.click();
            UINavigation.clickCurrDay();
            Thread.sleep(1000);
            create.click();

        }

        else if (title != null && date == null && terms != null){
            d.clear();
            t.click();
            Thread.sleep(1000);
            terms.click();
            create.click();
        }

        else if (title == null && date != null && terms != null){
            t.clear();
            date.click();
            UINavigation.clickCurrDay();
            Thread.sleep(1000);
            create.click();
        }


        else{
            title.sendKeys(CommonResources.courseTitle);
            create.click();
        }
        Thread.sleep(2000);


        try {
            box = (new WebDriverWait(CommonResources.browserDriver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector(CommonResources.cssSelectorMessageBox)));
            String boxText = box.getText();
            System.out.println(String.format("Message Box appeared: \"%s\"", boxText));

            if (Objects.equals(boxText.toString(), CommonResources.courseCreationDupTitleMsg)) {
                boolean equal = true;
                while (equal) {
                    String currTitle = title.getAttribute("value");
                    char lastChar = currTitle.charAt(currTitle.length() - 1);

                    if (Character.isLetter(lastChar)) {
                        currTitle = currTitle + "0";
                        title.clear();
                        title.sendKeys(currTitle);
                        create.click();
                    }

                    else {
                        int num = Integer.parseInt(Character.toString(lastChar));
                        num++;
                        currTitle = currTitle.substring(0, currTitle.length() - 1) + num;
                        title.clear();
                        title.sendKeys(currTitle);
                        create.click();
                        Thread.sleep(500);
                        WebElement isPresent = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorMessageBox));
                        if (!isPresent.isDisplayed()) {
                            equal = false;
                        }
                    }
                }
            }
        }

        catch (org.openqa.selenium.TimeoutException te){
            UINavigation.clickSkip();
        }
    }

}
