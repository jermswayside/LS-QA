package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class FlexTextTests {
    public static void search() throws InterruptedException {
        long startTime = System.nanoTime();
        List<WebElement> allCourses = getAllCourses();
        ArrayList<String> allBooks = CommonResources.getAllBooks();
        for (WebElement course : allCourses) {
            String courseTitle = course.getText();
            if (allBooks.contains(courseTitle)) {
                UINavigation.scrollTo(course);

                UINavigation.accessCourse(courseTitle);

                UINavigation.clickSkip();

                if (!navContainsFlexTextTab()) {
                    System.out.println(String.format("FlexText is not available for: %s", courseTitle));
                    UINavigation.navToDash();
                    continue;
                }

                UINavigation.clickFlexTextTab();
                int ftCount = getAllFlexText().size();
                for (int i=0;i<ftCount; i++) {
                    List<WebElement> allFT = getAllFlexText();
                    WebElement currFT = allFT.get(i);
                    System.out.println(String.format("Accessing flextext: %s", currFT.getText()));
                    Utility.waitForVisible(currFT);
                    UINavigation.scrollTo(currFT);
                    Thread.sleep(1000);
                    currFT.click();

                    UINavigation.clickSkip();
                    Thread.sleep(1000);

                    UINavigation.clickSearch();
                    WebElement searchInput = CommonResources.browserDriver.findElement(
                            By.cssSelector(CommonResources.cssSelectorSearchInput));
                    for(String word: CommonResources.getSearchWords()) {
                        enterWord(word, searchInput);
                        Thread.sleep(500);

                        UINavigation.clickSearchGo();
                        Thread.sleep(1000);

                        checkCorrectReturns(word);
                        Thread.sleep(500);

                        UINavigation.scrollTo(searchInput);
                        Thread.sleep(500);

                        searchInput.clear();
                        Thread.sleep(500);
                    }
                    UINavigation.clickFlexTextTab();
                }
            }
            UINavigation.navToDash();
        }
    }

    private static void checkCorrectReturns(String w) {
        if(Objects.equals(w,"un")){
            WebElement total = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorHitCount));
            Utility.waitForVisible(total);
            int totalNum = Integer.parseInt(total.getText());
            if(totalNum != 100){
                System.out.println("Incorrect return.");
            }
        }
        if(Objects.equals(w, "notaword")){
            if(!hasNoneReturnErrorMessage()){
                System.out.println("Incorrect return.");
            }
        }
    }

    public static void checkJumpToPage() throws InterruptedException, IOException{
        long startTime = System.nanoTime();
        List<WebElement> allCourses = getAllCourses();
        ArrayList<String> allBooks = CommonResources.getAllBooks();
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


                List<WebElement> allFT = getAllFlexText();

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

                    if(checkCorrectPage(randNum) || (!checkCorrectPage(randNum) && !hasLink(randNum))){
                        System.out.println("Redirected to the correct page.");
                        //System.out.println(Utility.getCurrDate());
                    }
                    else if(hasLink(randNum)){
                        String error = String.format(
                                "%s: The FlexText %s did not redirect to page %s correctly.",
                                Utility.getCurrDate(), currFT.getText(), randNum);
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
        long endTime = System.nanoTime();

        Utility.nanoToReadableTime(startTime, endTime);
    }

    public static void checkExplorerLinks() throws InterruptedException, IOException {
        long startTime = System.nanoTime();
        int courseSize = getAllCourses().size();
        for (int y = 0; y<courseSize; y++) {
            List<WebElement> allCourses = getAllCourses();
            WebElement currCourse = allCourses.get(y);
            ArrayList<String> allBooks = CommonResources.getAllBooks();
            String courseTitle = currCourse.getText();
            if (allBooks.contains(courseTitle)) {
                UINavigation.scrollTo(currCourse);

                UINavigation.accessCourse(courseTitle);

                UINavigation.clickSkip();

                if (!navContainsFlexTextTab()) {
                    System.out.println(String.format("FlexText is not available for: %s", courseTitle));
                    UINavigation.navToDash();
                    continue;
                }

                UINavigation.clickFlexTextTab();

                int allFlexCnt = getAllFlexText().size();
                for (int x = 1; x<allFlexCnt; x++) {
                    List<WebElement> allFT = getAllFlexText();
                    WebElement currFT = allFT.get(x);
                    System.out.println(String.format("Accessing flextext: %s", currFT.getText()));
                    UINavigation.scrollTo(currFT);
                    Thread.sleep(1000);
                    currFT.click();

                    UINavigation.clickSkip();
                    Thread.sleep(1000);

                    while(hasNextPageButton()) {
                        System.out.println(String.format("Now accessing: %s", getActiveSection().getText()));
                        WebElement nextPageButton = getNextPageButton();

                        switchToFT();

                        if (getAllExplorerCompass().isEmpty()) {

                            switchToMain();

                            System.out.println(String.format(
                                    "There are no explorer links on section: %s", getActiveSection().getText()));

                            UINavigation.scrollTo(nextPageButton);
                            Thread.sleep(1000);

                            System.out.println("Accessing next section...");
                            nextPageButton.click();

                            continue;
                        }

                        int size = getAllExplorerCompass().size();
                        for(int i=0; i<size; i++) {
                            List<WebElement> allExplorerCompass = getAllExplorerCompass();

                            WebElement currCompass = allExplorerCompass.get(i);
                            UINavigation.scrollTo(currCompass);

                            currCompass.click();
                            Thread.sleep(1500);

                            if (hasExplorerBox()) {
                                if (!hasExplorerLink()) {
                                    System.out.println(String.format(
                                            "Link doesn't exist for explorer compass: #%s", i));
                                    continue;
                                }
                                String currURL = CommonResources.browserDriver.getCurrentUrl();
                                int explorerSize = explorerCompassSize();
                                boolean switching = false;
                                for(int j=0; j<explorerSize; j++){
                                //    wait.until(ExpectedConditions.visibilityOfAllElements(getExplorerLink()));
                                    if(switching){
                                        allExplorerCompass = getAllExplorerCompass();
                                        currCompass = allExplorerCompass.get(i);
                                        currCompass.click();
                                        Thread.sleep(1000);
                                    }
                                    List<WebElement> currLinkList = getExplorerLink();
                                    WebElement currLink = currLinkList.get(j);
                                    UINavigation.scrollTo(getExplorerBox());
                                    Thread.sleep(500);
                                    String linkText = currLink.getText();
                                    currLink.click();
                                    String newURL = CommonResources.browserDriver.getCurrentUrl();
                                    if (Objects.equals(currURL, newURL)){
                                        String error = String.format(
                                                "%s did not redirect correctly.", linkText);
                                        Utility.writeToErrorLog(
                                                "FlexTextError.txt", error);
                                        System.out.println(error);
                                    }
                                    CommonResources.browserDriver.get(currURL);
                                    Thread.sleep(5000);
                                    switchToFT();
                                    switching = true;
                                }
                            }
                        }
                        Thread.sleep(1000);
                        switchToMain();
                        System.out.println(String.format(
                                "Link checking complete for section: %s", getActiveSection().getText()));

                        nextPageButton = getNextPageButton();
                        UINavigation.scrollTo(nextPageButton);
                        Thread.sleep(1000);

                        nextPageButton.click();
                    }
                    UINavigation.clickFlexTextTab();
                }
            }
            UINavigation.navToDash();
        }
        long endTime = System.nanoTime();
        System.out.println(String.format("Time it took to complete the test was: %s", endTime-startTime));
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

    private static List<WebElement> getAllExplorerCompass(){
        return CommonResources.browserDriver.findElements(By.cssSelector(
                        CommonResources.cssSelectorExplorerImg));
    }

    private static List<WebElement> getAllFlexText() {
        WebDriverWait wait = new WebDriverWait(CommonResources.browserDriver, 60);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(CommonResources.cssSelectorAllFlexText)));
    }

    private static List<WebElement> getAllCourses(){
        return CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorCourse));
    }

    private static WebElement getActiveSection() {
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorActiveChapter));
    }

    private static WebElement getNextPageButton() {
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorNextPage));
    }

    private static boolean hasNextPageButton() {
        try {
            getNextPageButton();
        }
        catch (NoSuchElementException n){
            return false;
        }
        return true;
    }

    private static boolean hasExplorerBox() {
        try {
            getExplorerBox();
        }
        catch (NoSuchElementException n){
            return false;
        }
        return true;
    }

    private static WebElement getExplorerBox() {
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorExplorerLinkBox));
    }

    private static boolean hasExplorerLink() {
        List<WebElement> l = getExplorerLink();
        if (l.size() == 0){
            return false;
        }
        return true;
    }

    private static List<WebElement> getExplorerLink(){
        return CommonResources.browserDriver.findElements(
                By.cssSelector(CommonResources.cssSelectorExplorerLink));
    }

    private static int explorerCompassSize(){
        return getExplorerLink().size();
    }

    private static boolean hasLink(int page) {
        String pageNum = "article#p" + page;
        try {
            WebElement p = CommonResources.browserDriver.findElement(By.cssSelector(pageNum));
            return true;
        } catch (NoSuchElementException n) {
            return false;
        }
    }

    private static void enterWord(String w, WebElement we){
        we.sendKeys(w);
    }

    private static boolean hasNoneReturnErrorMessage(){
        try{
            WebElement e = CommonResources.browserDriver.findElement(By.xpath(CommonResources.cssXpathNoMatch));
            Utility.waitForVisible(e);
            return true;
        }
        catch (NoSuchElementException n){
            return false;
        }
    }
}



