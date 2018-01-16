package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContentManagerTests {
    public static void checkContentIcons() throws InterruptedException{
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

            List<WebElement> foldersLvl2 = getSubFolders(folder);
            int folderCnt = 0;
            for(WebElement f: foldersLvl2) {
                Thread.sleep(5000);
                allFolders = getFolders();
                foldersLvl2 = getSubFolders(allFolders.get(i));
                f = foldersLvl2.get(folderCnt);
                UINavigation.scrollTo(f);
                Thread.sleep(1000);
                f.click();
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
                    System.out.println("Yep");
                }
                folderCnt++;
            }
            WebElement togglerLvl1 = getToggler(folder);
            UINavigation.scrollTo(togglerLvl1);
            Thread.sleep(1000);

            togglerLvl1.click();
        }
    }

    public static void checkViewAssignGradesAttemptLinks() throws InterruptedException{
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

            List<WebElement> foldersLvl2 = getSubFolders(folder);
            int folderCnt = 0;
            for(WebElement f: foldersLvl2) {
                allFolders = getFolders();
                foldersLvl2 = getSubFolders(allFolders.get(i));
                f = foldersLvl2.get(folderCnt);
                UINavigation.scrollTo(f);
                Thread.sleep(1000);
                f.click();
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

    }

    public static void checkAssigning() throws InterruptedException{
        Thread.sleep(2000);
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

            List<WebElement> foldersLvl2 = getSubFolders(folder);
            int folderCnt = 0;
            for(WebElement f: foldersLvl2) {
                allFolders = getFolders();
                foldersLvl2 = getSubFolders(allFolders.get(i));
                f = foldersLvl2.get(folderCnt);
                UINavigation.scrollTo(f);
                Thread.sleep(1000);
                f.click();
                WebElement togglerLvl2 = getToggler(f);
                Thread.sleep(1000);
                if(togglerLvl2 == null) {
                    assign();
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
                        assign();
                    }
                    System.out.println("Yep");
                }
                folderCnt++;
            }
        }
    }

    private static void assign() throws InterruptedException {
        Thread.sleep(500);
        List<WebElement> items = getCourseItems();
        int i = 0;
        for (WebElement item : items) {
            Thread.sleep(500);
            items = getCourseItems();
            item = items.get(i);
            UINavigation.scrollTo(item);
            List<WebElement> subLinks = getSubLinks(item);
            for(int j=0; j<subLinks.size(); j++){
                WebElement currLink = subLinks.get(j);
                if(currLink.getText().equals("Assign")){
                    currLink.click();
                    Thread.sleep(500);
                    if(popUpAppeared()){

                        Thread.sleep(500);

                        WebElement selectAll = getSelectAll();

                        selectAll.click();

                        UINavigation.clickActiveNextStep();
                        Thread.sleep(500);

                        UINavigation.clickChooseDate();
                        Thread.sleep(500);

                        UINavigation.clickCurrDay();
                        Thread.sleep(500);

                        UINavigation.clickSave();
                        Thread.sleep(500);
                    }
                }
            }
        }
    }

    private static WebElement getSelectAll() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorSelectAll);
    }

    /*TODO: Audio and Video assignments are bugged*/
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
    private static List<WebElement> getFolders() {
        return CommonResources.browserDriver.findElements(
                By.cssSelector(CommonResources.cssSelectorContentFolders));
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
        Thread.sleep(5000);
        List<WebElement> list = Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorNavigationItems);
        return list;
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
        Thread.sleep(1000);
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
