package Tests;
import HelpersAndUtilities.CommonResources;
import Objects.Test;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import Objects.User;

import java.util.*;


public class AssignmentTests {
    private static String currCat = CommonResources.getAllCategories().get(2);

    public static void selectEachAssignments() throws InterruptedException{
        Test currTest = new Test(currCat,
                "Add Each Assignment", "", "");
        long start = System.nanoTime();
        UINavigation.navToAssignment();

        Thread.sleep(1000);

        System.out.println("Now selecting each assignment...");

        ArrayList<String> icons = CommonResources.getIconsCssClass();
        while(!icons.isEmpty()){
            UINavigation.clickAddAssignments();

            System.out.println("Waiting for course content to load...");

            List<WebElement> folders = getFolders();
            WebElement dropLocation = getDropLocation();

            folder_loop:
            for(int i = 1; i<folders.size(); i++){
                Thread.sleep(500);
                WebElement currFolder = folders.get(i);
                UINavigation.scrollTo(currFolder);

                String folderTitle = currFolder.getText();

                System.out.println(String.format("Expanding folder \"%s\".", folderTitle));

                currFolder.click();

                List<WebElement> foldersLvl2 = getFoldersLvl2();

                for(int j = 0; j<foldersLvl2.size(); j++){
                    WebElement currFoldersLvl2Closer = getFoldersLvl2Closer().get(j);
                    Thread.sleep(500);
                    WebElement currInnerFolder = foldersLvl2.get(j);

                    String folderLvl2Title = currInnerFolder.getText();

                    System.out.println(String.format("Expanding folder in folder \"%s\": \"%s\".", folderTitle, currInnerFolder.getText()));

                    UINavigation.scrollTo(currInnerFolder);
                    currInnerFolder.click();

                    List<WebElement> foldersLvl3 = getFoldersLvl3();
                    System.out.println(String.format("There are %s items in inner folder \"%s\"", foldersLvl3.size(),folderLvl2Title));
                    for (int k = 0; k<foldersLvl3.size(); k++){
                        String currIcon;
                        WebElement currLvl3Element = foldersLvl3.get(k);
                            try {
                                System.out.println(currLvl3Element.getText());
                                List<WebElement> allIcons = getCurrLvl3FolderIcon(currLvl3Element);
                                currIcon = getWsiIcon(allIcons);
                                UINavigation.scrollTo(currLvl3Element);
                            }
                            catch (NoSuchElementException | NullPointerException n){
                                continue;
                            }
                            if (icons.contains(currIcon)) {
                                UINavigation.dragAndDrop(currLvl3Element, dropLocation);
                                icons.remove(currIcon);
                                break folder_loop;
                            }
                    }
                    UINavigation.scrollTo(currFoldersLvl2Closer);
                    currFoldersLvl2Closer.click();
                }
                UINavigation.scrollTo(currFolder);
                List<WebElement> foldersCloser = getFoldersCloser();
                foldersCloser.get(i).click();
            }
            Utility.createAssignment();
        }
        System.out.println("Assignments creation complete.");
        long end = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(start, end);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(start, end);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }


