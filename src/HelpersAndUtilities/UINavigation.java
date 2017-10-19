package HelpersAndUtilities;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


// This class is used for helper methods that don't relate to testing functions

public class UINavigation {
    public static void navToDash() throws InterruptedException {
        try {
            Thread.sleep(3000);
            WebElement dash = (new WebDriverWait(CommonResources.browserDriver, 30))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("nav > div.ws-layout-wrapper >  a.ws-main-menu-item:nth-child(1)")));
            dash.click();
            System.out.println("Redirecting back to dashboard...");
        }
        catch (NoSuchElementException n){
            System.out.println("Dashboard button not found.  Contact administrator.");
            System.out.println("Exiting program.");
            System.exit(0);
        }
    }

    public static void navToAssignment() throws InterruptedException{

        accessCourse();
        clickSkip();

        Thread.sleep(1000);

        accessAssignments();
        clickSkip();
    }

    public static void navToAssignment(WebDriver driver) throws InterruptedException{
        accessCourse(driver);
        clickSkip(driver);

        Thread.sleep(1000);

        accessAssignments(driver);
        clickSkip(driver);
    }

    public static void clickSkip() throws InterruptedException{
        System.out.println("Waiting for tutorial box to appear...");
        try {
            WebElement skip = (new WebDriverWait(CommonResources.browserDriver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector(CommonResources.cssSelectorPopupSkip)));
            skip.click();
            System.out.println("Clicked \"Skip\".");
        }
        catch (org.openqa.selenium.TimeoutException te){
            System.out.println("Tutorial box not found; Continuing script.");
        }
    }

    public static void clickSkip(WebDriver driver) throws InterruptedException{
        System.out.println("Waiting for tutorial box to appear...");
        try {
            WebElement skip = (new WebDriverWait(driver, 20))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector(CommonResources.cssSelectorPopupSkip)));
            skip.click();
            System.out.println("Clicked \"Skip\".");
        }
        catch (org.openqa.selenium.TimeoutException te){
            System.out.println("Tutorial box not found; Continuing script.");
        }
    }

    public static void accessCourse() throws InterruptedException{
        WebElement course = (new WebDriverWait(CommonResources.browserDriver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(CommonResources.cssSelectorCourse)));
        System.out.println(("Now accessing course \"asd\""));
        course.click();

        Thread.sleep(5000);
    }

    public static void accessCourse(WebDriver driver) throws InterruptedException{
        WebElement course;
        try {
            course = (new WebDriverWait(driver, 20))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector(CommonResources.cssSelectorCourseStudent)));
        }
        catch (TimeoutException e){
            System.out.println("There are no courses for qastudent.  Exiting.");
            return;
        }
        System.out.println(("Now accessing course \"asd\""));
        course.click();

        Thread.sleep(5000);
    }

    public static void accessAssignments() throws InterruptedException{
        WebElement assignment = (new WebDriverWait(CommonResources.browserDriver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(CommonResources.cssXpathAssignmentTab)));

        System.out.println(String.format("Now accessing assignments tab..."));
        assignment.click();

        Thread.sleep(5000);
    }

    public static void accessAssignments(WebDriver driver) throws InterruptedException{
        WebElement assignment = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(CommonResources.cssXpathAssignmentTab)));

        System.out.println(String.format("Now accessing assignments tab..."));
        assignment.click();

        Thread.sleep(5000);

    }

    public static void clickAddAssignments() throws InterruptedException {
        WebElement addAssignment = (new WebDriverWait(CommonResources.browserDriver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(CommonResources.cssSelectorAddAssignment)));
        addAssignment.click();
        System.out.println("Clicked \"ADD ASSIGNMENT\"");
    }

    public static void clickNextStep() throws InterruptedException {
        WebElement nextStep = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorNextStep));
        nextStep.click();

        System.out.println("Clicked \"NEXT STEP\".");

    }

    public static void clickActiveNextStep() throws InterruptedException {
        WebElement nextNextStep = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorNextNextStep));
        nextNextStep.click();

        System.out.println("Clicked \"NEXT STEP\".");
    }

    public static void clickChooseDate() throws InterruptedException {
        WebElement datePick = (new WebDriverWait(CommonResources.browserDriver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(CommonResources.cssXpathChooseDate)));
        datePick.click();
    }

    public static void clickAssign() throws InterruptedException {
        WebElement assign = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorAssign));
        assign.click();

        System.out.println("Clicked \"Assign\".");
    }

    public static void clickStudentCheckBox() throws InterruptedException {
        WebElement checkbox = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorStudentCheckBox));
        checkbox.click();

        System.out.println("Clicked checkbox for \"QA Student 1\"");
    }

    public static void clickCurrDay() throws InterruptedException{
        WebElement datePickerCurrDate = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorCurrDay));
        datePickerCurrDate.click();

        System.out.println("Clicked \"Choose date\" and inputting current day and time.");
    }

    public static void clickEdit() throws InterruptedException {
        WebElement edit = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorEdit));
        edit.click();

        System.out.println("Click \"Edit\".");
    }

    public static void clickSave() throws  InterruptedException {
        WebElement save = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorSave));
        save.click();

        System.out.println("Clicked \"SAVE\".");
    }

    public static void clickCompletedAssigned() {
        WebElement students = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorAssignees));
        students.click();

        System.out.println("Clicked \"Completed/Assigned\".");
    }

    public static void clickClose() {
        WebElement close = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorClose));
        close.click();

        System.out.println("Clicked \"CLOSE\".");
    }

    public static void clickEditNextStep() {
        WebElement next = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorEditNextStep));
        next.click();

        System.out.println("Clicked \"NEXT STEP\".");
    }

    public static void clickTrashcan() {
        WebElement trashCan = CommonResources.browserDriver.findElement(By.cssSelector(
                CommonResources.cssSelectorTrashCan));
        trashCan.click();

        System.out.println("Clicked trashcan icon.");
    }

    public static void clickCheckAll() {
        WebElement checkAll = CommonResources.browserDriver.findElement(By.cssSelector(
                CommonResources.cssSelectorCheckAllBox));
        checkAll.click();

        System.out.println("Clicked check all box.");
    }

    public static void clickRemoveSelected(){
        WebElement removeSelected = CommonResources.browserDriver.findElement(
                By.linkText(CommonResources.cssLinkedTextRemove));
        removeSelected.click();

        System.out.println("Clicked \"Remove Selected\".");
    }

    public static void clickRemoveConfirm() throws InterruptedException{
        WebElement removeConfirm = Utility.getRemoveConfirm();
        removeConfirm.click();

        System.out.println("Clicked \"CONFIRM\".");
    }

    public static void clickArchive() {
        WebElement archive = CommonResources.browserDriver.findElement(
                By.cssSelector(CommonResources.cssSelectorArchive));
        archive.click();

        System.out.println("Clicked Archive icon.");
    }

    public static void clickArchiveYes() {
        WebElement yes = CommonResources.browserDriver.findElement(
                By.cssSelector(CommonResources.cssSelectorArchiveYes));
        yes.click();

        System.out.println("Clicked \"YES\".");
    }

    public static void clickShowArchived(){
        WebElement showArchived = CommonResources.browserDriver.findElement(
                By.cssSelector(CommonResources.cssSelectorShowArchived));
        showArchived.click();

        System.out.println("Clicked \"Show archived\".");
    }
}