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

                Thread.sleep(8000);

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
                    UINavigation.scrollTo(currFT);
                    Thread.sleep(1000);
                    currFT.click();

                    UINavigation.clickSkip();
                    Thread.sleep(1000);

                    UINavigation.clickSearch();
                    WebElement searchInput = getSearchInput();
                    for(String word: CommonResources.getSearchWords()) {
                        enterWord(word, searchInput);
                        Thread.sleep(500);

                        UINavigation.clickSearchGo();
                        Thread.sleep(1000);

                        UINavigation.scrollTo(searchInput);
                        Thread.sleep(500);

                        List<WebElement> results = getResults();

                        System.out.println(word);
                        if(Objects.equals("pomme", word) || Objects.equals("notaword", word)){
                            if(results.size() > 0) {
                                System.out.println("Error");
                                return;
                            }
                            searchInput.clear();
                        }
                        else {
                            WebElement firstResult = results.get(0);
                            String titlePageNumber = getTitlePageNumberOfFirstResult(firstResult);
                            Thread.sleep(4000);
                            firstResult.click();
                            switchToFT();
                            String textBody = getTextBody();
                            int indBeg = textBody.indexOf("p. " + titlePageNumber);
                            int pageNumberNum = Integer.parseInt(titlePageNumber) + 1;
                            int indEnd = textBody.indexOf("p. " + pageNumberNum);

                            if (!getIndexOfWithin(word, textBody, indBeg, indEnd)) {
                                System.out.println("Did not return correct page.");
                            }
                            switchToMain();
                            searchInput.clear();
                            Thread.sleep(500);
                        }
                    }
                    UINavigation.clickFlexTextTab();
                }
            }
            else{
                continue;
            }
            UINavigation.navToDash();
        }
    }

    private static String getTextBody() {
        try {
            return CommonResources.browserDriver.findElement(By.tagName("body")).getAttribute("innerHTML");
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }
    private static String getTitlePageNumberOfFirstResult(WebElement firstResult) {
        try {
            return firstResult.findElement(By.cssSelector("div.page-b")).getText().split( " ")[1];
        }
        catch(NoSuchElementException n){
            return "";
        }
    }
    private static WebElement getSearchInput() throws InterruptedException{
        try {
            return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorSearchInput);
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static List<WebElement> getResults() {
        try {
            return CommonResources.browserDriver.findElements(
                    By.cssSelector(CommonResources.cssSelectorSearchResults));
        }
        catch (NoSuchElementException n) {
            return null;
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


                Thread.sleep(8000);

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

                    WebElement page = getPageNumberInput();

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

    private static WebElement getPageNumberInput() {
        try {
            return CommonResources.browserDriver.findElement(
                    By.cssSelector(CommonResources.cssSelectorPageInput));
        }
        catch (NoSuchElementException n) {
            return null;
        }
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
                            Thread.sleep(2000);
                            WebElement currCompass = allExplorerCompass.get(i);
                            UINavigation.scrollTo(currCompass);

                            currCompass.click();
                            Thread.sleep(2000);

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


    private static boolean navContainsFlexTextTab() throws InterruptedException{
        List<WebElement> nav = CommonResources.browserDriver.findElements(
                By.cssSelector(CommonResources.cssSelectorCourseNav));
        for(WebElement navTitle : nav){
            if(Objects.equals(navTitle.getText(), "FlexText")){
                return true;
            }
        }
        return false;
    }



    private static boolean checkCorrectPage(int pn) {
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
        return Utility.randomInt(10, max+1);
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

    private static List<WebElement> getAllFlexText() throws InterruptedException{
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorAllFlexText);
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
        WebElement e = CommonResources.browserDriver.findElement(By.xpath(CommonResources.cssXpathNoMatch));
        return true;
    }

    private static boolean getIndexOfWithin(String word, String page, int beginning, int end){
        String between = page.substring(beginning, end);
        if(between.indexOf(word) > 0){
            return true;
        }
        return false;
    }

    private static void checkCorrectReturns(String w) throws InterruptedException {
        if(Objects.equals(w,"un")){
            WebElement total = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorHitCount);
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
}



