package TestConfigurations;

import HelpersAndUtilities.UINavigation;
import Tests.CreateCourseTests;

public class CreateCourseConfig {
    public static void checkCreateCourse() throws InterruptedException{
        UINavigation.clickSkip();
        CreateCourseTests.createCourse();
    }

    public static void checkAllCreateCourseTests() throws InterruptedException {
        checkCreateCourse();
        UINavigation.navToDash();
    }
}
