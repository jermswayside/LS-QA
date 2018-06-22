package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import Objects.Test;

import java.util.ArrayList;
import java.util.List;

public class ContentManagerTests {
    private static String currCat = CommonResources.getAllCategories().get(4);
    public static void contentIcons() throws InterruptedException{
        Test currTest = new Test(currCat, "Check Content Icons", "", "");
        long start = System.nanoTime();
        ArrayList<String> icons = CommonResources.getIconsClass();
        List<WebElement> allFolders = getFolders();
        search:
        for(int i = 1; i<allFolders.size(); i++){
            allFolders = getFolders();
            WebElement folder = allFolders.get(i);
            while(!folder.isDisplayed()){}

            UINavigation.scrollTo(folder);
            Thread.sleep(1000);

            folder.click();

            allFolders = getFolders();

            folder = allFolders.get(i);

            List<WebElement> foldersLvl2 = getSubFolders(folder);

            int folderCnt = 0;
            for(WebElement f: foldersLvl2) {
                allFolders = getFolders();
                foldersLvl2 = getSubFolders(allFolders.get(i));
                f = foldersLvl2.get(folderCnt);
                UINavigation.scrollTo(f);
                Thread.sleep(1000);
                f.click();

                allFolders = getFolders();
                foldersLvl2 = getSubFolders(allFolders.get(i));
                f = foldersLvl2.get(folderCnt);

                WebElement togglerLvl2 = getToggler(f);
                Thread.sleep(1000);
                if(togglerLvl2 == null) {
                    checkIcons(icons);
                    if(icons.isEmpty()){
                        break search;
                    }
                }
                else{
                    List<WebElement> subfolders = getSubFolders(f);
                    for(int y=0; y<subfolders.size(); y++){
                        allFolders = getFolders();
                        foldersLvl2 = getSubFolders(allFolders.get(i));
                        f = foldersLvl2.get(folderCnt);
                        subfolders = getSubFolders(f);
                        WebElement subfolder = subfolders.get(y);
                        Thread.sleep(1000);
                        UINavigation.scrollTo(subfolder);
                        Thread.sleep(1000);
                        subfolder.click();
                        checkIcons(icons);
                        if(icons.isEmpty()){
                            break search;
                        }
                    }
                }
                folderCnt++;
            }
            WebElement togglerLvl1 = getToggler(folder);
            UINavigation.scrollTo(togglerLvl1);
            Thread.sleep(1000);

            togglerLvl1.click();
        }
        long end = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(start, end);
        }
        else if (CommonResources.qaTestMode.equals("n")) {
            String time = Utility.readableTime(start, end);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    public static void viewAssignGradesAttemptLinks() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Sublinks", "", "");
        long start = System.nanoTime();
        Thread.sleep(2000);
        ArrayList<String> icons = CommonResources.getIconsClass();
        List<WebElement> allFolders = getFolders();
        search:
        for(int i = 1; i<allFolders.size(); i++){
            allFolders = getFolders();
            WebElement folder = allFolders.get(i);
            while(!folder.isDisplayed()){}

            UINavigation.scrollTo(folder);
            Thread.sleep(1000);

            folder.click();

            allFolders = getFolders();
            folder = allFolders.get(i);
            List<WebElement> foldersLvl2 = getSubFolders(folder);
            int folderCnt = 0;
            for(WebElement f: foldersLvl2) {
                allFolders = getFolders();
                foldersLvl2 = getSubFolders(allFolders.get(i));
                f = foldersLvl2.get(folderCnt);
                UINavigation.scrollTo(f);
                Thread.sleep(1000);
                f.click();
                allFolders = getFolders();
                foldersLvl2 = getSubFolders(allFolders.get(i));
                f = foldersLvl2.get(folderCnt);
                WebElement togglerLvl2 = getToggler(f);
                Thread.sleep(1000);
                if(togglerLvl2 == null) {
                    checkSubLinks(icons);
                    if(icons.isEmpty()){
                        break search;
                    }
                }
                else{
                    List<WebElement> subfolders = getSubFolders(f);
                    for(int x = 0; x<subfolders.size(); x++){
                        Thread.sleep(1000);
                        allFolders = getFolders();
                        foldersLvl2 = getSubFolders(allFolders.get(i));
                        f = foldersLvl2.get(folderCnt);
                        subfolders = getSubFolders(f);
                        WebElement subfolder = subfolders.get(x);
                        UINavigation.scrollTo(subfolder);
                        Thread.sleep(1000);
                        subfolder.click();
                        checkSubLinks(icons);
                        if(icons.isEmpty()){
                            break search;
                        }
                    }
                    System.out.println("Yep");
                }
                folderCnt++;
            }
            WebElement togglerLvl1 = getToggler(folder);
            UINavigation.scrollTo(togglerLvl1);
            Thread.sleep(1000);

            togglerLvl1.click();
        }

        long end = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(start, end);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(start, end));
        }
    }

    public static void assigning() throws InterruptedException{
        Test currTest = new Test(currCat, "Check Assigning from Content", "", "");
        long start = System.nanoTime();
        ArrayList<String> icons = CommonResources.getIconsClass();
        List<WebElement> allFolders = getFolders();
        search:
        for(int i = 1; i<allFolders.size(); i++) {
            allFolders = getFolders();
            WebElement folder = allFolders.get(i);
            while (!folder.isDisplayed()) {
            }
            UINavigation.scrollTo(folder);
            Thread.sleep(1000);

            folder.click();
            allFolders = getFolders();
            folder = allFolders.get(i);
            List<WebElement> foldersLvl2 = getSubFolders(folder);
            int folderCnt = 0;
            for(WebElement f: foldersLvl2) {
                allFolders = getFolders();
                foldersLvl2 = getSubFolders(allFolders.get(i));
                f = foldersLvl2.get(folderCnt);
                UINavigation.scrollTo(f);
                Thread.sleep(1000);
                f.click();
                allFolders = getFolders();
                foldersLvl2 = getSubFolders(allFolders.get(i));
                f = foldersLvl2.get(folderCnt);
                WebElement togglerLvl2 = getToggler(f);
                Thread.sleep(1000);
                if(togglerLvl2 == null) {
                    assign(icons);
                    if(icons.isEmpty()) {
                        break search;
                    }
                }
                else{
                    List<WebElement> subfolders = getSubFolders(f);
                    for(int x = 0; x<subfolders.size(); x++){
                        Thread.sleep(1000);
                        allFolders = getFolders();
                        foldersLvl2 = getSubFolders(allFolders.get(i));
                        f = foldersLvl2.get(folderCnt);
                        subfolders = getSubFolders(f);
                        WebElement subfolder = subfolders.get(x);
                        UINavigation.scrollTo(subfolder);
                        Thread.sleep(1000);
                        subfolder.click();
                        assign(icons);
                        if(icons.isEmpty()) {
                            break search;
                        }
                    }
                    System.out.println("Yep");
                }
                folderCnt++;
            }
        }
        long end = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(start, end);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(start, end));
        }
    }

    private static void assign(ArrayList icons) throws InterruptedException {
        Thread.sleep(500);
        List<WebElement> items = getCourseItems();
        int i = 0;
        for (WebElement item : items) {
            Thread.sleep(500);
            items = getCourseItems();
            item = items.get(i);
            UINavigation.scrollTo(item);
            WebElement icon = getIcon(item);
            String iconName = getIconString(icon, icons);
            List<WebElement> subLinks = getSubLinks(item);
            if (!iconName.equals("")) {
                for (int j = 0; j < subLinks.size(); j++) {
                    WebElement currLink = Utility.waitForClickable(subLinks.get(j));
                    if (currLink.getText().equals("Assign")) {
                        currLink.click();
                        Thread.sleep(500);
                        if (popUpAppeared()) {

                            Thread.sleep(500);

                            WebElement selectAll = getSelectAll();

                            selectAll.click();

                            UINavigation.clickActiveNextStep();
                            Thread.sleep(500);

                            UINavigation.clickChooseDate();
                            Thread.sleep(500);

                            UINavigation.clickCurrDay();
                            Thread.sleep(500);

                            try {
                                UINavigation.clickSave();
                            }
                            catch (NoSuchElementException n) {
                                UINavigation.clickAssign();
                            }

                            Thread.sleep(4000);

                        }
                    }
                }
                icons.remove(iconName);
                System.out.printf("Removed %s", iconName);
                System.out.println();

                WebElement messageBox = getMessageBox();
                Utility.waitForClickable(messageBox);
                messageBox.click();
            }
            i++;
        }

    }

    private static WebElement getMessageBox() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorMessageBox);
    }

    private static WebElement getSelectAll() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorSelectAll);
    }

    private static void checkSubLinks(ArrayList icons) throws InterruptedException {
        List<WebElement> items = getCourseItems();
        int i = 0;
        for (WebElement item : items) {
            Thread.sleep(500);
            items = getCourseItems();
            WebElement icon = getIcon(items.get(i));
            UINavigation.scrollTo(icon);
            String iconName = getIconString(icon, icons);
            if (!iconName.equals("")) {
                items = getCourseItems();
                item = items.get(i);
                List<WebElement> links = getSubLinks(item);
                for (int j = 0; j < links.size(); j++) {
                    Thread.sleep(500);
                    items = getCourseItems();
                    item = items.get(i);
                    links = getSubLinks(item);
                    WebElement currLink = links.get(j);
                    UINavigation.scrollTo(item);
                    String currUrl = CommonResources.browserDriver.getCurrentUrl();
                    currLink.click();
                    Thread.sleep(500);
                    String newUrl = CommonResources.browserDriver.getCurrentUrl();
                    if (popUpAppeared()) {
                        Thread.sleep(500);
                        UINavigation.clickX();
                    } else if (!currUrl.equals(newUrl)) {
                        String iconType = CommonResources.getIconTypes().get(iconName);
                        if (!correctRedirect(newUrl, iconType)) {
                            System.out.println("Error");
                        }
//                        if(iconType.equals("quiz")) {
////                            try {
////                                Thread.sleep(2000);
////                                WebElement title = getTitle();
////                                Utility.waitForVisible(title);
////                            }
////                            catch (NoSuchElementException n){
////                                System.out.println("Error");
////                            }
//                        }
                        CommonResources.browserDriver.get(currUrl);
                    }
                }
                icons.remove(iconName);
                System.out.printf("Removed %s", iconName);
                System.out.println();
            }
            i++;
        }
    }

    private static WebElement getStartButton() {
        try {
            return CommonResources.browserDriver.findElement(
                    By.cssSelector(CommonResources.cssSelectorQuizStartButton));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }
    private static WebElement getTitle() {
        try {
            return CommonResources.browserDriver.findElement(
                    By.cssSelector(CommonResources.cssSelectorQuizTitle));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }
    private static List<WebElement> getSubLinks(WebElement item) {
        try {
            return item.findElements(By.cssSelector(CommonResources.cssSelectorSubLinks));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static boolean correctRedirect(String newUrl, String type){
        if(newUrl.indexOf(type) > 0){
            return true;
        }
        return false;
    }
    private static boolean popUpAppeared(){
        WebElement popup;
        try {
            popup = CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorAssignmentPopup));
        }
        catch (NoSuchElementException n){
            return false;
        }
        return popup.isDisplayed();
    }
    private static List<WebElement> getFolders() throws InterruptedException {
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorContentFolders);
    }


    private static WebElement getToggler(WebElement f){
        try {
            return f.findElement(By.cssSelector(CommonResources.cssSelectorCurrFolderToggler));
        }
        catch (NoSuchElementException e){
            return null;
        }
    }

    private static List<WebElement> getSubFolders(WebElement f){
        WebElement list = f.findElement(By.cssSelector("ul"));
        while(!list.isDisplayed()){}
        return list.findElements(By.xpath("*"));
    }

    private static List<WebElement> getCourseItems() throws InterruptedException{
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorNavigationItems);
    }

    private static WebElement getIcon(WebElement w){
        return w.findElement(By.cssSelector("a"));
    }

    private static String getIconString(WebElement i, ArrayList<String> list) {
        String s = i.getAttribute("class");
        for (String icon : list) {
            if (s.indexOf(icon) > 0) {
                return icon;
            }
        }
        return "";
    }

    private static void checkIcons(ArrayList<String> icons) throws InterruptedException{
        List<WebElement> items = getCourseItems();
        int i = 0;
        for(WebElement item: items){
            items = getCourseItems();
            WebElement icon = getIcon(items.get(i));
            UINavigation.scrollTo(icon);
            String iconStr = getIconString(icon, icons);
            if(!iconStr.equals("")){
                icons.remove(iconStr);
                String currentUrl = CommonResources.browserDriver.getCurrentUrl();
                icon.click();
                if(currentUrl.equals(CommonResources.browserDriver.getCurrentUrl())) {
                    closeTabs();
                }
                else{
                    CommonResources.browserDriver.get(currentUrl);
                }
                System.out.println("removed " + iconStr);
            }
            i++;
        }
    }

    private static void closeTabs(){
        String homeWindow = CommonResources.browserDriver.getWindowHandle();
        if(CommonResources.browserDriver.getWindowHandles().size() == 1){
            System.out.println("Error");
        }
        int i = 0;
        for(String winHandle: CommonResources.browserDriver.getWindowHandles()){
            System.out.println(winHandle);
            CommonResources.browserDriver.switchTo().window(winHandle);
            if(i == 1){
                CommonResources.browserDriver.close();
            }
            i++;
        }
        CommonResources.browserDriver.switchTo().window(homeWindow);
    }
}
