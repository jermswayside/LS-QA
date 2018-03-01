package Tests;
import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;

import java.util.*;


public class AssignmentTests {
    public static void selectEachAssignments() throws InterruptedException{
        long start = System.nanoTime();
        UINavigation.navToAssignment();

        Thread.sleep(1000);

        System.out.println("Now selecting each assignment...");

        ArrayList<String> icons = CommonResources.getIcons();
        UINavigation.clickSkip();
        boolean cached = false;
        while(!icons.isEmpty()){
            UINavigation.clickAddAssignments();

            System.out.println("Waiting for course content to load...");

            if(!cached) {
                Thread.sleep(20000);
                cached = true;
            }

            WebElement firstSelectorFolder = getFolders().get(0);

            List<WebElement> folders = getFolders();
            List<WebElement> foldersCloser = getFoldersCloser();
            WebElement dropLocation = getDropLocation();

            folder_loop:
            for(int i = 1; i<folders.size(); i++){
                Thread.sleep(500);
                WebElement currFolder = folders.get(i);
                UINavigation.scrollTo(currFolder);

                String folderTitle = currFolder.getText();

                System.out.println(String.format("Expanding folder \"%s\".", folderTitle));

                currFolder.click();

                List<WebElement> foldersLvl2 = getFoldersLvl2(currFolder);

                List<WebElement> foldersLvl2Closer = getFoldersLvl2Closer(currFolder);

                for(int j = 0; j<foldersLvl2.size(); j++){
                    Thread.sleep(500);
                    WebElement currInnerFolder = foldersLvl2.get(j);

                    WebElement currInnerFolderCloser = foldersLvl2Closer.get(j);

                    String folderLvl2Title = currInnerFolder.getText();

                    System.out.println(String.format("Expanding folder in folder \"%s\": \"%s\".", folderTitle, currInnerFolder.getText()));

                    UINavigation.scrollTo(currInnerFolder);
                    currInnerFolder.click();

                    List<WebElement> foldersLvl3 = getFoldersLvl3(currInnerFolder);
                    System.out.println(String.format("There are %s items in inner folder \"%s\"", foldersLvl3.size(),folderLvl2Title));
                    for (int k = 0; k<foldersLvl3.size(); k++){
                        String currIcon;
                        WebElement currLvl3Element = foldersLvl3.get(k);
                            try {
                                System.out.println(currLvl3Element.getText());
                                currIcon = getCurrLvl3FolderIcon(currLvl3Element).getAttribute("class");
                                UINavigation.scrollTo(currLvl3Element);
                            }
                            catch (NoSuchElementException | NullPointerException n){
                                continue;
                            }
                            if (icons.contains(currIcon)) {
                                dragAndDrop(currLvl3Element, dropLocation);
                                icons.remove(currIcon);
                                break folder_loop;
                            }
                        }
                    currInnerFolderCloser.click();
                    }
                    UINavigation.scrollTo(currFolder);
                    foldersCloser.get(i).click();
                }
                Utility.createAssignment();
                Utility.waitForNotVisible(getLoadingAssignButton());
        }
        try {
            WebElement popup = getCreateAssignmentPopup();
            Utility.waitForNotVisible(popup);
        }
        catch (NoSuchElementException n){}
        System.out.println("Assignments creation complete.");
        long end = System.nanoTime();
        Utility.nanoToReadableTime(start, end);
    }


