package TestConfigurations;

import HelpersAndUtilities.UINavigation;
import Tests.BookCountTests;

public class BookCountConfig {
    public static void checkBookCount() throws InterruptedException {
        BookCountTests.checkBookCount();
    }

    public static void checkAllBookCountTests() throws InterruptedException {
        BookCountTests.checkBookCount();
        UINavigation.navToDash();
    }
}
