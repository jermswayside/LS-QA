package HelpersAndUtilities;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

// This class is used for testing functionality that are one-off/uncommonly used.

public class Utility {

    public static void courseCount() throws InterruptedException {
        System.out.println("Checking course book count...");

        WebElement newCourse = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorNewCourse));
        newCourse.click();
        System.out.println("Clicked \"New Course\".");
        Thread.sleep(2000);

        WebElement content = CommonResources.browserDriver.findElement(
                By.cssSelector(CommonResources.cssSelectorCourseContent));
        int booksCount = content.findElements(By.xpath("*")).size();
        System.out.println("Finding books count...");
        System.out.println(String.format("Books found: %d", booksCount));

        if (booksCount < 3){
            System.out.println("There aren't enough books loaded.  Contact the administrator.");
            System.exit(0);
        }
    }

    public static WebDriver startBrowser(String driver, String path){
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
        currDriver.get(CommonResources.urlDevLS);
        return currDriver;
    }

    public static WebElement getRemoveConfirm() throws InterruptedException{
        List<WebElement> allRemoveConfirmButtons = CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorRemoveConfirm));
        return allRemoveConfirmButtons.get(allRemoveConfirmButtons.size()-1);
    }

    public static void login(String user, String pass, WebDriver driver) throws InterruptedException {

        //Finds the Login button on the Login screen and clicks on the button
        WebElement element = (new WebDriverWait(driver, 60))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(CommonResources.cssClassButtonLogin)));
        element.click();

        System.out.println(String.format("Now logging in as \"%s\"", user));

        //Finds the username field on the Login screen and enters the username
        WebElement username = driver.findElement(By.cssSelector(CommonResources.cssSelectorUsername));
        username.sendKeys(user);

        //Finds the password field on the Login screen and enters the password, then submit
        WebElement password = driver.findElement(By.cssSelector(CommonResources.cssSelectorPassword));
        password.sendKeys(pass, Keys.ENTER);

    }

    public static void logout(){
        WebElement logout = CommonResources.browserDriver.findElement(By.linkText(CommonResources.cssLinkTextLogout));
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
        flvl2.click();

        List<WebElement> folders3 = flvl2.findElements(By.cssSelector(CommonResources.cssSelectorFoldersLvl3));

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
        Thread.sleep(1000);
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

    public static void confirmAssignmentSelectedStudents() throws InterruptedException {
        System.out.println("Now confirming assignments for assigned students...");

        CommonResources.assignedAssignments = getAssignments(CommonResources.cssSelectorAssignmentTitle);

        WebDriver qastudentBrowser = startBrowser(CommonResources.chromeDriver, CommonResources.pathChromeDriver);
        WebDriver qastudent1Browser = startBrowser(CommonResources.chromeDriver, CommonResources.pathChromeDriver);

        login(CommonResources.usernameStudent, CommonResources.passwordStudent, qastudentBrowser);
        login(CommonResources.usernameStudent1, CommonResources.passwordStudent1, qastudent1Browser);

        Thread.sleep(5000);
        UINavigation.clickSkip(qastudentBrowser);
        UINavigation.clickSkip(qastudent1Browser);

        UINavigation.navToAssignment(qastudentBrowser);
        UINavigation.navToAssignment(qastudent1Browser);

        ArrayList<String> qastudentAssigned = getAssignments(CommonResources.cssSelectorStudentAssignmentTitle,
                qastudentBrowser);
        ArrayList<String> qastudent1Assigned = getAssignments(CommonResources.cssSelectorStudentAssignmentTitle,
                qastudent1Browser);


        String assignmentTitle = CommonResources.assignedAssignments.get(0);
        String qastudentAssignmentTitle = qastudentAssigned.get(0);
        String qastudent1AssignmentTitle = qastudent1Assigned.get(0);

        if (qastudentAssigned.isEmpty()){
            System.out.println("There are no assignments assigned to qastudent.");
        }

        else if (Objects.equals(qastudentAssignmentTitle, assignmentTitle)){
            System.out.println(String.format("%s has been successfully added for qastudent!",
                    assignmentTitle));
        }

        if (qastudent1Assigned.isEmpty()){
            System.out.println("There are no assignments assigned to qastudent1.");
        }
        else if (Objects.equals(qastudent1AssignmentTitle, assignmentTitle)){
            System.out.println(String.format("%s has been successfully added for qastudent1!",
                    assignmentTitle));
        }

        qastudentBrowser.close();
        qastudent1Browser.close();
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
        double elapsedTime = end-start/1000000000.0;
        int minutes = (int) (elapsedTime/60)%60;
        int seconds = (int) (elapsedTime%60);
        System.out.println(String.format("%s minutes and %s seconds", minutes, seconds));
    }

    public static WebElement waitForVisible(WebElement w){
        WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 60);
        return wait.until(ExpectedConditions.visibilityOf(w));
    }

    public static boolean waitForNotVisible(WebElement w){
        WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 60);
        return wait.until(ExpectedConditions.invisibilityOf(w));
    }

    public static void waitForVisible(List<WebElement> w){
        WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 60);
        wait.until(ExpectedConditions.visibilityOfAllElements(w));
    }
}