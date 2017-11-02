import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import Tests.AssignmentTests;
import Tests.CreateCourse;
import Tests.FlexTextTests;

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

        UINavigation.clickSkip();
        Thread.sleep(10000);
        UINavigation.clickSkip();
        Thread.sleep(1000);
        UINavigation.clickSkip();

        Scanner scanChoice = new Scanner(System.in);

        while(true) {
            System.out.println("Which test would you like to run?:\n" +
                    "\"1\" - Create Courses\n" +
                    "\"2\" - Check Book Selection Count in New Course Page\n" +
                    "\"3\" - Assignments\n" +
                    "\"4\" - FlexText\n" +
                    "\"5\" - Exit program.");
            try {
                choiceEntry = scanChoice.nextInt();

                if(choiceEntry>5){
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
                        System.out.println("Select assignment functionality:\n" +
                                "\"1\" - Create all assignments\n" +
                                "\"2\" - Create each assignment individually\n" +
                                "\"3\" - Confirm assignments for students\n" +
                                "\"4\" - Delete assignments\n" +
                                "\"5\" - Edit/Assign assignment to new student\n" +
                                "\"6\" - Archive assignment\n" +
                                "\"7\" - Back"
                        );
                        int assignmentChoiceEntry = assignmentChoice.nextInt();
                        if (assignmentChoiceEntry > 7) {
                            System.out.println("Please input a valid number");
                            return;
                        }

                        if (assignmentChoiceEntry == 1){
                            System.out.println("Testing selecting and creating all assignment functionality...");

                            UINavigation.navToAssignment();

                            Thread.sleep(1000);

                            UINavigation.clickAddAssignments();

                            AssignmentTests.selectAllAssignments();

                            Utility.createAssignment();

                            Utility.simpleDeleteAssignments();
                        }

                        if (assignmentChoiceEntry == 2) {
                            System.out.println("Testing selecting and creating each" +
                                    " assignment individually functionality...");

                            UINavigation.navToAssignment();

                            Thread.sleep(1000);

                            UINavigation.clickAddAssignments();

                            AssignmentTests.selectEachAssignments();

                            Utility.simpleDeleteAssignments();
                        }

                        if (assignmentChoiceEntry == 3){
                            System.out.println("Confirming that assignments are properly assigned to students...");

                            UINavigation.navToAssignment();

                            Thread.sleep(1000);

                            UINavigation.clickAddAssignments();

                            Utility.simpleSelectAssignment();

                            Utility.createAssignment();

                            AssignmentTests.confirmAssignments();

                            Utility.simpleDeleteAssignments();
                        }

                        if (assignmentChoiceEntry == 4) {
                            System.out.println("Testing deleting assignments...");

                            UINavigation.navToAssignment();

                            Thread.sleep(1000);

                            UINavigation.clickAddAssignments();

                            Utility.simpleSelectAssignment();

                            Utility.createAssignment();

                            AssignmentTests.deleteAssignments();
                        }
                        if(assignmentChoiceEntry == 5){
                            System.out.println("Testing editing assignments...");
                            UINavigation.navToAssignment();

                            Thread.sleep(1000);

                            UINavigation.clickAddAssignments();

                            Utility.simpleSelectAssignment();

                            Utility.createAssignmentSelectedStudents();

                            AssignmentTests.editAssignments();

                            Utility.confirmAssignmentSelectedStudents();

                            Utility.simpleDeleteAssignments();
                        }
                        if (assignmentChoiceEntry == 6) {
                            System.out.println("Testing archiving assignments...");

                            UINavigation.navToAssignment();

                            Thread.sleep(1000);

                            UINavigation.clickAddAssignments();

                            Utility.simpleSelectAssignment();

                            Utility.createAssignment();

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
                    Scanner flextextChoice = new Scanner(System.in);
                    System.out.println("Select a FlexText functionality:\n" +
                            "\"1\" - Jump to page");
                    int choice = flextextChoice.nextInt();

                    if (choice == 1){
                        FlexTextTests.checkJumpToPage();
                    }
                }
                if (choiceEntry == 5) {
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