    public static void selectAllAssignments() throws InterruptedException {
        Test currTest = new Test(currCat, "Assigning All Assignments", "", "");
        long start = System.nanoTime();

        UINavigation.navToAssignment();

        Thread.sleep(1000);

        UINavigation.clickAddAssignments();

        ArrayList<String> copyOfIcons = CommonResources.getIconsCssClass();

        List<WebElement> folders = getFolders();
        List<WebElement> foldersCloser = getFoldersCloser();

        WebElement dropLocation = getDropLocation();

        ArrayList<String> assignments = new ArrayList<>();
        folder_loop:
        for (int i = 1; i < folders.size() - 1; i++) {
            Thread.sleep(500);
            WebElement currFolder = folders.get(i);
            UINavigation.scrollTo(currFolder);
            currFolder.click();
            List<WebElement> foldersLvl2 = getFoldersLvl2();

            for (int j = 0; j < foldersLvl2.size(); j++) {
                Thread.sleep(500);
                WebElement currInnerFolder = foldersLvl2.get(j);

                UINavigation.scrollTo(currInnerFolder);
                currInnerFolder.click();

                List<WebElement> foldersLvl3 = getFoldersLvl3();


                for (int k = 0; k < foldersLvl3.size(); k++) {
                    if (!copyOfIcons.isEmpty()) {
                        String currIcon;
                        WebElement currLvl3FolderElement = foldersLvl3.get(k);
                        try {
                            System.out.println(currLvl3FolderElement.getText());
                            List<WebElement> allIcons = getCurrLvl3FolderIcon(currLvl3FolderElement);
                            currIcon = getWsiIcon(allIcons);
                            UINavigation.scrollTo(currLvl3FolderElement);
                        } catch (NoSuchElementException | NullPointerException n) {
                            continue;
                        }
                        if (copyOfIcons.contains(currIcon)) {
                            UINavigation.dragAndDrop(currLvl3FolderElement, dropLocation);
                            assignments.add(currLvl3FolderElement.getText());
                            copyOfIcons.remove(currIcon);
                        }
                    } else {
                        break folder_loop;
                    }
                }
                UINavigation.scrollTo(currInnerFolder);
                Thread.sleep(1000);
                currInnerFolder.click();
            }

            UINavigation.scrollTo(currFolder);
            currFolder.click();
        }
        Utility.createAssignment();
        try {
            WebElement popup = getCreateAssignmentPopup();
            Utility.waitForNotVisible(popup);
        }
        catch (NullPointerException n){
            ArrayList<String> allAssignments = Utility.getAssignments(CommonResources.cssSelectorAssignmentTitle);
            assignments.removeAll(allAssignments);
            if (assignments.isEmpty()) {
                System.out.println("All assignments have been added successfully!");
            } else {
                for (Iterator<String> i = assignments.iterator(); i.hasNext(); ) {
                    System.out.println(String.format("%s was not added successfully.", i.next()));
                    if(CommonResources.qaTestMode.equals("n")){
                        Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                        return;
                    }
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
            if(CommonResources.qaTestMode.equals("n")){
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                return;
            }
        }
        long end = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(start, end);
        }
        else if (CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(start, end);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    public static void confirmAssignments() throws InterruptedException {
        Test currTest = new Test(currCat, "Confirm Assignments", "", "");
        long start = System.nanoTime();

        UINavigation.navToAssignment();

        Thread.sleep(1000);

        UINavigation.clickAddAssignments();

        Utility.simpleSelectAssignment();

        Utility.createAssignment();

        System.out.println("Now confirming assigning in \"qastudent\"");

        System.out.println("Now logging out...");
        Thread.sleep(2000);



        System.out.println("Now logging into \"qastudent\"");
        User student = new User(CommonResources.usernameStudent, CommonResources.passwordStudent);
        Utility.switchUsers(student, CommonResources.browserDriver);
        UINavigation.clickSkip();

        UINavigation.accessCourse(CommonResources.courseForAssignmentTest);
        UINavigation.clickSkip();

        UINavigation.accessAssignments();
        UINavigation.clickSkip();

        ArrayList<String> currAssignments = Utility.getAssignments(CommonResources.cssSelectorStudentAssignmentTitle);

        if (currAssignments.isEmpty()){
            System.out.println(String.format("There are no assignments assigned to \"%s\"",CommonResources.usernameStudent));
            if(CommonResources.qaTestMode.equals("n")){
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
            return;
        }
        else{
            System.out.println(String.format("There are %s assignments that have been assigned.", currAssignments.size()));
            currAssignments.forEach((assignments)->
                    System.out.println(String.format("%s has been successfully assigned.",assignments)));
        }


        User teacher = new User(CommonResources.usernameTeacher, CommonResources.passwordTeacher);
        Utility.switchUsers(teacher, CommonResources.browserDriver);

        long end = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(start, end);
        }
        else if(CommonResources.qaTestMode.equals("n")) {
            String time = Utility.readableTime(start, end);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    public static void deleteAssignments() throws InterruptedException {
        Test currTest = new Test(currCat, "Delete Assignments", "", "");

        long start = System.nanoTime();

        UINavigation.navToAssignment();

        Thread.sleep(1000);

        UINavigation.clickAddAssignments();

        Utility.simpleSelectAssignment();

        Utility.createAssignment();

        System.out.println("Now deleting assignments...");

        Thread.sleep(2000);

        WebDriver studentBrowser = Utility.startBrowser(CommonResources.chromeDriver, CommonResources.pathChromeDriver, CommonResources.siteChoiceEntry);
        System.out.println("Opened browser for student...");

        User student = new User(CommonResources.usernameStudent, CommonResources.passwordStudent);
        Utility.login(student, studentBrowser);
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
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        System.out.println("User currently has assignments: ");
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

            if(studentAssignments.size() == 0){
                System.out.println("Assignments have been successfully removed from the student account.");
                studentBrowser.quit();
            }
            else {
                System.out.println("Assignments are not deleted.");
                if(CommonResources.qaTestMode.equals("n")){
                    Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                }
                studentBrowser.quit();
                return;
            }
        }
        else {
            System.out.println("No assignments created yet.");
            if(CommonResources.qaTestMode.equals("n")){
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
            studentBrowser.quit();
            return;
        }
        long end = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(start, end);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(start, end);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
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
        Test currTest = new Test(currCat, "Edit Assignment", "", "");
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
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
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

        boolean passed = Utility.confirmAssignmentSelectedStudents(currTest);
        long end = System.nanoTime();
        if(!passed) {
            System.out.println("There are no assignments assigned.");
            return;
        }
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(start, end);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(start, end);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    public static void archiveAssignment() throws InterruptedException {
        Test currTest = new Test (currCat,"Archive Assignments", "", "");

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
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }
        System.out.println(String.format("There are %s assignment(s) that have been archived named:",
                allArchiveAssignments.size()));
        allArchiveAssignments.forEach((assignment -> System.out.println(assignment)));
        long end = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(start, end);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(start, end);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static String getWsiIcon(List<WebElement> all) {
        for(int i = 0;i < all.size(); i++){
            String currIconClass = all.get(i).getAttribute("class");
            if(currIconClass.contains("wsi")){
                return currIconClass;
            }
        }
        return "";
    }

    private static WebElement getLoaderIcon() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorLoaderIcon);
    }
    private static WebElement getCreateAssignmentPopup() throws InterruptedException{
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorCreateAssignmentPopup);
    }

    private static List<WebElement> getCurrLvl3FolderIcon(WebElement currLvl3Folder) {
        try {
            return currLvl3Folder.findElements(By.cssSelector(CommonResources.cssSelectorIcons));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static List<WebElement> getFoldersLvl3() {
        return CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorFoldersLvl3));
    }

    private static List<WebElement> getFoldersLvl2Closer() {
        return CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorFoldersLvl2Closer));
    }

    private static List<WebElement> getFoldersLvl2Opener() {
        return CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorFoldersLvl2Opener));
    }
    private static List<WebElement> getFoldersLvl2() throws InterruptedException {
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorFoldersLvl2);
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

    private static List<WebElement> getFoldersOpener() throws InterruptedException {
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorFoldersOpener);
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

    private static void confirmBrowserPrompt() {

    }
}