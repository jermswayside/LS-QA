package TestConfigurations;
import Tests.FlashcardTests;
import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;

public class FlashcardConfig {
    private static String url = Utility.getSiteVersionFlashcard();

    public static void checkAllFlashcardTests(){

    }

    public static void checkArrowKeysNav() throws InterruptedException {
        CommonResources.browserDriver.get(url);
        FlashcardTests.arrowKeysNav();
    }

    public static void checkArrowKeysFlip() throws InterruptedException {
        CommonResources.browserDriver.get(url);
        FlashcardTests.arrowKeysFlip();
    }

    public static void checkClickFlip() throws InterruptedException {
        CommonResources.browserDriver.get(url);
        FlashcardTests.clickFlip();
    }

    public static void checkArrowUINav() throws InterruptedException {
        CommonResources.browserDriver.get(url);
        FlashcardTests.arrowUINav();
    }

    public static void checkSlider() throws InterruptedException {
        CommonResources.browserDriver.get(url);
        FlashcardTests.slider();
    }

    public static void checkLearned() throws InterruptedException {
        CommonResources.browserDriver.get(url);
        FlashcardTests.learned();
    }
}
