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
    public static void createCourse() throws InterruptedException{

        System.out.println("Checking creating course functionality...");

        CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorNewCourse)).click();
        System.out.println("Clicked \"New Course\".");
        Thread.sleep(2000);

        CommonResources.browserDriver.findElement(By.cssSelector(
                CommonResources.cssSelectorCreateCourse)).click();
        System.out.println("Clicked \"Create Course\" for \"Azulejo\".");

        System.out.println("Testing empty input...");
        WebElement createCourse = CommonResources.browserDriver.findElement(
                By.cssSelector(CommonResources.cssSelectorCreateCoursePopUp));
        Thread.sleep(2000);
        checkMessageBox(createCourse, null, null, null);


        System.out.println(String.format("Testing inputting only course title \"%s\"...",CommonResources.courseTitle));
        WebElement courseTitle = CommonResources.browserDriver.findElement(
                By.cssSelector(CommonResources.cssSelectorCourseTitle));
        Thread.sleep(2000);
        checkMessageBox(createCourse, courseTitle,null,null);


        System.out.println("Testing inputting only current day");

        WebElement startDate = CommonResources.browserDriver.findElement(By.xpath(CommonResources.cssXpathStartDate));
        Thread.sleep(2000);
        checkMessageBox(createCourse, null, startDate, null);


        System.out.println("Testing only checking the T&C box...");
        WebElement termsBox = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorTermsBox));
        Thread.sleep(2000);
        checkMessageBox(createCourse, null, null, termsBox);


        System.out.println("Testing inputting course title and current day only...");
        Thread.sleep(2000);
        checkMessageBox(createCourse, courseTitle, startDate, null);


        System.out.println(String.format("Testing inputting course title \"%s\" and checking T&C box...",CommonResources.courseTitle));
        Thread.sleep(2000);
        checkMessageBox(createCourse,courseTitle,null,termsBox);

        System.out.println("Testing inputting current day and checking the T&C box only...");
        Thread.sleep(2000);
        checkMessageBox(createCourse,null,startDate,termsBox);

        System.out.println(String.format("Testing inputting course title \"%s\", current day, and checking T&C box...",
                CommonResources.courseTitle));
        Thread.sleep(2000);
        checkMessageBox(createCourse,courseTitle,startDate,termsBox);
        UINavigation.clickSkip();

        try {
            WebElement cog = (new WebDriverWait(CommonResources.browserDriver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(CommonResources.cssSelectorCogWheel)));

            System.out.println("Course creation finished.");

        }

        catch (NoSuchElementException e){
            System.out.println("Course creation finished, but it did not redirect to expected page.  Contact administrator.");
        }
    }


    private static void checkMessageBox(WebElement create, WebElement title, WebElement date, WebElement terms) throws InterruptedException{
        WebElement box = null;
        WebElement t = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorCourseTitle));
        WebElement d = CommonResources.browserDriver.findElement(By.xpath(CommonResources.cssXpathStartDate));
        WebElement tm = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorTermsBox));

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
