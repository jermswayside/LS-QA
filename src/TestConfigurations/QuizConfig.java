package TestConfigurations;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import Tests.QuizTests;
import org.openqa.selenium.WebElement;

public class QuizConfig {
    public static void checkAllQuizTests() throws InterruptedException {
        String url = Utility.getSiteVersionQuiz();
        CommonResources.browserDriver.get(url);

        Utility.getQuizStartButton().click();

        QuizTests.simpleTextQuestion(); //Question 1

        QuizTests.essayQuestion(); //Question 2

        QuizTests.clozeQuestion(); //Question 3

        QuizTests.multipleChoiceNoShuffle(); //Question 4

        QuizTests.multipleChoiceWithShuffle(); //Question 5

        QuizTests.multipleChoiceWithShuffleAndMultipleCorrect(); //Question 6

        QuizTests.rankQuestion(); //Question 7

        QuizTests.matchingQuestion(); //Question 8

        QuizTests.simpleRecordingQuestion(); //Question 9

        QuizTests.comparativeRecordingQuestion(); //Question 10

        QuizTests.checkHyperlinks();

        QuizTests.checkSummary();

        WebElement submitButton = QuizTests.getSubmitButton();
        submitButton.click();

        Thread.sleep(1000);

        CommonResources.browserDriver.switchTo().alert().accept();

        QuizTests.checkCorrectSaves();
        UINavigation.navToDash();
    }
}
