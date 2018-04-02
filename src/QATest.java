import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import Tests.*;

import java.io.IOException;
import java.util.*;

public class QATest {
    public static void main(String[] args) throws InterruptedException, IOException{
        Scanner scanBrowserChoice = new Scanner(System.in);
        int choiceEntry = 0;
        String browser = "";
        String driver = "";
        do {
            try {
                System.out.println("Which browser driver would you like to use?:\n" +
                        "\"1\" - Google Chrome\n" +
                        "\"2\" - Firefox\n" +
                        "\"3\" - Quit the program");
                choiceEntry = scanBrowserChoice.nextInt();
            }

            catch (InputMismatchException i) {
                System.out.println("Please input a number.");
                continue;
            }

            if(choiceEntry > 3 || choiceEntry < 0){
                System.out.println("Please input a valid number.");
            }

            if(choiceEntry == 1){
                browser = CommonResources.pathChromeDriver;
                driver = CommonResources.chromeDriver;
            }

            if(choiceEntry == 2){
                browser = CommonResources.pathFFDriver;
                driver = CommonResources.geckoDriver;
            }

            if(choiceEntry == 3){
                System.out.println("Exiting program.");
                System.exit(0);
            }
        }while(choiceEntry > 4 || choiceEntry < 0);
        CommonResources.browserDriver = Utility.startBrowser(driver, browser);
        Utility.login(CommonResources.usernameTeacher, CommonResources.passwordTeacher, CommonResources.browserDriver);


        Thread.sleep(10000);
        UINavigation.clickSkip();

        Scanner scanChoice = new Scanner(System.in);

        while(true) {
            System.out.println("Which test would you like to run?:");
            int num = 0;
            for(String test: CommonResources.getAllTests()){
                System.out.println(String.format("\"%s\" - %s", num+1, test));
                num++;
            }
            try {
                choiceEntry = scanChoice.nextInt();

                if(choiceEntry>CommonResources.getAllTests().length){
                    System.out.println("Please input a valid number.");
                    continue;
                }

                else if (choiceEntry == 1){
                    CreateCourse.createCourse();
                }

                else if (choiceEntry == 2){
                   Utility.courseCount();
                }

                else if (choiceEntry == 3) {
                    try {
                        Scanner assignmentChoice = new Scanner(System.in);
                        System.out.println("Select assignment functionality:");
                        num = 0;
                        for(String test: CommonResources.getAllAssignmentTests()){
                            System.out.println(String.format("\"%s\" - %s", num+1, test));
                            num++;
                        }
                        int assignmentChoiceEntry = assignmentChoice.nextInt();
                        if (assignmentChoiceEntry > CommonResources.getAllAssignmentTests().length) {
                            System.out.println("Please input a valid number");
                            return;
                        }

                        if (assignmentChoiceEntry == 1){
                            System.out.println("Testing selecting and creating all assignment functionality...");

                            AssignmentTests.selectAllAssignments();

                            Utility.simpleDeleteAssignments();
                        }

                        if (assignmentChoiceEntry == 2) {
                            System.out.println("Testing selecting and creating each" +
                                    " assignment individually functionality...");

                            AssignmentTests.selectEachAssignments();

                            Utility.simpleDeleteAssignments();
                        }

                        if (assignmentChoiceEntry == 3){
                            System.out.println("Confirming that assignments are properly assigned to students...");

                            AssignmentTests.confirmAssignments();

                            UINavigation.navToAssignment();

                            Utility.simpleDeleteAssignments();
                        }

                        if (assignmentChoiceEntry == 4) {
                            System.out.println("Testing deleting assignments...");

                            AssignmentTests.deleteAssignments();
                        }
                        if(assignmentChoiceEntry == 5){
                            System.out.println("Testing editing assignments...");
                            AssignmentTests.editAssignments();
                            Utility.simpleDeleteAssignments();
                        }
                        if (assignmentChoiceEntry == 6) {
                            System.out.println("Testing archiving assignments...");

                            AssignmentTests.archiveAssignment();

                            Utility.simpleDeleteAssignments();
                        }
                        if (assignmentChoiceEntry == 7) {
                            continue;
                        }
                    }

                    catch (InputMismatchException i) {
                        System.out.println("Please input a number.");
                    }
                }

                if (choiceEntry == 4) {
                    Scanner flexTextChoice = new Scanner(System.in);
                    System.out.println("Select a FlexText functionality:");

                    num = 0;
                    for(String test: CommonResources.getAllFlexTextTests()){
                        System.out.println(String.format("\"%s\" - %s", num+1, test));
                        num++;
                    }

                    int choice = flexTextChoice.nextInt();

                    if (choice == 1){
                        FlexTextTests.checkJumpToPage();
                    }

                    if (choice == 2){
                        FlexTextTests.checkExplorerLinks();
                    }

                    if(choice == 3){
                        FlexTextTests.search();
                    }

                }
                if(choiceEntry == 5){
                    scanChoice = new Scanner(System.in);
                    num = 0;
                    for(String test: CommonResources.getAllContentTests()){
                        System.out.println(String.format("\"%s\" - %s", num+1, test));
                        num++;
                    }
                    int contentChoice = scanChoice.nextInt();
                    if(contentChoice == 1) {
                        UINavigation.accessCourse("asd");
                        UINavigation.clickSkip();
                        ContentManagerTests.checkContentIcons();
                    }
                    if(contentChoice == 2) {
                        UINavigation.accessCourse("asd");
                        UINavigation.clickSkip();
                        ContentManagerTests.checkViewAssignGradesAttemptLinks();
                    }
                    if(contentChoice == 3) {
                        UINavigation.accessCourse("asd");
                        UINavigation.clickSkip();
                        ContentManagerTests.checkAssigning();
                    }
                }
                if(choiceEntry == 6){

                    scanChoice = new Scanner(System.in);
                    num = 0;
                    for(String test: CommonResources.getAllLanguagePortfolioTests()){
                        System.out.println(String.format("\"%s\" - %s", num+1, test));
                        num++;
                    }
                    int portfolioChoice = scanChoice.nextInt();
                    Utility.logout();
                    Utility.login(CommonResources.usernameStudent,
                            CommonResources.passwordStudent,
                            CommonResources.browserDriver);
                    if(portfolioChoice == 1) {
                        LanguagePortfolio.checkViewButton();
                    }
                    if(portfolioChoice == 2) {
                        LanguagePortfolio.checkAddQuizAttemptButton();
                    }
                    if(portfolioChoice == 3) {
                        LanguagePortfolio.checkAddRecordingButton();
                    }
                    if(portfolioChoice == 4) {
                        LanguagePortfolio.checkAddVideoRecordingButton();
                    }
                    if(portfolioChoice == 5) {
                        LanguagePortfolio.checkChangeProgressLink();
                    }
                    if(portfolioChoice == 6) {
                        LanguagePortfolio.checkAddingComment();
                    }
                    if(portfolioChoice == 7) {
                        LanguagePortfolio.checkFileUpload();
                    }
                    Utility.logout();
                    Utility.login(CommonResources.usernameTeacher,
                            CommonResources.passwordTeacher,
                            CommonResources.browserDriver);
                }

                if (choiceEntry == 7) {
                    scanChoice = new Scanner(System.in);
                    num = 0;
                    for(String test: CommonResources.getAllProfileSettingsTests()){
                        System.out.println(String.format("\"%s\" - %s", num+1, test));
                        num++;
                    }
                    int profileSettingsChoice = scanChoice.nextInt();

                    if(profileSettingsChoice == 1) {
                        ProfileSettingsTests.editFirstName();
                    }

                    if(profileSettingsChoice == 2) {
                        ProfileSettingsTests.editLastName();
                    }

                    if(profileSettingsChoice == 3) {
                        ProfileSettingsTests.editPassword();
                    }

                    if(profileSettingsChoice == 4) {
                        ProfileSettingsTests.editEmail();
                    }

                    if(profileSettingsChoice == 5) {
                        ProfileSettingsTests.editTimezone();
                    }

                    if(profileSettingsChoice == 6) {
                        ProfileSettingsTests.editImage();
                    }
                }
                if (choiceEntry == 8){
                    scanChoice = new Scanner(System.in);
                    num = 0;
                    for(String test: CommonResources.getAllCourseSettingsTest()){
                        System.out.println(String.format("\"%s\" - %s", num+1, test));
                        num++;
                    }
                    int courseSettingsChoice = scanChoice.nextInt();

                    if (courseSettingsChoice == 1){
                        CourseSettings.assignmentPenalty();
                    }

                    if(courseSettingsChoice == 2) {
                        CourseSettings.quizHidePassFailStatus();
                    }

                    if(courseSettingsChoice == 3) {
                        CourseSettings.quizThreshold();
                    }

                    if(courseSettingsChoice == 4) {
                        CourseSettings.quizMaxAttempts();
                    }

                    if(courseSettingsChoice == 5) {
                        CourseSettings.showHideContent();
                    }
                }
                if(choiceEntry == 9){
                    QuizTests.quizUICheck();
                }
                if (choiceEntry == CommonResources.getAllTests().length) {
                    System.out.println("Now exiting.");
                    CommonResources.browserDriver.quit();
                    System.exit(0);
                }
                UINavigation.navToDash();
                Thread.sleep(5000);
            }

            catch (InputMismatchException i) {
                System.out.println("Please input a number.");
            }
        }
    }
}