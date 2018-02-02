package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CourseSettings {
    public static void assignmentPenalty() throws InterruptedException{
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
        Thread.sleep(5000);
        Utility.waitForClickable(messageBox);
        messageBoxText = "Value in field [ quizAssignmentPenalty ] is not at least 0 fieldname=quizAssignmentPenalty";
        System.out.println(messageBoxText);
        if(!messageBox.getText().equals(messageBoxText)) {
            System.out.println("Greater than 0 error changed or is wrong");
        }
        Thread.sleep(2000);
        messageBox.click();

        assignmentsPenaltyInput.clear();
        messageBox = Utility.getMessageBox();
        Utility.waitForClickable(messageBox);
        assignmentsPenaltyInput.sendKeys("101");

        messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);

        assignmentsPenaltyInput.clear();
        messageBox = Utility.getMessageBox();
        Utility.waitForClickable(messageBox);
        assignmentsPenaltyInput.sendKeys("100");

        messageBoxText = "Changes were saved";
        messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        if(!messageBox.getText().equals(messageBoxText)) {
            System.out.println(String.format("Message in message box were not correct: %s", messageBox.getText()));
            return;
        }

        assignmentsCleanUp();
    }

    public static void quizHidePassFailStatus(){}
    public static void quizThreshold() {}
    public static void quizMaxAttempts() {
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

        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
    }
}
