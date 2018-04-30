import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import Objects.User;

import java.io.IOException;
import java.util.*;

public class qaTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        CommonResources.siteChoiceEntry = Utility.getSiteVersion();
        Scanner qaTestModeChoice = new Scanner(System.in);

        String browser = "";
        String driver = "";

        Scanner scanBrowserChoice = new Scanner(System.in);
        int choiceEntry;
        System.out.println("Which browser driver would you like to use?:\n" +
                "\"1\" - Google Chrome\n" +
                "\"2\" - Firefox\n" +
                "\"3\" - Quit the program");
        choiceEntry = scanBrowserChoice.nextInt();

        switch(choiceEntry) {
            case 1: browser = CommonResources.pathChromeDriver; driver = CommonResources.chromeDriver; break;
            case 2: browser = CommonResources.pathChromeDriver; driver = CommonResources.chromeDriver; break;
            default: System.out.println("Please input a valid number");
        }

        CommonResources.browserDriver = Utility.startBrowser(driver, browser, CommonResources.siteChoiceEntry);
        User teacher = new User(CommonResources.usernameTeacher, CommonResources.passwordTeacher);
        Utility.login(teacher, CommonResources.browserDriver);


        Thread.sleep(10000);
        UINavigation.clickSkip();

        System.out.println("Which mode would you like to run tests on?:\n" +
                "\"D\" - Debug\n" +
                "\"N\" - Normal");

        CommonResources.qaTestMode = qaTestModeChoice.next().toLowerCase();

        switch (CommonResources.qaTestMode) {
            case "d":
                qaTestDebug.run();
                break;
            case "n":
                qaTestNormal.run();
                break;
            default:
                System.out.println("Invalid mode input.");
        }
    }
}