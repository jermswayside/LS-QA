package HelpersAndUtilities;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;


// This class is used for helper methods that don't relate to testing functions

public class UINavigation {
    public static void navToDash() throws InterruptedException {
        Thread.sleep(3000);
        if(CommonResources.siteChoiceEntry == 1){
            CommonResources.browserDriver.get(CommonResources.urlStageLS);
        }
        if(CommonResources.siteChoiceEntry == 2){
            CommonResources.browserDriver.get(CommonResources.urlProdLS);
        }
        if(CommonResources.siteChoiceEntry == 3) {
            CommonResources.browserDriver.get(CommonResources.urlDevLS);
        }
        System.out.println("Redirecting back to dashboard...");
    }

    public static void navToAssignment() throws InterruptedException{
        Thread.sleep(3000);
        accessCourse(CommonResources.courseForAssignmentTest);

        Thread.sleep(5000);

        accessAssignments();
    }

    public static void navToAssignment(WebDriver driver) throws InterruptedException{
        Thread.sleep(3000);
        accessCourse(CommonResources.courseForAssignmentTest, driver);

        Thread.sleep(5000);
        clickSkip(driver);

        Thread.sleep(1000);

        accessAssignments(driver);
        clickSkip(driver);
    }

    public static void clickSkip() throws InterruptedException{
        System.out.println("Waiting for tutorial box to appear...");
        try {
            WebElement skip = (new WebDriverWait(CommonResources.browserDriver, 20))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector(CommonResources.cssSelectorStartingBanner)));
            skip.click();

        }
        catch (org.openqa.selenium.TimeoutException te){
            System.out.println("Tutorial box not found; Continuing script.");
        }
    }

    public static void clickSkip(WebDriver driver) throws InterruptedException{
        System.out.println("Waiting for tutorial box to appear...");
        try {
            WebElement skip = (new WebDriverWait(driver, 20))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector(CommonResources.cssSelectorStartingBanner)));
            skip.click();

        }
        catch (org.openqa.selenium.TimeoutException te){
            System.out.println("Tutorial box not found; Continuing script.");
        }
    }

    public static void accessCourse(String course) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 20);

        ExpectedCondition<List<WebElement>> condition = ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(CommonResources.cssSelectorCourse));

        List<WebElement> courses = wait.until(condition);
        Thread.sleep(1000);
        courses.forEach((c) ->{
            if (Objects.equals(c.getText(), course)){
                scrollTo(c);
                Utility.waitForClickable(c);

                c.click();
                System.out.println(String.format("Now accessing course: %s", course));

                return;
            }
        });
    }

    public static void clickX() throws InterruptedException{
        Thread.sleep(500);
        WebElement X = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorAssignX);
        Thread.sleep(1000);
        X.click();
    }
    public static void accessCourse(String course, WebDriver driver) throws InterruptedException{
        try {
            WebDriverWait wait = new WebDriverWait(driver, 20);

            ExpectedCondition<List<WebElement>> condition = ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(CommonResources.cssSelectorCourseStudent));
            clickSkip();
            List<WebElement> courses = wait.until(condition);
            courses.forEach((c) -> {
                if (Objects.equals(c.getText(), course)){
                    scrollTo(c, driver);
                    c.click();
                    System.out.println(String.format(
                            "Now accessing course: %s", course));

                    return;
                }
            });
        }
        catch (TimeoutException e) {
            System.out.println("There are no courses for qastudent.  Exiting.");
            return;
        }
    }

    public static void accessAssignments() throws InterruptedException{
        WebElement assignment = (new WebDriverWait(CommonResources.browserDriver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(CommonResources.cssXpathAssignmentTab)));

        System.out.println("Now accessing assignments tab...");
        scrollTo(assignment);
        assignment.click();

        Thread.sleep(5000);
    }

    public static void accessAssignments(WebDriver driver) throws InterruptedException{
        WebElement assignment = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(CommonResources.cssXpathAssignmentTab)));

        System.out.println("Now accessing assignments tab...");
        assignment.click();

        Thread.sleep(5000);

    }

    public static void clickAddAssignments() throws InterruptedException {
        WebElement addAssignment = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorAddAssignment);
        Thread.sleep(2000);
        addAssignment.click();
        System.out.println("Clicked \"ADD ASSIGNMENT\"");
    }

    public static void clickNextStep() throws InterruptedException {
        Thread.sleep(2000);

        WebElement nextStep = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorFooterButtons));
        nextStep.click();

        System.out.println("Clicked \"NEXT STEP\".");

    }

    public static void clickActiveNextStep() throws InterruptedException {
        Thread.sleep(500);
        WebElement nextNextStep = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorNextNextStep);
        Utility.waitForClickable(nextNextStep);
        Thread.sleep(500);
        nextNextStep.click();

        System.out.println("Clicked \"NEXT STEP\".");
    }

    public static void clickChooseDate() throws InterruptedException {
        WebElement datePick = Utility.waitForElementToExistByXpath(CommonResources.cssXpathChooseDate);
        datePick.click();
    }

    public static void clickAssign() throws InterruptedException {
        WebElement assign = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorFooterButtons);
        Utility.waitForClickable(assign);
        assign.click();

        System.out.println("Clicked \"Assign\".");
    }

    public static void clickStudentCheckBox() throws InterruptedException {
        List<WebElement> students = Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorEditAllStudents);
        for(WebElement student:students){
            if(student.getText().toLowerCase().contains("fake@email.com")){
                WebElement checkBox = student.findElement(By.cssSelector("div > " + CommonResources.cssSelectorCurrStudentCheckBox));
                checkBox.click();
            }
        }
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
        List<WebElement> footerButtons = Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorFooterButtons);
        footerButtons.get(1).click();
    }

    public static void clickCompletedAssigned() throws InterruptedException {
        WebElement students = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorAssignees);
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

    public static void clickCheckAll() throws InterruptedException {
        WebElement checkAll = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorCheckAllBox);
        Thread.sleep(3000);
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

    public static void clickArchive() throws InterruptedException{
        Thread.sleep(500);
        WebElement archive = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorArchive);
        archive.click();

        System.out.println("Clicked Archive icon.");
    }

    public static void clickArchiveYes() throws InterruptedException {
        List<WebElement> popupConfirm = Utility.waitForElementsToExistByCssSelector(
                CommonResources.cssSelectorPopupConfirm);
        WebElement lastPopupConfirm = popupConfirm.get(popupConfirm.size()-1);
        System.out.println(lastPopupConfirm.getAttribute("style"));
        WebElement yes = lastPopupConfirm.findElement(By.cssSelector(CommonResources.cssSelectorArchiveYes));
        System.out.println(yes.getAttribute("class"));
        yes.click();

        System.out.println("Clicked \"YES\".");
    }

    public static void clickShowArchived(){
        WebElement showArchived = CommonResources.browserDriver.findElement(
                By.cssSelector(CommonResources.cssSelectorShowArchived));
        showArchived.click();

        System.out.println("Clicked \"Show archived\".");
    }

    public static void scrollTo(WebElement we){
        JavascriptExecutor je = (JavascriptExecutor) CommonResources.browserDriver;
        je.executeScript("arguments[0].scrollIntoView(true);", we);
    }

    public static void scrollTo(WebElement we, WebDriver d){
        JavascriptExecutor je = (JavascriptExecutor) d;
        je.executeScript("arguments[0].scrollIntoView(true);", we);
    }

    public static void clickFlexTextTab() throws InterruptedException{
        WebElement ft = CommonResources.browserDriver.findElement(By.linkText("FlexText"));
        scrollTo(ft);
        Thread.sleep(10000);
        ft.click();
        System.out.println("Clicked FlexText tab.");
    }

    public static void clickJumpToPage(){
        WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 60);
        WebElement jumpToPageIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(CommonResources.cssSelectorJumpToPage)));
        jumpToPageIcon.click();

        System.out.println("Clicked Jump To Page icon.");
    }

    public static void clickSearch() throws InterruptedException{
        WebElement search = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorSearch);
        search.click();
    }
    public static void clickSearchGo() throws InterruptedException{
        WebElement searchGo = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorSearchGo);
        Thread.sleep(1000);
        scrollTo(searchGo);
        searchGo.click();
    }

    public static void clickProfile() throws InterruptedException {
        Thread.sleep(500);
        WebElement profile = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorProfile);
        scrollTo(profile);
        Thread.sleep(500);
        profile.click();
    }
    public static void dragAndDrop(WebElement object, WebElement to) {
        Actions actions = new Actions(CommonResources.browserDriver);
        actions.dragAndDrop(object, to).build().perform();
    }

    public static void clickAndHold(WebElement from, WebElement to) throws InterruptedException {
        Actions actions = new Actions(CommonResources.browserDriver);
        actions.clickAndHold(from).moveToElement(to).build().perform();
    }

    public static void release(WebElement to) {
        Actions actions = new Actions(CommonResources.browserDriver);
        actions.release(to).build().perform();
    }

    public static void clickAndHoldAndMoveByOffset(WebElement from, WebElement to, int x, int y) throws InterruptedException {
        Actions actions = new Actions(CommonResources.browserDriver);
        actions.clickAndHold(from).moveToElement(to).moveByOffset(x, y).build().perform();
    }

    public static void browserAlertAction(String res) {
        switch (res) {
            case "OK": CommonResources.browserDriver.switchTo().alert().accept();
            break;

            case "Cancel" : CommonResources.browserDriver.switchTo().alert().dismiss();
            break;
        }
    }


}