import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import Objects.User;
import TestConfigurations.*;
import Tests.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class qaTestDebug {
    public static void run() throws InterruptedException, IOException{
        Scanner scanChoice = new Scanner(System.in);
        while(true) {
            System.out.println("Which test would you like to run?:");
            int num = 0;
            for(String test: CommonResources.getAllTests()){
                System.out.println(String.format("\"%s\" - %s", num+1, test));
                num++;
            }
            try {
                int choiceEntry = scanChoice.nextInt();

                if(choiceEntry>CommonResources.getAllTests().length){
                    System.out.println("Please input a valid number.");
                    continue;
                }

                else if (choiceEntry == 1){
                    CreateCourseConfig.checkCreateCourse();
                }

                else if (choiceEntry == 2){
                    BookCountConfig.checkBookCount();
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

                            AssignmentsConfig.checkSelectAllAssignments();
                        }

                        if (assignmentChoiceEntry == 2) {
                            System.out.println("Testing selecting and creating each" +
                                    " assignment individually functionality...");

                            AssignmentsConfig.checkSelectEachAssignments();
                        }

                        if (assignmentChoiceEntry == 3){
                            System.out.println("Confirming that assignments are properly assigned to students...");

                            AssignmentsConfig.checkConfirmAssignments();
                        }

                        if (assignmentChoiceEntry == 4) {
                            System.out.println("Testing deleting assignments...");

                            AssignmentsConfig.checkDeleteAssignments();
                        }
                        if(assignmentChoiceEntry == 5){
                            System.out.println("Testing editing assignments...");

                            AssignmentsConfig.checkEditAssignments();
                        }
                        if (assignmentChoiceEntry == 6) {
                            System.out.println("Testing archiving assignments...");

                            AssignmentsConfig.checkArchiveAssignments();
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
                        FlexTextConfig.checkJumpToPage();
                    }

                    if (choice == 2){
                        FlexTextConfig.checkExplorerLinks();
                    }

                    if(choice == 3){
                        FlexTextConfig.checkSearch();
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
                        ContentManagerConfig.checkContentManagerIcons();
                    }
                    if(contentChoice == 2) {
                        ContentManagerConfig.checkSubLinks();
                    }
                    if(contentChoice == 3) {
                        ContentManagerConfig.checkAssigning();
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

                    User student = new User(CommonResources.usernameStudent, CommonResources.passwordStudent);

                    Utility.switchUsers(student, CommonResources.browserDriver);
                    if(portfolioChoice == 1) {
                        LanguagePortfolioConfig.checkViewButton();
                    }
                    if(portfolioChoice == 2) {
                        LanguagePortfolioConfig.checkAddQuizAttemptButton();
                    }
                    if(portfolioChoice == 3) {
                        LanguagePortfolioConfig.checkAddRecordingButton();
                    }
                    if(portfolioChoice == 4) {
                        LanguagePortfolioConfig.checkAddVideoRecordingButton();
                    }
                    if(portfolioChoice == 5) {
                        LanguagePortfolioConfig.checkChangeProgressLink();
                    }
                    if(portfolioChoice == 6) {
                        LanguagePortfolioConfig.checkAddingComment();
                    }
                    if(portfolioChoice == 7) {
                        LanguagePortfolioConfig.checkFileUpload();
                    }

                    User teacher = new User(CommonResources.usernameTeacher, CommonResources.passwordTeacher);
                    Utility.switchUsers(teacher, CommonResources.browserDriver);
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
                        ProfileSettingsConfig.checkEditFirstName();
                    }

                    if(profileSettingsChoice == 2) {
                        ProfileSettingsConfig.checkEditLastName();
                    }

                    if(profileSettingsChoice == 3) {
                        ProfileSettingsConfig.checkEditPassword();
                    }

                    if(profileSettingsChoice == 4) {
                        ProfileSettingsConfig.checkEditEmail();
                    }

                    if(profileSettingsChoice == 5) {
                        ProfileSettingsConfig.checkEditTimezone();
                    }

                    if(profileSettingsChoice == 6) {
                        ProfileSettingsConfig.checkEditImage();
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
                        CourseSettingsConfig.checkAssignmentPenalty();
                    }

                    if(courseSettingsChoice == 2) {
                        CourseSettingsConfig.checkQuizHidePassFailStatus();
                    }

                    if(courseSettingsChoice == 3) {
                        CourseSettingsConfig.checkQuizThreshold();
                    }

                    if(courseSettingsChoice == 4) {
                        CourseSettingsConfig.checkQuizMaxAttempts();
                    }

                    if(courseSettingsChoice == 5) {
                        CourseSettingsConfig.checkShowHideContent();
                    }
                }
                if(choiceEntry == 9){
                    scanChoice = new Scanner(System.in);
                    num = 0;
                    for(String test: CommonResources.getAllQuizTests()){
                        System.out.println(String.format("\"%s\" - %s", num+1, test));
                        num++;
                    }
                    int quizTestChoice = scanChoice.nextInt();
                    switch (quizTestChoice){
                        case 1:
                            QuizConfig.checkAllQuizTests();
                            break;
                    }

                }
                if(choiceEntry == 10) {
                    scanChoice = new Scanner(System.in);
                    num = 0;
                    for(String test: CommonResources.getAllFlashcardsTests()){
                        System.out.println(String.format("\"%s\" - %s", num+1, test));
                        num++;
                    }
                    int fcTestChoice = scanChoice.nextInt();
                    switch (fcTestChoice){
                        case 1: FlashcardConfig.checkArrowKeysNav();
                        break;

                        case 2: FlashcardConfig.checkArrowKeysFlip();
                        break;

                        case 3: FlashcardConfig.checkClickFlip();
                        break;

                        case 4: FlashcardConfig.checkArrowUINav();
                        break;

                        case 5: FlashcardConfig.checkSlider();
                        break;

                        case 6: FlashcardConfig.checkLearned();
                        break;
                    }
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
