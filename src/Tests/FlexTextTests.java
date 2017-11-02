package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class FlexTextTests {
    public static void checkJumpToPage() throws InterruptedException, IOException{
        List<WebElement> allCourses = CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorCourse));
        ArrayList<String> allBooks = CommonResources.getAllBooks();
        int courseCount = 0;
        for (WebElement course: allCourses){
            String courseTitle = course.getText();
            if(allBooks.contains(courseTitle)){
                UINavigation.scrollTo(course);

                UINavigation.accessCourse(courseTitle);

                UINavigation.clickSkip();

                if(!navContainsFlexTextTab()){
                    System.out.println(String.format("FlexText is not available for: %s", courseTitle));
                    UINavigation.navToDash();
                    continue;
                }

                UINavigation.clickFlexTextTab();

                WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 60);

                List<WebElement> allFT = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(CommonResources.cssSelectorAllFlexText)));

                for(WebElement currFT: allFT){
                    System.out.println(String.format("Accessing flextext: %s", currFT.getText()));
                    UINavigation.scrollTo(currFT);
                    Thread.sleep(1000);
                    currFT.click();

                    UINavigation.clickSkip();
                    Thread.sleep(1000);

                    UINavigation.clickJumpToPage();

                    WebElement page = CommonResources.browserDriver.findElement(
                            By.cssSelector(CommonResources.cssSelectorPageInput));

                    int max = Integer.parseInt(page.getAttribute("max"));
                    int randNum = getRandNum(max);

                    enterRandNum(randNum, page);

                    switchToFT();

                    boolean correctPage  = checkCorrectPage(randNum);
                    if(correctPage){
                        System.out.println("Redirected to the correct page.");
                        System.out.println(Utility.getCurrDate());
                    }

                    else{
                        String error = String.format(
                                "%s: The FlexText %s did not redirect to page %s correctly.",
                                Utility.getCurrDate(), currFT, randNum);
                        System.out.println(error);
                        Utility.writeToErrorLog(CommonResources.errorLogFlexText, error);
                    }

                    Thread.sleep(1000);

                    switchToMain();
                    Thread.sleep(1000);

                    UINavigation.clickFlexTextTab();
                }
                UINavigation.navToDash();
            }
        }
    }

    private static boolean navContainsFlexTextTab(){
        List<WebElement> nav = CommonResources.browserDriver.findElements(
                By.cssSelector(CommonResources.cssSelectorCourseNav));
        for(WebElement navTitle : nav){
            if(Objects.equals(navTitle.getText(), "FlexText")){
                return true;
            }
        }
        return false;
    }

    private static int randomInt(int from, int to){
        return ThreadLocalRandom.current().nextInt(from, to);
    }

    private static boolean checkCorrectPage(int pn){
        try {
            String pageNum = "article#p" + pn;
            WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 30);
            List<WebElement> currPageNum = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(pageNum)));
        }
        catch (TimeoutException t){
            return false;
        }
        return true;
    }

    private static void switchToFT(){
        CommonResources.browserDriver.switchTo().frame(
                CommonResources.browserDriver.findElement(By.id("flextext-iframe")));
    }

    private static void switchToMain(){
        CommonResources.browserDriver.switchTo().defaultContent();
    }

    private static int getRandNum(int max){
        return randomInt(10, max+1);
    }

    private static void enterRandNum(int num, WebElement pageInput) throws InterruptedException {
        System.out.println("Deleting page input entry...");
        pageInput.clear();
        Thread.sleep(1000);

        System.out.println(String.format("Entering %s into page input...", num));
        pageInput.sendKeys(Integer.toString(num));
        Thread.sleep(1000);

        System.out.println(String.format("Redirecting to page number: %s...", num));
        pageInput.sendKeys(Keys.RETURN);
        Thread.sleep(1000);
    }
}

