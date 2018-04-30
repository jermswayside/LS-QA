package TestConfigurations;

import HelpersAndUtilities.UINavigation;
import Tests.CourseSettingsTests;

public class CourseSettingsConfig {
    public static void checkAllCourseSettingsTests() throws InterruptedException {
        checkAssignmentPenalty();
        UINavigation.navToDash();
        UINavigation.clickSkip();

        checkQuizHidePassFailStatus();
        UINavigation.navToDash();
        UINavigation.clickSkip();

        checkQuizThreshold();
        UINavigation.navToDash();
        UINavigation.clickSkip();

        checkQuizMaxAttempts();
        UINavigation.navToDash();
        UINavigation.clickSkip();

        checkShowHideContent();
        UINavigation.navToDash();
        UINavigation.clickSkip();
    }

    public static void checkAssignmentPenalty() throws InterruptedException {
        CourseSettingsTests.assignmentPenalty();
    }

    public static void checkQuizHidePassFailStatus() throws InterruptedException{
        CourseSettingsTests.quizHidePassFailStatus();
    }

    public static void checkQuizThreshold() throws InterruptedException {
        CourseSettingsTests.quizThreshold();
    }

    public static void checkQuizMaxAttempts() throws InterruptedException {
        CourseSettingsTests.quizMaxAttempts();
    }

    public static void checkShowHideContent() throws InterruptedException {
        CourseSettingsTests.showHideContent();
    }
}
