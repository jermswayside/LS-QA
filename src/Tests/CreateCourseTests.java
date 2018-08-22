package Tests;
import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import Objects.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.Objects;

public class CreateCourseTests {
    private static Test currTest = new Test(CommonResources.getAllCategories().get(0), "Create Course", "", "");

    public static void createCourse() throws InterruptedException {
        long startTime = System.nanoTime();

        clickNewCourseButton();
        clickCreateCourseButton();

        if(!noCourseTitleTest()){
            System.out.println("Course creation failed.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
        }

        String creationUrl = CommonResources.browserDriver.getCurrentUrl();

        addCourseTitleTest();

        String courseUrl = CommonResources.browserDriver.getCurrentUrl();

        if(creationUrl.equals(courseUrl)){
            System.out.println("Course creation failed.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
            return;
        }

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")) {
            String time = Utility.readableTime(startTime, endTime);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    private static void addCourseTitleTest() throws InterruptedException {
        int titleNum = 0;
       do {
            WebElement titleInput = getTitleInput();
            System.out.println(titleInput.getText());
            if(!titleInput.getAttribute("value").isEmpty()){
                titleInput.clear();
            }
            titleInput.sendKeys(String.format("Hello World%s", titleNum));
            titleNum++;

            WebElement formCreateCourseButton = getFormCreateCourseButton();
            formCreateCourseButton.click();
            Thread.sleep(1000);
        } while(Utility.getMessageBox().isDisplayed());
    }

    private static boolean noCourseTitleTest() throws InterruptedException {
        WebElement createCoursePopup = getCreateCoursePopup();
        Utility.waitForClickable(createCoursePopup);

        WebElement formCreateCourseButton = getFormCreateCourseButton();
        formCreateCourseButton.click();

        Thread.sleep(5000);

        WebElement errorMessage = Utility.getMessageBox();
        if(!errorMessage.isDisplayed()) {
            System.out.println("Error message box did not appear.");
            return false;
        }
        return true;
    }

    private static WebElement getTitleInput() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorTitleInput);
    }

    private static WebElement getCreateCoursePopup() throws InterruptedException {
        try {
            return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorCreateCoursePopUp);
        }
        catch(NoSuchElementException n) {
            return null;
        }
    }
    private static WebElement getCreateCourseButton() {
        try {
            return CommonResources.browserDriver.findElements(By.cssSelector(
                    CommonResources.cssSelectorCreateCourse)).get(1);
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static WebElement getFormCreateCourseButton() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorFormCreateCourseButton);
    }

    private static WebElement getFormCreateCourseButtonLoading() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorFormCreateCourseButtonLoading);
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
}