    public static void selectAllAssignments() throws InterruptedException {
        long start = System.nanoTime();

        UINavigation.navToAssignment();

        Thread.sleep(1000);

        UINavigation.clickAddAssignments();

        ArrayList<String> copyOfIcons = CommonResources.getIcons();

        Thread.sleep(3000);
        System.out.println("Waiting for course content to load...");

        List<WebElement> folders = getFolders();
        List<WebElement> foldersCloser = getFoldersCloser();

        WebElement dropLocation = getDropLocation();

        ArrayList<String> assignments = new ArrayList<>();
        folder_loop:
        for (int i = 1; i < folders.size() - 1; i++) {
            Thread.sleep(500);
            WebElement currFolder = folders.get(i);
            UINavigation.scrollTo(currFolder);
            String folderTitle = currFolder.getText();
            System.out.println(String.format("Expanding folder \"%s\".", folderTitle));
            currFolder.click();
            List<WebElement> foldersLvl2 = getFoldersLvl2(currFolder);
            List<WebElement> foldersLvl2Closer = getFoldersLvl2Closer(currFolder);
            System.out.println(String.format("There are %s folders within \"%s\"", foldersLvl2.size(), folderTitle));

            for (int j = 0; j < foldersLvl2.size(); j++) {
                Thread.sleep(500);
                WebElement currInnerFolder = foldersLvl2.get(j);
                WebElement currInnerFolderCloser = foldersLvl2Closer.get(j);
                String currInnerFolderTitle = foldersLvl2.get(j).getText();

                System.out.println(String.format("Expanding folder in folder \"%s\": \"%s\".", folderTitle, currInnerFolder.getText()));

                UINavigation.scrollTo(currInnerFolder);
                currInnerFolder.click();

                List<WebElement> foldersLvl3 = getFoldersLvl3(currInnerFolder);
                System.out.println(String.format("There are %s items in inner folder \"%s\"", foldersLvl3.size(), currInnerFolderTitle));

                for (int k = 0; k < foldersLvl3.size(); k++) {
                    if (!copyOfIcons.isEmpty()) {
                        String currIcon;
                        WebElement currLvl3FolderElement = foldersLvl3.get(k);
                        try {
                            currIcon = getCurrLvl3FolderIcon(currLvl3FolderElement).getAttribute("class");
                            UINavigation.scrollTo(currLvl3FolderElement);
                        } catch (NoSuchElementException | NullPointerException n) {
                            continue;
                        }
                        if (copyOfIcons.contains(currIcon)) {
                            dragAndDrop(currLvl3FolderElement, dropLocation);
                            assignments.add(currLvl3FolderElement.getText());
                            copyOfIcons.remove(currIcon);
                        }
                    } else {
                        break folder_loop;
                    }
                }
                currInnerFolderCloser.click();
            }

            UINavigation.scrollTo(currFolder);
            foldersCloser.get(i).click();
        }
        Utility.createAssignment();
        try {
            WebElement popup = getCreateAssignmentPopup();
            Utility.waitForNotVisible(popup);
        }
        catch (NoSuchElementException n){
            ArrayList<String> allAssignments = Utility.getAssignments(CommonResources.cssSelectorAssignmentTitle);
            assignments.removeAll(allAssignments);
            if (assignments.isEmpty()) {
                System.out.println("All assignments have been added successfully!");
            } else {
                for (Iterator<String> i = assignments.iterator(); i.hasNext(); ) {
                    System.out.println(String.format("%s was not added successfully.", i.next()));
                }
            }
        }
        ArrayList<String> allAssignments = Utility.getAssignments(CommonResources.cssSelectorAssignmentTitle);
        assignments.removeAll(allAssignments);
        if (assignments.isEmpty()) {
            System.out.println("All assignments have been added successfully!");
        } else {
            for (Iterator<String> i = assignments.iterator(); i.hasNext(); ) {
                System.out.println(String.format("%s was not added successfully.", i.next()));
            }
        }
        long end = System.nanoTime();
        Utility.nanoToReadableTime(start, end);
    }

    public static void confirmAssignments() throws InterruptedException {
        long start = System.nanoTime();

        UINavigation.navToAssignment();

        Thread.sleep(1000);

        UINavigation.clickAddAssignments();

        Utility.simpleSelectAssignment();

        Utility.createAssignment();

        System.out.println("Now confirming assigning in \"qastudent\"");

        System.out.println("Now logging out...");
        Thread.sleep(2000);

        Utility.logout();

        System.out.println("Now logging into \"qastudent\"");
        Utility.login(CommonResources.usernameStudent, CommonResources.passwordStudent, CommonResources.browserDriver);

        UINavigation.accessCourse(CommonResources.courseForAssignmentTest);
        UINavigation.clickSkip();

        UINavigation.accessAssignments();
        UINavigation.clickSkip();

        ArrayList<String> currAssignments = Utility.getAssignments(CommonResources.cssSelectorStudentAssignmentTitle);

        if (currAssignments.isEmpty()){
            System.out.println(String.format("There are no assignments assigned to \"%s\"",CommonResources.usernameStudent));
        }
        else{
            System.out.println(String.format("There are %s assignments that have been assigned.", currAssignments.size()));
            currAssignments.forEach((assignments)->
                    System.out.println(String.format("%s has been successfully assigned.",assignments)));
        }
        Utility.logout();

        Utility.login(CommonResources.usernameTeacher, CommonResources.passwordTeacher, CommonResources.browserDriver);

        long end = System.nanoTime();
        Utility.nanoToReadableTime(start, end);
    }

