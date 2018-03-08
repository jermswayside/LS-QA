package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class QuizTests {
    public static void quizUICheck() throws InterruptedException {
        String url = "https://stagelearningsite.waysidepublishing.com/explorer/4609475/quiz/4609462/start";
        CommonResources.browserDriver.get(url);

        WebElement start = getStart();
        start.click();

        simpleTextQuestion(); //Question 1

        essayQuestion(); //Question 2

        clozeQuestion(); //Question 3

        multipleChoiceNoShuffle(); //Question 4

        multipleChoiceNoWithShuffle(); //Question 5

        multipleChoiceWithShuffleAndMultipleCorrect(); //Question 6

        rankQuestion(); //Question 7

        matchingQuestion(); //Question 8

        simpleRecordingQuestion(); //Question 9

        comparativeRecordingQuestion(); //Question 10
    }

    private static void simpleTextQuestion() throws InterruptedException {
        WebElement simpleTextQuestionInput = getSimpleTextQuestionInput();

        UINavigation.scrollTo(simpleTextQuestionInput);
        Thread.sleep(1000);

        simpleTextQuestionInput.click();

        if(!virtualKeyboardIsDisplayed(getSimpleTextQuestion())){
            System.out.println("Virtual keyboard did not appeaar for Simple Text/Question 1");
            return;
        }

        WebElement simpleTextQuestionTitle = getSimpleTextQuestionTitle();

        UINavigation.scrollTo(simpleTextQuestionTitle);
        Thread.sleep(1000);

        simpleTextQuestionInput.click();
        Thread.sleep(1000);

        if(virtualKeyboardIsDisplayed(getSimpleTextQuestion())){
            System.out.println("Virtual keyboard stayed appeared for Simple Text/Question 1");
            return;
        }

        simpleTextQuestionInput.sendKeys("Correct");
    }


    private static void essayQuestion() {
    }


    private static void clozeQuestion() throws InterruptedException {
        WebElement clozeQuestionInput = getClozeQuestionInput();

        UINavigation.scrollTo(clozeQuestionInput);
        Thread.sleep(1000);

        clozeQuestionInput.click();

        if(!virtualKeyboardIsDisplayed(getClozeQuestion())) {
            System.out.println("Virtual keyboard did not appeaar for Cloze question/Question 2");
            return;
        }

        clozeQuestionInput.sendKeys("correct");

        WebElement clozeQuestionTitle = getClozeQuestionTitle();
        UINavigation.scrollTo(clozeQuestionTitle);
        clozeQuestionInput.click();

        if(virtualKeyboardIsDisplayed(getClozeQuestion())){
            System.out.println("Virtual keyboard stayed sppeared for Cloze question/Question 2");
            return;
        }

        clozeQuestionInput.sendKeys("Correct");
    }

    private static void multipleChoiceNoShuffle() {
    }

    private static void multipleChoiceNoWithShuffle() {
    }

    private static void multipleChoiceWithShuffleAndMultipleCorrect() {
    }

    private static void rankQuestion() {
    }

    private static void matchingQuestion() {
    }

    private static void simpleRecordingQuestion() {
    }

    private static void comparativeRecordingQuestion() {
    }

    private static WebElement getStart() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorQuizStartButton);
    }

    private static WebElement getSimpleTextQuestion() throws InterruptedException {
//        WebElement simpleText = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorSimpleTextQuestion);
//        return simpleText;

        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorSimpleTextQuestion);
    }

    private static WebElement getSimpleTextQuestionInput() throws InterruptedException {
        return getSimpleTextQuestion().findElement(By.cssSelector(
                CommonResources.cssSelectorSimpleTextQuestionInput));
    }

    private static WebElement getSimpleTextQuestionTitle() throws InterruptedException {
        return getSimpleTextQuestion().findElement(By.cssSelector(
                CommonResources.cssSelectorQuizTitle
        ));
    }

    private static boolean virtualKeyboardIsDisplayed(WebElement questionType){
        try {
            if (questionType.findElement(By.cssSelector(CommonResources.cssSelectorInputVirtualKeyboard)).isDisplayed()) {
                return true;
            }
        }
        catch (NoSuchElementException n) {
            if (questionType.findElement(By.cssSelector("div > " + CommonResources.cssSelectorInputVirtualKeyboard)).isDisplayed()){
                return true;
            }
        }
        return false;
    }

    private static WebElement getClozeQuestion() throws InterruptedException {
        return CommonResources.browserDriver.findElement(By.cssSelector(
                CommonResources.cssSelectorClozeQuestion
        ));
    }

    private static WebElement getClozeQuestionTitle() throws InterruptedException {
        return getClozeQuestion().findElement(By.cssSelector(CommonResources.cssSelectorQuestionTitle));
    }

    private static WebElement getClozeQuestionInput() throws InterruptedException {
        return getClozeQuestion().findElement(By.cssSelector(CommonResources.cssSelectorClozeQuestionInput));
    }

    private static WebElement getClozeQuestionSelect() throws InterruptedException {
        return getClozeQuestion().findElement(By.cssSelector(CommonResources.cssSelectorClozeQuestionSelect));
    }
}
