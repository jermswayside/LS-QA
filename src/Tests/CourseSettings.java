package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CourseSettings {
    public static void assignmentPenalty() throws InterruptedException{
        long startTime = System.nanoTime();
        UINavigation.accessCourse("asd");
        UINavigation.clickSkip();

        WebElement settingsLink = getSettingsLink();
        Utility.waitForClickable(settingsLink);
        settingsLink.click();

        WebElement assignmentsPenaltyInput = getAssignmentsSettingsPenaltyInput();
        if(assignmentsPenaltyInput.isEnabled()) {
            System.out.println("Penalty input is already enabled.");
            return;
        }

        WebElement assignmentsSettingsPenaltyToggle = getAssignmentsSettingsPenaltyToggle();
        assignmentsSettingsPenaltyToggle.click();

        WebElement saveButton = getSaveButton();
        saveButton.click();

        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForClickable(messageBox);

        String messageBoxText = "Changes were saved";
        if(!messageBox.getText().equals(messageBoxText)) {
            System.out.println(String.format("Message in message box were not correct: %s", messageBox.getText()));
            return;
        }

        if(!assignmentsPenaltyInput.isEnabled()) {
            System.out.println("Penalty input not enabled after toggling.");
            return;
        }
        messageBox.click();

        assignmentsPenaltyInput.sendKeys("-1");
        Thread.sleep(2000);
        saveButton = getSaveButton();
        saveButton.click();

        Utility.waitForClickable(messageBox);
        messageBoxText = "Value in field [ quizAssignmentPenalty ] is not at least 0 fieldname=quizAssignmentPenalty";
        System.out.println(messageBoxText);
        if(!messageBox.getText().equals(messageBoxText)) {
            System.out.println("Greater than 0 error changed or is wrong");
            return;
        }
        messageBox.click();

        assignmentsPenaltyInput.clear();

        assignmentsPenaltyInput.sendKeys("101");
        Thread.sleep(1000);
        saveButton = getSaveButton();
        saveButton.click();

        messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        messageBox.click();

        assignmentsPenaltyInput.clear();
        assignmentsPenaltyInput.sendKeys("100");
        Thread.sleep(3000);

        saveButton = getSaveButton();
        saveButton.click();

        messageBoxText = "Changes were saved";
        messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        if(!messageBox.getText().equals(messageBoxText)) {
            System.out.println(String.format("Message in message box were not correct: %s", messageBox.getText()));
            return;
        }
        messageBox.click();
        Thread.sleep(2000);
        long endTime = System.nanoTime();
        Utility.nanoToReadableTime(startTime, endTime);
        assignmentsCleanUp();
    }

    public static void quizHidePassFailStatus() throws InterruptedException {
        long startTime = System.nanoTime();
        UINavigation.accessCourse("asd");
        UINavigation.clickSkip();

        WebElement settingsLink = getSettingsLink();
        Utility.waitForClickable(settingsLink);
        settingsLink.click();

        WebElement quizSettings = getQuizSettings();
        Utility.waitForClickable(quizSettings);
        quizSettings.click();

        WebElement hidePassFailToggle = getHidePassFailStatusToggle();
        Thread.sleep(1000);
        hidePassFailToggle.click();

        WebElement saveButton = getQuizSaveButton();
        saveButton.click();

        String messageBoxText = "Changes were saved";
        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        if(!messageBox.getText().equals(messageBoxText)) {
            System.out.println(String.format("Message in message box were not correct: %s", messageBox.getText()));
            return;
        }
        messageBox.click();

        long endTime = System.nanoTime();
        Utility.nanoToReadableTime(startTime, endTime);
        quizHidePassFailCleanup();
    }

    public static void quizThreshold() throws InterruptedException {
        long startTime = System.nanoTime();

        UINavigation.accessCourse("asd");
        UINavigation.clickSkip();

        WebElement settingsLink = getSettingsLink();
        Utility.waitForClickable(settingsLink);
        settingsLink.click();

        WebElement quizSettings = getQuizSettings();
        Utility.waitForClickable(quizSettings);
        quizSettings.click();

        WebElement quizThreshold = getQuizThreshold();
        quizThreshold.sendKeys("-1");

        WebElement quizSaveButton = getQuizSaveButton();
        quizSaveButton.click();

        WebElement messageBox = Utility.getMessageBox();
        if(messageBox.isDisplayed()) {
            System.out.println("Quiz Threshold saved with -1 as input");
            return;
        }

        quizThreshold.clear();
        Thread.sleep(1000);
        quizThreshold.sendKeys("101");
        Thread.sleep(1000);

        quizSaveButton.click();

        messageBox = Utility.getMessageBox();
        if(messageBox.isDisplayed()) {
            System.out.println("Quiz Threshold saved with 101 as input");
            return;
        }

        quizThreshold.clear();
        Thread.sleep(1000);
        quizThreshold.sendKeys("50");
        Thread.sleep(1000);
        quizSaveButton.click();

        messageBox = Utility.getMessageBox();
        String messageBoxText = "Changes were saved";
        Utility.waitForVisible(messageBox);
        if(!messageBox.getText().equals(messageBoxText)) {
            System.out.println(String.format("Message in message box were not correct: %s", messageBox.getText()));
            return;
        }

        long endTime = System.nanoTime();
        Utility.nanoToReadableTime(startTime, endTime);

        quizThresholdCleanup();
    }

    public static void quizMaxAttempts() throws InterruptedException {
        long startTime = System.nanoTime();

        UINavigation.accessCourse("asd");
        UINavigation.clickSkip();

        WebElement settingsLink = getSettingsLink();
        Utility.waitForClickable(settingsLink);
        settingsLink.click();

        WebElement quizSettings = getQuizSettings();
        Utility.waitForClickable(quizSettings);
        quizSettings.click();
    }
    public static void showHideContent() {}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static List<WebElement> getNavBar() throws InterruptedException{
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorCourseNav);
    }

    private static WebElement getSettingsLink() throws InterruptedException {
        return getNavBar().get(getNavBar().size()-1);
    }

    private static List<WebElement> getSettings() throws InterruptedException {
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorSettings);
    }

    private static WebElement getAssignmentsSettings() throws InterruptedException{
        return getSettings().get(0);
    }

    private static List<WebElement> getAssignmentsSettingsFormGroups() throws InterruptedException {
        return getAssignmentsSettings().findElements(By.cssSelector(CommonResources.cssSelectorAssignmentsFormGroups));
    }

    private static WebElement getAssignmentsSettingsPenaltyToggle() throws InterruptedException {
        return getAssignmentsSettingsFormGroups().get(0).findElement(
                By.cssSelector(CommonResources.cssSelectorAssignmentsPenaltyToggle));
    }

    private static WebElement getAssignmentsSettingsPenaltyInput() throws InterruptedException {
        return getAssignmentsSettingsFormGroups().get(1).findElement(
                By.cssSelector(CommonResources.cssSelectorAssignmentsPenaltyInput));
    }

    private static void assignmentsCleanUp() throws InterruptedException {
        WebElement assignmentsSettingsPenaltyToggle = getAssignmentsSettingsPenaltyToggle();
        assignmentsSettingsPenaltyToggle.click();

        Thread.sleep(2000);

        WebElement saveButton = getSaveButton();
        saveButton.click();

        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
    }

    private static WebElement getSaveButton() throws InterruptedException {
        List<WebElement> formGroups = getAssignmentsSettingsFormGroups();
        return formGroups.get(formGroups.size()-1).findElement(
                By.cssSelector(CommonResources.cssSelectorSettingsSaveButton));
    }

    private static List<WebElement> getSideNav() throws InterruptedException {
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorSideNav);
    }

    private static WebElement getQuizSettings() throws InterruptedException {
        return getSideNav().get(1);
    }

    private static WebElement getHidePassFailStatusToggle() throws InterruptedException {
        WebElement toggle = getQuizSettingsInputGroups().get(0).findElement(
                By.cssSelector(CommonResources.cssSelectorQuizSettingsHidePassFailToggle));
        return toggle;
    }

    private static List<WebElement> getQuizSettingsInputGroups() throws InterruptedException {
        List<WebElement> quizSettingsInputGroups = CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorQuizSettingsInputGroups));
        return quizSettingsInputGroups;
    }

    private static void quizHidePassFailCleanup() throws InterruptedException {
        WebElement hidePassFailToggle = getHidePassFailStatusToggle();
        hidePassFailToggle.click();

        WebElement saveButton = getQuizSaveButton();
        saveButton.click();
    }

    private static WebElement getQuizSaveButton() throws InterruptedException {
        List<WebElement> quizSettingsInputGroup = getQuizSettingsInputGroups();
        return quizSettingsInputGroup.get(quizSettingsInputGroup.size()-1).findElement(
                By.cssSelector(CommonResources.cssSelectorSettingsSaveButton));
    }

    private static WebElement getQuizThreshold() throws InterruptedException {
        List<WebElement> quizSettingsInputGroup = getQuizSettingsInputGroups();
        return quizSettingsInputGroup.get(1).findElement(
                By.cssSelector(CommonResources.cssSelectorAssignmentsPenaltyInput));
    }

    private static void quizThresholdCleanup() throws InterruptedException {
        WebElement quizThreshold = getQuizThreshold();
        quizThreshold.sendKeys("-1");

        WebElement quizSaveButton = getQuizSaveButton();
        quizSaveButton.click();
    }
}
