package TestConfigurations;

import HelpersAndUtilities.UINavigation;
import Tests.FlexTextTests;

import java.io.IOException;

public class FlexTextConfig {
    public static void checkJumpToPage() throws InterruptedException, IOException {
        FlexTextTests.jumpToPage();
    }

    public static void checkExplorerLinks() throws InterruptedException, IOException {
        FlexTextTests.explorerLinks();
    }

    public static void checkSearch() throws InterruptedException, IOException {
        FlexTextTests.search();
    }

    public static void checkAllFlexTextTests() throws InterruptedException, IOException {
        checkJumpToPage();
        UINavigation.navToDash();

        checkExplorerLinks();
        UINavigation.navToDash();

        checkSearch();
        UINavigation.navToDash();
    }
}
