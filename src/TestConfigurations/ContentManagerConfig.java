package TestConfigurations;

import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import Tests.ContentManagerTests;

public class ContentManagerConfig {
    public static void checkContentManagerIcons() throws InterruptedException{
        UINavigation.accessCourse("asd");
        UINavigation.clickSkip();
        ContentManagerTests.contentIcons();
    }

    public static void checkSubLinks() throws InterruptedException {
        UINavigation.accessCourse("asd");
        UINavigation.clickSkip();
        ContentManagerTests.viewAssignGradesAttemptLinks();
    }

    public static void checkAssigning() throws InterruptedException {
        UINavigation.accessCourse("asd");
        UINavigation.clickSkip();
        ContentManagerTests.assigning();
        UINavigation.accessAssignments();
        Utility.simpleDeleteAssignments();
    }

    public static void checkAllContentManagerTests() throws InterruptedException {
        checkContentManagerIcons();
        UINavigation.navToDash();

        checkSubLinks();
        UINavigation.navToDash();

        checkAssigning();
        UINavigation.navToDash();
        UINavigation.navToAssignment();
        Utility.simpleDeleteAssignments();
        UINavigation.navToDash();
    }
}
