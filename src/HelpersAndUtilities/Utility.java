package HelpersAndUtilities;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import Objects.Test;
import Objects.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

// This class is used for testing functionality that are one-off/uncommonly used.

public class Utility {
    public static int randomInt(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }


    public static WebDriver startBrowser(String driver, String path, int choice){
        System.setProperty(driver, path);
        WebDriver currDriver = null;
        if (path.contains("chromedriver.exe")){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--use-fake-ui-for-media-stream=1");
            currDriver = new ChromeDriver(options);
            currDriver.manage().window().maximize();
        }
        else if(path.contains("geckodriver.exe")){
            currDriver = new FirefoxDriver();
        }
        if(choice == 1){
            currDriver.get(CommonResources.urlStageLS);
        }
        if(choice == 2){
            currDriver.get(CommonResources.urlProdLS);
        }
        if(choice == 3) {
            currDriver.get(CommonResources.urlDevLS);
        }
        return currDriver;
    }

    public static WebElement getRemoveConfirm() throws InterruptedException{
        List<WebElement> allRemoveConfirmButtons = CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorRemoveConfirm));
        return allRemoveConfirmButtons.get(allRemoveConfirmButtons.size()-1);
    }

    public static void login(User user, WebDriver driver) throws InterruptedException {

        //Finds the Login button on the Login screen and clicks on the button
        WebElement element = (new WebDriverWait(driver, 60))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(CommonResources.cssClassButtonLogin)));
        element.click();

        System.out.println(String.format("Now logging in as \"%s\"", user.getUsername()));

        //Finds the username field on the Login screen and enters the username
        WebElement username = driver.findElement(By.cssSelector(CommonResources.cssSelectorUsername));
        username.sendKeys(user.getUsername());

        //Finds the password field on the Login screen and enters the password, then submit
        WebElement password = driver.findElement(By.cssSelector(CommonResources.cssSelectorPassword));
        password.sendKeys(user.getPassword());

        Thread.sleep(1000);

        WebElement login = driver.findElement(By.cssSelector(CommonResources.cssSelectorLogin));
        login.click();

    }

    public static void logout() throws InterruptedException {
        WebElement logout = waitForElementToExistByCssSelector(CommonResources.cssSelectorLogout);
        waitForVisible(logout);
        Thread.sleep(4000);
        if(Utility.getMessageBox().isDisplayed()){
            Utility.getMessageBox().click();
        }
        logout.click();
    }


    public static void simpleDeleteAssignments() throws InterruptedException {
        System.out.println("Now deleting assignment(s)...");

        Thread.sleep(4000);

        List<WebElement> existingAssignments = CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorAssignments));

        if (existingAssignments.isEmpty()){
            System.out.println("There are no assignments created.  Exiting.");
            return;
        }

        UINavigation.clickTrashcan();

        UINavigation.clickCheckAll();

        UINavigation.clickRemoveSelected();

        Thread.sleep(2000);

        UINavigation.clickRemoveConfirm();

        Utility.waitForNotVisible(Utility.getRemoveConfirm());
    }

    public static void simpleSelectAssignment() throws InterruptedException {
        System.out.println("Now selecting assignment...");

        System.out.println("Waiting for course content to load...");

        WebElement f = (new WebDriverWait(CommonResources.browserDriver, 60))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(CommonResources.cssSelectorFolders)));

        WebElement target = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorDropContainer));

        List<WebElement> folders = CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorFolders));

        WebElement flvl1 = folders.get(1);
        flvl1.click();

        List<WebElement> folders2 = flvl1.findElements(By.cssSelector(CommonResources.cssSelectorFoldersLvl2));

        WebElement flvl2 = folders2.get(0);
        Utility.waitForClickable(flvl2);
        Thread.sleep(1000);
        flvl2.click();

        List<WebElement> folders3 = flvl2.findElements(By.cssSelector(CommonResources.cssSelectorFoldersLvl3));

        Thread.sleep(1000);
        Actions actions = new Actions(CommonResources.browserDriver);
        actions.dragAndDrop(folders3.get(0), target).build().perform();

        Thread.sleep(1000);
    }

    public static void createAssignment() throws InterruptedException {
        System.out.println("Now creating assignment...");

        UINavigation.clickNextStep();
        Thread.sleep(1000);

        UINavigation.clickActiveNextStep();
        Thread.sleep(1000);

        UINavigation.clickChooseDate();
        Thread.sleep(1000);

        UINavigation.clickCurrDay();
        Thread.sleep(1000);

        UINavigation.clickAssign();
    }

    public static void createAssignmentSelectedStudents() throws InterruptedException {
        System.out.println("Now creating assignment for one student only...");

        UINavigation.clickNextStep();
        Thread.sleep(1000);

        UINavigation.clickStudentCheckBox();
        Thread.sleep(1000);

        UINavigation.clickActiveNextStep();
        Thread.sleep(1000);

        UINavigation.clickChooseDate();
        Thread.sleep(1000);

        UINavigation.clickCurrDay();
        Thread.sleep(1000);

        UINavigation.clickAssign();
        Thread.sleep(1000);
    }

    public static boolean confirmAssignmentSelectedStudents(Test ct) throws InterruptedException {
        System.out.println("Now confirming assignments for assigned students...");

        CommonResources.assignedAssignments = getAssignments(CommonResources.cssSelectorAssignmentTitle);

        WebDriver qastudentBrowser = startBrowser(CommonResources.chromeDriver, CommonResources.pathChromeDriver, CommonResources.siteChoiceEntry);
        WebDriver qastudent1Browser = startBrowser(CommonResources.chromeDriver, CommonResources.pathChromeDriver, CommonResources.siteChoiceEntry);

        User student = new User(CommonResources.usernameStudent, CommonResources.passwordStudent);
        User student1 = new User(CommonResources.usernameStudent1, CommonResources.passwordStudent1);
        login(student, qastudentBrowser);
        login(student1, qastudent1Browser);

        Thread.sleep(5000);
        UINavigation.clickSkip(qastudentBrowser);
        UINavigation.clickSkip(qastudent1Browser);

        UINavigation.navToAssignment(qastudentBrowser);
        UINavigation.navToAssignment(qastudent1Browser);

        ArrayList<String> qastudentAssigned = getAssignments(CommonResources.cssSelectorStudentAssignmentTitle,
                qastudentBrowser);
        ArrayList<String> qastudent1Assigned = getAssignments(CommonResources.cssSelectorStudentAssignmentTitle,
                qastudent1Browser);

        if (qastudentAssigned.isEmpty()){
            Utility.addTestToTests(ct, CommonResources.fail, CommonResources.na);
            qastudentBrowser.close();
            qastudent1Browser.close();
            return false;
        }

        String assignmentTitle = CommonResources.assignedAssignments.get(0);
        String qastudentAssignmentTitle = qastudentAssigned.get(0);
        String qastudent1AssignmentTitle = qastudent1Assigned.get(0);

        if (Objects.equals(qastudentAssignmentTitle, assignmentTitle)){
            System.out.println(String.format("%s has been successfully added for qastudent!",
                    assignmentTitle));
            qastudentBrowser.close();
            qastudent1Browser.close();
            return true;
        }

        if (qastudent1Assigned.isEmpty()){
            System.out.println("There are no assignments assigned to qastudent1.");
            Utility.addTestToTests(ct, CommonResources.fail, CommonResources.na);
            qastudentBrowser.close();
            qastudent1Browser.close();
            return false;
        }
        else if (Objects.equals(qastudent1AssignmentTitle, assignmentTitle)){
            System.out.println(String.format("%s has been successfully added for qastudent1!",
                    assignmentTitle));
            qastudentBrowser.close();
            qastudent1Browser.close();
            return true;
        }
        return false;
    }

    public static ArrayList<String> getAssignments(String person) throws InterruptedException{
        Thread.sleep(5000);
        List<WebElement> createdAssignments = CommonResources.browserDriver.findElements(
                By.cssSelector(CommonResources.cssSelectorAssignments));

        ArrayList<String> createdAssignmentsText = new ArrayList<>();

        if(createdAssignments.isEmpty()){
            return createdAssignmentsText;
        }
        for(Iterator<WebElement> i = createdAssignments.iterator(); i.hasNext();){
            createdAssignmentsText.add(i.next().findElement(By.cssSelector(person)).getText());
        }

        return createdAssignmentsText;
    }

    public static ArrayList<String> getAssignments(String person, WebDriver driver){
        List<WebElement> createdAssignments = driver.findElements(
                By.cssSelector(CommonResources.cssSelectorAssignments));

        ArrayList<String> createdAssignmentsText = new ArrayList<>();

        if(createdAssignments.isEmpty()){
            return createdAssignmentsText;
        }

        for(Iterator<WebElement> i = createdAssignments.iterator(); i.hasNext();){
            createdAssignmentsText.add(i.next().findElement(By.cssSelector(person)).getText());
        }
        return createdAssignmentsText;
    }

    public static void writeToErrorLog(String fn, String msg) throws IOException{
        File fileName = new File(fn);
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(msg);
        bw.close();
    }

    public static String getCurrDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void browserBack(){
        JavascriptExecutor js = (JavascriptExecutor) CommonResources.browserDriver;
        js.executeScript("window.history.go(-1)");
    }

    public static void nanoToReadableTime(long start, long end){
        long difference = end - start;
        System.out.println("Total execution time: " +
                String.format("%d min: %d sec",
                        TimeUnit.NANOSECONDS.toMinutes(difference),
                        TimeUnit.NANOSECONDS.toSeconds(difference) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference))));
    }

    public static String readableTime(long start, long end) {
        long difference = end - start;
        long minutes = TimeUnit.NANOSECONDS.toMinutes(difference);
        long seconds = TimeUnit.NANOSECONDS.toSeconds(difference) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference));
        return String.format("%d min: %d sec", minutes, seconds);
    }

    public static WebElement waitForElementToExistByCssSelector(String cssSelector) throws InterruptedException{
        int waitTime = 5;
        int waitCount = 0;
        while(waitCount <= 3){
            try {
                return CommonResources.browserDriver.findElement(By.cssSelector(cssSelector));
            }
            catch (NoSuchElementException n) {
                waitCount++;
                System.out.println(String.format("Now waiting %s seconds at wait count %s.", waitTime, waitCount));
                Thread.sleep(waitTime*1000);
            }
        }
        return null;
    }

    public static Select waitForSelectElementToExistByCssSelector(String cssSelector) throws InterruptedException{
        int waitTime = 5;
        int waitCount = 0;
        while(waitCount <= 3){
            try {
                return new Select(CommonResources.browserDriver.findElement(By.cssSelector(cssSelector)));
            }
            catch (NoSuchElementException n) {
                waitCount++;
                System.out.println(String.format("Now waiting %s seconds at wait count %s.", waitTime, waitCount));
                Thread.sleep(waitTime*1000);
            }
        }
        return null;
    }

    public static List<WebElement> waitForElementsToExistByCssSelector(String cssSelector) throws InterruptedException{
        int waitTime = 5;
        int waitCount = 0;
        List<WebElement> currList = CommonResources.browserDriver.findElements(By.cssSelector(cssSelector));
        while(waitCount <= 3 && currList.size() == 0){
            waitCount++;
            System.out.println(String.format("Now waiting %s seconds at wait count %s.", waitTime, waitCount));
            Thread.sleep(waitTime*1000);
            currList = CommonResources.browserDriver.findElements(By.cssSelector(cssSelector));

        }
        if(currList.size() > 0) {
            return currList;
        }
        return null;
    }

    public static WebElement waitForElementToExistByLinkText(String linkText) throws InterruptedException {
        int waitTime = 7;
        int waitCount = 0;
        while(waitCount <= 3){
            try {
                return CommonResources.browserDriver.findElement(By.linkText(linkText));
            }
            catch (NoSuchElementException n) {
                waitCount++;
                System.out.println(String.format("Now waiting %s seconds at wait count %s.", waitTime, waitCount));
                Thread.sleep(waitTime*1000);
            }
        }
        return null;
    }

    public static List<WebElement> waitForElementsToExistByLinkText(String linkText) throws InterruptedException {
        int waitTime = 10;
        int waitCount = 0;
        while(waitCount <= 3){
            try {
                return CommonResources.browserDriver.findElements(By.linkText(linkText));
            }
            catch (NoSuchElementException n) {
                waitCount++;
                Thread.sleep(waitTime*1000);
                System.out.println(String.format("Now waiting %s seconds at wait count %s.", waitTime, waitCount));
            }
        }
        return null;
    }

    public static WebElement waitForElementToExistByXpath(String xpath) throws InterruptedException {
        int waitTime = 7;
        int waitCount = 0;
        while(waitCount <= 3){
            try {
                return CommonResources.browserDriver.findElement(By.xpath(xpath));
            }
            catch (NoSuchElementException n) {
                waitCount++;
                System.out.println(String.format("Now waiting %s seconds at wait count %s.", waitTime, waitCount));
                Thread.sleep(waitTime*1000);
            }
        }
        return null;
    }

    public static  void wait(int seconds) throws InterruptedException {
        int waitTime = seconds;
        int waitCount = 0;
        while(waitCount <= 3) {
            Thread.sleep(waitTime*1000);
            waitCount++;
        }
    }

    public static boolean waitForNotVisible(WebElement w){
        WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 60);
        return wait.until(ExpectedConditions.invisibilityOf(w));
    }

    public static void waitForVisible(List<WebElement> w){
        WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 60);
        wait.until(ExpectedConditions.visibilityOfAllElements(w));
    }

    public static void waitForVisible(WebElement w){
        WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 60);
        wait.until(ExpectedConditions.visibilityOf(w));
    }

    public static WebElement waitForClickable(WebElement w){
        WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 60);
        return wait.until(ExpectedConditions.elementToBeClickable(w));
    }

    public static WebElement getMessageBox() throws InterruptedException {
        return waitForElementToExistByCssSelector(CommonResources.cssSelectorMessageBox);
    }

    public static int getSiteVersion() {
        Scanner siteChoice = new Scanner(System.in);
        System.out.println("Which version of Learning Site would you like to use?:\n" +
                "\"1\" - Stage\n" +
                "\"2\" - Production\n" +
                "\"3\" - Development");
        return siteChoice.nextInt();
    }

    public static void printPretty() {
        int longestCatLen = CommonResources.tests.get(0).getCategories().length();
        int longestNameLen = CommonResources.tests.get(0).getName().length();
        int longestPassFailLen = CommonResources.tests.get(0).getPassFail().length();
        int longestTimeLen = CommonResources.tests.get(0).getTime().length();

        for(int i = 0; i<CommonResources.tests.size(); i++){
            Test currTest = CommonResources.tests.get(i);
            int currCatLen = currTest.getCategories().length();
            int currNameLen = currTest.getName().length();
            int currPassFailLen = currTest.getPassFail().length();
            int currTimeLen = currTest.getTime().length();

            if(currCatLen > longestCatLen) {
                longestCatLen = currCatLen;
            }
            if(currNameLen > longestNameLen) {
                longestNameLen = currNameLen;
            }
            if(currPassFailLen > longestPassFailLen) {
                longestPassFailLen = currPassFailLen;
            }
            if(currTimeLen > longestTimeLen) {
                longestTimeLen = currTimeLen;
            }
        }

        int catFormatSpace = longestCatLen+4;
        int nameFormatSpace = longestNameLen+4;
        int passFailFormatSpace = longestPassFailLen+4;
        int timeFormatSpace = longestTimeLen+4;

        int total = catFormatSpace + nameFormatSpace + passFailFormatSpace + timeFormatSpace;

        System.out.printf("%-"+ catFormatSpace + "s%-" + nameFormatSpace + "s%-" + passFailFormatSpace + "s%-" + timeFormatSpace + "s\n",
                "CATEGORY", "NAME", "P/F", "Time");

        for(int i = 0; i<total; i++){
            System.out.print("-");
        }
        System.out.println();

        for(int i = 0; i<CommonResources.tests.size(); i++) {
            Test currTest = CommonResources.tests.get(i);
            System.out.printf("%-"+ catFormatSpace + "s%-" + nameFormatSpace + "s%-" + passFailFormatSpace + "s%-" + timeFormatSpace + "s\n",
                    currTest.getCategories(), currTest.getName(), currTest.getPassFail(), currTest.getTime());

        }
    }

    public static void addTestToTests(Test te, String pf, String ti) {
        te.setPassFail(pf);
        te.setTime(ti);
        CommonResources.tests.add(te);
    }


    public static void switchUsers(User to, WebDriver wd) throws InterruptedException {
        logout();
        login(to, wd);
    }

    public static String getSiteVersionQuiz() {
        if(CommonResources.siteChoiceEntry == 1) {
            return "https://stagelearningsite.waysidepublishing.com/explorer/5344303/quiz/5238761/start";
        }
        if(CommonResources.siteChoiceEntry == 2) {
            return "https://learningsite.waysidepublishing.com/explorer/5238731/quiz/5238761/start";
        }
        System.out.println("Dev does not have QA Textbook");
        return "";
    }

    public static WebElement getQuizStartButton() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorQuizStartButton);
    }

    public static WebElement getCancelButton() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorQuizCancelButton);
    }

    public static String getSiteVersionFlashcard() {
        if(CommonResources.siteChoiceEntry == 1) {
            return "https://stagelearningsite.waysidepublishing.com/explorer/3448380/flashcards/1255357/start";
        }
        else if(CommonResources.siteChoiceEntry == 2) {
            return "https://learningsite.waysidepublishing.com/explorer/3448380/flashcards/1255357/start";
        }
        else if(CommonResources.siteChoiceEntry == 3) {
            return "https://devlearningsite.waysidepublishing.com/explorer/3448380/flashcards/1255357/start";
        }
        return "";
    }
}