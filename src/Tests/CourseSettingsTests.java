package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import Objects.Test;

import java.util.List;
import java.util.concurrent.CompletionException;

public class CourseSettingsTests {
    private static String currCat = CommonResources.getAllCategories().get(7);
    public static void assignmentPenalty() throws InterruptedException{
        Test currTest = new Test(currCat, "Check Assignment Penalty", "", "");
        long startTime = System.nanoTime();
        UINavigation.accessCourse("asd");
        UINavigation.clickSkip();

        WebElement settingsLink = getSettingsLink();
        Utility.waitForClickable(settingsLink);
        settingsLink.click();

        WebElement assignmentsPenaltyInput = getAssignmentsSettingsPenaltyInput();
        if(assignmentsPenaltyInput.isEnabled()) {
            System.out.println("Penalty input is already enabled.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
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
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        if(!assignmentsPenaltyInput.isEnabled()) {
            System.out.println("Penalty input not enabled after toggling.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }
        messageBox.click();

        assignmentsPenaltyInput.sendKeys("-1");
        Thread.sleep(2000);
        saveButton = getSaveButton();
        saveButton.click();

        WebElement valueValidations = getValueValidations();
        Utility.waitForClickable(valueValidations);

        if(!valueValidations.isDisplayed()) {
            System.out.println("Value validations did not appear.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        assignmentsPenaltyInput.clear();

        assignmentsPenaltyInput.sendKeys("101");
        Thread.sleep(2000);
        saveButton = getSaveButton();
        saveButton.click();

        valueValidations = getValueValidations();
        Utility.waitForClickable(valueValidations);

        if(!valueValidations.isDisplayed()) {
            System.out.println("Value validations did not appear.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        assignmentsPenaltyInput.clear();
        assignmentsPenaltyInput.sendKeys("100");
        Thread.sleep(2000);

        saveButton = getSaveButton();
        saveButton.click();

        messageBoxText = "Changes were saved";
        messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        if(!messageBox.getText().equals(messageBoxText)) {
            System.out.println(String.format("Message in message box were not correct: %s", messageBox.getText()));
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }
        messageBox.click();
        Thread.sleep(2000);
        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if (CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
        assignmentsCleanUp();
    }

    public static void quizHidePassFailStatus() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Quiz Hide Pass Fail Status", "", "");
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
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }
        messageBox.click();

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")) {
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
        quizHidePassFailCleanup();
    }

    public static void quizThreshold() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Quiz Threshold", "", "");
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

        Thread.sleep(1000);

        WebElement quizSaveButton = getQuizSaveButton();
        quizSaveButton.click();

        Thread.sleep(1000);

        WebElement messageBox = Utility.getMessageBox();
        if(messageBox.isDisplayed()) {
            System.out.println("Quiz Threshold saved with -1 as input");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        quizThreshold.clear();
        Thread.sleep(1000);
        quizThreshold.sendKeys("101");
        Thread.sleep(1000);

        quizSaveButton.click();

        Thread.sleep(1000);

        messageBox = Utility.getMessageBox();
        if(messageBox.isDisplayed()) {
            System.out.println("Quiz Threshold saved with 101 as input");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
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
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if (CommonResources.qaTestMode.equals("n")) {
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }

        quizThresholdCleanup();
    }

    public static void quizMaxAttempts() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Quiz Max Attempts", "", "");
        long startTime = System.nanoTime();

        UINavigation.accessCourse("asd");
        UINavigation.clickSkip();

        WebElement settingsLink = getSettingsLink();
        Utility.waitForClickable(settingsLink);
        settingsLink.click();

        WebElement quizSettings = getQuizSettings();
        Utility.waitForClickable(quizSettings);
        quizSettings.click();

        WebElement quizAttempts = getQuizAttempts();
        quizAttempts.sendKeys("-1");

        Thread.sleep(1000);

        WebElement quizSaveButton = getQuizSaveButton();
        quizSaveButton.click();

        WebElement messageBox = Utility.getMessageBox();
        if(messageBox.isDisplayed()) {
            System.out.println("Quiz Threshold saved with -1 as input");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        quizAttempts.clear();
        quizAttempts.sendKeys("0");

        Thread.sleep(1000);

        quizSaveButton.click();

        messageBox = Utility.getMessageBox();
        if(messageBox.isDisplayed()) {
            System.out.println("Quiz Threshold saved with 0 as input");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        quizAttempts.clear();
        quizAttempts.sendKeys("100");

        Thread.sleep(1000);

        quizSaveButton.click();

        String messageBoxText = "Changes were saved";
        Utility.waitForVisible(messageBox);
        if(!messageBox.getText().equals(messageBoxText)) {
            System.out.println(String.format("Message in message box were not correct: %s", messageBox.getText()));
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }

        quizAttemptsCleanUp();
    }
    public static void showHideContent() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Show Hide Content", "", "");
        long startTime = System.nanoTime();

        UINavigation.accessCourse("asd");
        UINavigation.clickSkip();

        WebElement settingsLink = getSettingsLink();
        Utility.waitForClickable(settingsLink);
        settingsLink.click();

        WebElement quizSettings = getQuizSettings();
        Utility.waitForClickable(quizSettings);
        quizSettings.click();

        WebElement showHideContentSettings = getShowHideContentSettings();
        showHideContentSettings.click();

        WebElement firstChapter = getChapter(0);
        UINavigation.scrollTo(firstChapter);
        Thread.sleep(1000);
        WebElement firstChapterCheckbox = getCheckBox(firstChapter);

        firstChapterCheckbox.click();
        Thread.sleep(1000);

        try {
            getNoCheckBox(firstChapter);
        }
        catch (NoSuchElementException n) {
            System.out.println("Checkbox-to-NoCheckBox not working.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        firstChapter.click();

        Thread.sleep(1000);

        WebElement firstSubChapter;

        try {
            firstSubChapter = getVisibleContent().get(0);
        }
        catch (NoSuchElementException n) {
            System.out.println("Expanding not working.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        WebElement subChapterCheckbox = getNoCheckBox(firstSubChapter);
        subChapterCheckbox.click();

        try{
            getCheckBox(firstSubChapter);
        }
        catch (NoSuchElementException n){
            System.out.println("Checkbox-to-NoCbeckBox in subchapter not working");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        try{
            getDashBox(firstChapter);
        }
        catch(NoSuchElementException n) {
            System.out.println("NoCheckBox-to-DashCheckBox in first chapter not working");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }

        showHideContentSettingsCleanUp();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void showHideContentSettingsCleanUp() throws InterruptedException {
        WebElement firstChapter = getChapter(0);

        UINavigation.scrollTo(firstChapter);

        Thread.sleep(1000);

        WebElement firstChapterBox = getDashBox(firstChapter);
        firstChapterBox.click();

        Thread.sleep(5000);


        firstChapterBox = getNoCheckBox(firstChapter);
        firstChapterBox.click();

        Thread.sleep(5000);
    }
    private static List<WebElement> getVisibleContent() {
        return CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorVisibleContent));
    }
    private static WebElement getDashBox(WebElement chapter) {
        return chapter.findElement(By.cssSelector(CommonResources.cssSelectorChapterCheckboxDash));
    }
    private static WebElement getNoCheckBox(WebElement chapter) {
        return chapter.findElement(By.cssSelector(CommonResources.cssSelectorChapterCheckboxNoCheck));
    }
    private static WebElement getCheckBox(WebElement chapter) {
        return chapter.findElement(By.cssSelector(CommonResources.cssSelectorChapterCheckboxCheck));
    }
    private static WebElement getChapter(int i) throws InterruptedException {
        return getShowHideContent().get(i);
    }

    private static List<WebElement> getShowHideContent() throws InterruptedException {
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorShowHideContent);
    }
    private static WebElement getShowHideContentSettings() throws InterruptedException {
        return getSideNav().get(2);
    }

    private static void quizAttemptsCleanUp() throws InterruptedException {
        WebElement quizAttempts = getQuizAttempts();
        quizAttempts.clear();
        quizAttempts.sendKeys("3");
        Thread.sleep(1000);

        WebElement quizSaveButton = getQuizSaveButton();
        quizSaveButton.click();

        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
    }

    private static WebElement getQuizAttempts() throws InterruptedException {
        return getQuizSettingsInputGroups().get(1).findElement(
                By.cssSelector(CommonResources.cssSelectorAssignmentsPenaltyInput));
    }

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
        return quizSettingsInputGroup.get(2).findElement(
                By.cssSelector(CommonResources.cssSelectorAssignmentsPenaltyInput));
    }

    private static void quizThresholdCleanup() throws InterruptedException {
        WebElement quizThreshold = getQuizThreshold();
        quizThreshold.clear();
        quizThreshold.sendKeys("80");

        Thread.sleep(1000);

        WebElement quizSaveButton = getQuizSaveButton();
        quizSaveButton.click();

        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
    }

    private static WebElement getValueValidations() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorValueValidator);
    }
}