    public static void deleteAssignments() throws InterruptedException{
        long start = System.nanoTime();

        UINavigation.navToAssignment();

        Thread.sleep(1000);

        UINavigation.clickAddAssignments();

        Utility.simpleSelectAssignment();

        Utility.createAssignment();

        System.out.println("Now deleting assignments...");

        Thread.sleep(2000);

        WebDriver studentBrowser = Utility.startBrowser(CommonResources.chromeDriver, CommonResources.pathChromeDriver);
        System.out.println("Opened browser for student...");

        Utility.login(CommonResources.usernameStudent, CommonResources.passwordStudent, studentBrowser);
        Thread.sleep(10000);
        UINavigation.clickSkip(studentBrowser);

        UINavigation.accessCourse(CommonResources.courseForAssignmentTest, studentBrowser);
        UINavigation.clickSkip(studentBrowser);

        UINavigation.accessAssignments(studentBrowser);
        UINavigation.clickSkip(studentBrowser);

        ArrayList<String> studentAssignments = Utility.getAssignments(
                CommonResources.cssSelectorStudentAssignmentTitle, studentBrowser);

        if(studentAssignments.isEmpty()){
            System.out.println("There are no assignments assigned to student." +
                    "  Closing student browser and navigating back to dashboard now.");
            studentBrowser.quit();
            return;
        }

        System.out.println("Student currently has assignments: ");
        studentAssignments.forEach((assignment)-> System.out.println(assignment));
        System.out.println();

        List<WebElement> existingAssignments = getExistingAssignments();

        if (existingAssignments.size() > 0) {
            ArrayList<String> teacherAssignments = Utility.getAssignments(
                    CommonResources.cssSelectorAssignmentTitle);

            System.out.println("Teacher currently has assignments:");
            teacherAssignments.forEach((assignments)-> System.out.println(assignments));
            System.out.println();

            UINavigation.clickTrashcan();

            UINavigation.clickCheckAll();

            UINavigation.clickRemoveSelected();

            Thread.sleep(2000);

            UINavigation.clickRemoveConfirm();

            System.out.println("Assignments have been deleted.");

            Thread.sleep(2000);

            studentBrowser.navigate().refresh();

            Thread.sleep(5000);

            studentAssignments = Utility.getAssignments(CommonResources.cssSelectorStudentAssignmentTitle, studentBrowser);

            if(studentAssignments == null){
                System.out.println("Assignments have been successfully removed from the student account.");
                studentBrowser.quit();
                return;
            }

            studentAssignments.forEach((assignments)-> System.out.println(String.format(
                    "%s was not deleted.", assignments)));
        }
        else {
            System.out.println("No assignments created yet.");
        }
        studentBrowser.close();
        long end = System.nanoTime();
        Utility.nanoToReadableTime(start, end);
    }

    private static List<WebElement> getExistingAssignments() {
        try {
            return  CommonResources.browserDriver.findElements(By.cssSelector(
                    CommonResources.cssSelectorAssignments));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    public static void editAssignments() throws InterruptedException{
        long start = System.nanoTime();

        UINavigation.navToAssignment();

        Thread.sleep(1000);

        UINavigation.clickAddAssignments();

        Utility.simpleSelectAssignment();

        Utility.createAssignmentSelectedStudents();

        System.out.println("Now editing assignments...");

        Thread.sleep(2000);
        UINavigation.clickCompletedAssigned();
        Thread.sleep(1000);

        ArrayList<String> allStudents = getStudents();

        if(allStudents.isEmpty()){
            System.out.println("There are no students assigned to this assignment.");
            return;
        }

        System.out.println(String.format("There are %s student(s) that have been assigned to this assignment:",
                allStudents.size()));
        allStudents.forEach((student -> System.out.println(student)));
        Thread.sleep(1000);

        UINavigation.clickClose();
        Thread.sleep(1000);

        UINavigation.clickEdit();
        Thread.sleep(1000);

        UINavigation.clickStudentCheckBox();
        Thread.sleep(1000);

        UINavigation.clickEditNextStep();
        Thread.sleep(1000);

        UINavigation.clickSave();
        Thread.sleep(3000);

        UINavigation.clickCompletedAssigned();
        Thread.sleep(1000);

        allStudents = getStudents();

        System.out.println(String.format("There are now %s student(s) that have been assigned to this assignment:",
                allStudents.size()));
        allStudents.forEach((student -> System.out.println(student)));

        UINavigation.clickClose();

        Utility.confirmAssignmentSelectedStudents();
        long end = System.nanoTime();
        Utility.nanoToReadableTime(start, end);
    }

    public static void archiveAssignment() throws InterruptedException {
        long start = System.nanoTime();

        UINavigation.navToAssignment();

        Thread.sleep(1000);

        UINavigation.clickAddAssignments();

        Utility.simpleSelectAssignment();

        Utility.createAssignment();

        System.out.println("Now archiving assignment...");

        UINavigation.clickArchive();
        Thread.sleep(1000);

        UINavigation.clickArchiveYes();
        Thread.sleep(1000);

        confirmMessageBox();
        Thread.sleep(1000);

        UINavigation.clickShowArchived();

        WebElement loaderIcon = getLoaderIcon();
        Utility.waitForNotVisible(loaderIcon);

        ArrayList<String> allArchiveAssignments = getArchivedAssignments();

        if (allArchiveAssignments.isEmpty()){
            System.out.println("There are no archived assignments.");
            return;
        }
        System.out.println(String.format("There are %s assignment(s) that have been archived named:",
                allArchiveAssignments.size()));
        allArchiveAssignments.forEach((assignment -> System.out.println(assignment)));
        long end = System.nanoTime();
        Utility.nanoToReadableTime(start, end);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static WebElement getLoaderIcon() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorLoaderIcon);
    }
    private static WebElement getCreateAssignmentPopup() throws InterruptedException{
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorCreateAssignmentPopup);
    }

    private static void dragAndDrop(WebElement object, WebElement to) {
        Actions actions = new Actions(CommonResources.browserDriver);
        actions.dragAndDrop(object, to).build().perform();
    }

    private static WebElement getCurrLvl3FolderIcon(WebElement currLvl3Folder) {
        try {
            return currLvl3Folder.findElement(By.cssSelector(CommonResources.cssSelectorIcons));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static List<WebElement> getFoldersLvl3(WebElement currInnerFolder) {
        try {
            return currInnerFolder.findElements(By.cssSelector(CommonResources.cssSelectorFoldersLvl3));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static List<WebElement> getFoldersLvl2Closer(WebElement currFolder) {
        try {
            return currFolder.findElements(By.cssSelector(CommonResources.cssSelectorFoldersLvl2Closer));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static List<WebElement> getFoldersLvl2(WebElement currFolder) {
        try {
            return currFolder.findElements(By.cssSelector(CommonResources.cssSelectorFoldersLvl2));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }
    private static WebElement getDropLocation() {
        try {
            return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorDropContainer));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static List<WebElement> getFoldersCloser() {
        try {
            return CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorFoldersCloser));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static List<WebElement> getFolders() throws InterruptedException{
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorFolders);
    }

    private static WebElement getLoadingAssignButton() {
        try{
            return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorAssignLoading));
        }
        catch (NoSuchElementException n){
            return null;
        }
    }

    private static ArrayList<String> getStudents() throws InterruptedException {
        List<WebElement> students = Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorAllAssigned);
        if (students.isEmpty()){
            return new ArrayList<>();
        }
        ArrayList<String> studentNames = new ArrayList<>();
        students.forEach((student -> {
            String currStudentName = student.getText().split("\n")[1];
            studentNames.add(currStudentName);
        }));
        return studentNames;
    }

    private static ArrayList<String> getArchivedAssignments() {
        List<WebElement> archivedAssignments = CommonResources.browserDriver.findElements(
                By.cssSelector(CommonResources.cssSelectorArchivedAssignments)
        );
        if (archivedAssignments.isEmpty()){
            return new ArrayList<>();
        }
        ArrayList<String> archivedAssignmentNames = new ArrayList<>();

        archivedAssignments.forEach((assignment -> {
            String currAssignmentName = assignment.getText().split("\n")[0];
            System.out.println(currAssignmentName);
            archivedAssignmentNames.add(currAssignmentName);
        }));
        return archivedAssignmentNames;
    }

    private static void confirmMessageBox() {
        List<WebElement> messageBox = CommonResources.browserDriver.findElements(
                By.cssSelector(CommonResources.cssSelectorArchiveMessageBox));

        if (messageBox.isEmpty()){
            System.out.println("Message Box did not appear after archiving; Please contact administrator.");
            return;
        }

        String messageBoxText = messageBox.get(0).getText();

        if(Objects.equals(messageBoxText, CommonResources.archiveSuccessMessage)){
            System.out.println(String.format(
                    "Message Box appeared with text: %s", CommonResources.archiveSuccessMessage));
        }

        else {
            System.out.println(String.format(
                    "MessageBox appeared, but with different success text: %s", messageBoxText));
        }
    }
}