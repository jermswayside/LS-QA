package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Objects;

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

        checkSummary();

        checkHyperlinks();

        WebElement submitButton = getSubmitButton();
        submitButton.click();

        Thread.sleep(1000);

        CommonResources.browserDriver.switchTo().alert().accept();

        List<WebElement> questionsTableQuestions = getQuestionsTableQuestions();

        for(int i = 0; i<questionsTableQuestions.size(); i++) {
            WebElement currQuestion = questionsTableQuestions.get(i);
            List<WebElement> questionFields = getQuestionFields(currQuestion);
            String questionTitle = questionFields.get(0).getText();
            String questionGrade = questionFields.get(1).getText();
            if(!questionTitle.equals("Essay Question") &&
                    !questionTitle.equals("Simple Recording Question") &&
                    !questionTitle.equals("Comparative Recording Question") &&
                    !questionTitle.equals("None Question (Nonyabizzzznezzzzz)")) {
                if(!questionGrade.equals("100")) {
                    System.out.printf("Answers for %s not saved correctly.\n", questionTitle);
                    System.exit(0);
                }
            }
        }
    }


    private static void checkHyperlinks() throws InterruptedException {
        List<WebElement> summaryTitles = getSummaryTitles();
        List<WebElement> questionTitle = getAllQuestionTitles();

        for(int i=0; i<summaryTitles.size()-1; i++) {
            WebElement currTitle = summaryTitles.get(i);

            WebElement currQuestionTitle = questionTitle.get(i);

            UINavigation.scrollTo(currTitle);

            Thread.sleep(1000);

            currTitle.click();
            Thread.sleep(2000);

            if(!currQuestionTitle.getText().equals(currTitle.getText())){
                System.out.printf("Summary title %s does not correspond with quiz title %s",
                        currTitle, currQuestionTitle);
                System.exit(0);
            }
            if(!currQuestionTitle.isDisplayed()){
                System.out.println("Hyperlink not working correctly");
                System.exit(0);
            }

        }
    }

    private static void checkSummary() throws InterruptedException {
        List<WebElement> summaryItems = getSummaryItems();
        for(int i = 0; i<summaryItems.size(); i++) {
            WebElement currSummaryItem = summaryItems.get(i);
            if(!currSummaryItem.getAttribute("class").equals(CommonResources.summaryBoxChecked)){
                WebElement checkedSummaryItem = Utility.waitForElementToExistByCssSelector(
                        CommonResources.cssSelectorSummaryItemsChecked);
                if(checkedSummaryItem == null) {
                    System.out.println("Summary item not checked/took too long to be checked.");
                    System.exit(0);
                }
            }
        }
    }

    private static List<WebElement> getAllQuestionTitles() {
        return CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorQuestionTitle));
    }
    private static List<WebElement> getSummaryTitles() {
        return CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorSummaryTitles));
    }

    private static List<WebElement> getSummaryItems() {
        return CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorSummaryItems));
    }
    private static List<WebElement> getQuestionFields(WebElement currQuestion) {
        return currQuestion.findElements(By.cssSelector(CommonResources.cssSelectorQuestionsTableQuestionsFields));
    }
    private static List<WebElement> getQuestionsTableQuestions() throws InterruptedException {
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorQuestionsTableQuestions);
    }

    private static WebElement getSubmitButton() {
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorQuizSubmitButton));
    }

    private static void simpleTextQuestion() throws InterruptedException {
        WebElement simpleTextQuestionInput = getSimpleTextQuestionInput();

        UINavigation.scrollTo(simpleTextQuestionInput);
        Thread.sleep(1000);

        simpleTextQuestionInput.click();

        if(!virtualKeyboardIsDisplayed(getSimpleTextQuestion())){
            System.out.println("Virtual keyboard did not appeaar for Simple Text/Question 1");
            System.exit(0);
        }

        WebElement simpleTextQuestionTitle = getSimpleTextQuestionTitle();

        UINavigation.scrollTo(simpleTextQuestionTitle);
        Thread.sleep(1000);

        simpleTextQuestionTitle.click();
        Thread.sleep(2000);

        if(virtualKeyboardIsDisplayed(getSimpleTextQuestion())){
            System.out.println("Virtual keyboard stayed appeared for Simple Text/Question 1");
            System.exit(0);
        }

        simpleTextQuestionInput.sendKeys("correct");
    }


    private static void essayQuestion() throws InterruptedException {
        WebElement essayQuestionInput = getEssayQuestionInput();
        essayQuestionInput.sendKeys("correct");
        Thread.sleep(2000);
    }


    private static void clozeQuestion() throws InterruptedException {
        WebElement clozeQuestionInput = getClozeQuestionInput();

        UINavigation.scrollTo(clozeQuestionInput);
        Thread.sleep(1000);

        clozeQuestionInput.click();

        if(!virtualKeyboardIsDisplayed(getClozeQuestion())) {
            System.out.println("Virtual keyboard did not appeaar for Cloze question/Question 3");
            System.exit(0);
        }

        clozeQuestionInput.sendKeys("correct");

        WebElement clozeQuestionTitle = getClozeQuestionTitle();
        UINavigation.scrollTo(clozeQuestionTitle);
        Thread.sleep(2000);

        clozeQuestionTitle.click();
        Thread.sleep(2000);

        if(virtualKeyboardIsDisplayed(getClozeQuestion())){
            System.out.println("Virtual keyboard stayed sppeared for Cloze question/Question 3");
            System.exit(0);
        }

        WebElement clozeQuestionSelect = getClozeQuestionSelect();
        Select dropCloze = new Select(clozeQuestionSelect);
        Thread.sleep(1000);
        dropCloze.selectByVisibleText("correct");
    }

    private static void multipleChoiceNoShuffle() throws InterruptedException {
        WebElement multipleChoiceNoShuffle = getMultipleChoiceQuestions().get(0);
        UINavigation.scrollTo(multipleChoiceNoShuffle);
        List<WebElement> multipleChoiecNoShuffleOptions = getMultipleChoiceQuestionsOptions(multipleChoiceNoShuffle);
        for(int i = 0; i<multipleChoiecNoShuffleOptions.size(); i++){
            WebElement currOpt = multipleChoiecNoShuffleOptions.get(i);
            if (Objects.equals(currOpt.getText(), "correct")){
                currOpt.click();
            }
        }
    }

    private static void multipleChoiceNoWithShuffle() throws InterruptedException {
        WebElement multipleChoiceNoShuffle = getMultipleChoiceQuestions().get(1);
        UINavigation.scrollTo(multipleChoiceNoShuffle);
        List<WebElement> multipleChoiecNoShuffleOptions = getMultipleChoiceQuestionsOptions(multipleChoiceNoShuffle);
        for(int i = 0; i<multipleChoiecNoShuffleOptions.size(); i++){
            WebElement currOpt = multipleChoiecNoShuffleOptions.get(i);
            if (Objects.equals(currOpt.getText(), "correct")){
                currOpt.click();
            }
        }
    }

    private static void multipleChoiceWithShuffleAndMultipleCorrect() throws InterruptedException {
        WebElement multipleChoiceNoShuffle = getMultipleChoiceQuestions().get(2);
        UINavigation.scrollTo(multipleChoiceNoShuffle);
        List<WebElement> multipleChoiecNoShuffleOptions = getMultipleChoiceQuestionsOptions(multipleChoiceNoShuffle);
        for(int i = 0; i<multipleChoiecNoShuffleOptions.size(); i++){
            WebElement currOpt = multipleChoiecNoShuffleOptions.get(i);
            if (!Objects.equals(currOpt.getText(), "incorrect")){
                currOpt.click();
            }
        }
    }

    private static void rankQuestion() throws InterruptedException {
        List<WebElement> rankQuestionOptions = getRankQuestionOptions();
        WebElement firstOption = rankQuestionOptions.get(1);
        WebElement secondOption = rankQuestionOptions.get(0);

        UINavigation.clickAndHold(firstOption, secondOption);

        WebElement rankPlaceholder = getRankPlaceholder();
        UINavigation.release(rankPlaceholder);

        rankQuestionOptions = getRankQuestionOptions();
        firstOption = rankQuestionOptions.get(0);
        if(!firstOption.getText().equals("Second Thing")){
            System.out.println("Ranking Question not working properly.");
            System.exit(0);
        }
        secondOption = rankQuestionOptions.get(1);
        UINavigation.dragAndDrop(secondOption, firstOption);

        rankQuestionOptions = getRankQuestionOptions();
        firstOption = rankQuestionOptions.get(0);
        if(!firstOption.getText().equals("First Thing")) {
            System.out.println("Ranking Question rearrangement didn't work properly.");
            System.exit(0);
        }
    }

    private static void matchingQuestion() {
        WebElement matchingQuestion = getMatchingQuestion();
        List<WebElement> matchingLeft = getMatchingQuestionOptionsLeft();
        List<WebElement> matchingRight = getMatchingQuesitonOptionsRight();

        UINavigation.scrollTo(matchingQuestion);

        for(int i = 0; i<matchingLeft.size(); i++){
            WebElement currOptionLeft = matchingLeft.get(i);
            for(int y = 0; y<matchingRight.size(); y++) {
                WebElement currOptionRight = matchingRight.get(y);
                if(currOptionLeft.getText().equals(currOptionRight.getText())){
                    currOptionLeft.click();
                    currOptionRight.click();
                    int currSVGSize = getMatchingQuestionSVG().size();
                    if(currSVGSize != i+1){
                        System.out.println("Did not create line from option left to right.");
                        System.exit(0);
                    }
                    break;
                }
            }
        }
    }

    private static void simpleRecordingQuestion() throws InterruptedException {
        WebElement simpleRecordingQuestion = getSimpleRecordingQuestion();
        UINavigation.scrollTo(simpleRecordingQuestion);

        Thread.sleep(1000);

        WebElement simpleAudioRecordingStartButton = getSimpleRecordingAudioStartButton();
        simpleAudioRecordingStartButton.click();

        Thread.sleep(3000);
        WebElement simpleAudioRecordingStopButton = getSimpleRecordingAudioStopButton();
        if(!simpleAudioRecordingStopButton.isDisplayed()){
            System.out.println("Simple audio recording stop button did not display.");
            System.exit(0);
        }

        Thread.sleep(10000);
        simpleAudioRecordingStopButton.click();

        Thread.sleep(2000);
        WebElement simpleRecordingAudioRecordingBar = getSimpleRecordingAudioRecordingBar();
        if(simpleRecordingAudioRecordingBar == null) {
            System.out.println("Audio recording bar did not appear.");
            System.exit(0);
        }

        WebElement simpleVideoRecordingStartButton = getSimpleVideoRecordingStartButton();
        simpleVideoRecordingStartButton.click();

        Thread.sleep(3000);
        WebElement simpleVideoRecordingStopButton = getSimpleVideoRecordingStopButton();

        if(!simpleVideoRecordingStopButton.isDisplayed()){
            System.out.println("Simple video recording start button did not display.");
            System.exit(0);
        }

        Thread.sleep(10000);

        simpleVideoRecordingStopButton.click();

        Thread.sleep(1000);

        WebElement videoOutput = getVideoOutputs().get(1);
        Thread.sleep(1000);
        if(!videoOutput.isDisplayed()){
            System.out.println("Simple Recording Question video is not displaying correctly");
            System.exit(0);
        }
    }

    private static void comparativeRecordingQuestion() throws InterruptedException {
        WebElement comparativeRecord = getComparativeRecordingQuestion();
        UINavigation.scrollTo(comparativeRecord);
        Thread.sleep(1000);

        WebElement startButton = getComparativeStartButton();
        startButton.click();

        Thread.sleep(2000);

        WebElement stopButton = getComparativeStopButton();
        if(!stopButton.isDisplayed()){
            System.out.println("Stop button in Comparative Recording Question did not appear.");
            System.exit(0);
        }

        Thread.sleep(10000);

        stopButton.click();

        Thread.sleep(1000);
        if(!startButton.isDisplayed()){
            System.out.println("Start button in Comparative Recording Question did not appear.");
            System.exit(0);
        }

        WebElement comparativeAudioBar = getComparativeAudioBar();
        if(comparativeAudioBar == null) {
            System.out.println("Audio Bar Object not created.");
            System.exit(0);
        }
        else{
            if(!comparativeAudioBar.isDisplayed()){
                System.out.println("Audio bar not displayed.");
                System.exit(0);
            }
        }
    }

    private static WebElement getComparativeAudioBar() {
        try {
            return getComparativeRecordingQuestion().findElement(By.cssSelector(
                    CommonResources.cssSelectorComparativeRecordingAudioBar));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }
    private static WebElement getComparativeStopButton() {
        return getComparativeRecordingQuestion().findElement(By.cssSelector(
                CommonResources.cssSelectorComparativeRecordingQuestionAudioStopButton));
    }

    private static WebElement getComparativeStartButton() {
        return getComparativeRecordingQuestion().findElement(By.cssSelector(
                CommonResources.cssSelectorComparativeRecordingQuestionAudioStartButton));

    }
    private static WebElement getComparativeRecordingQuestion() {
        return CommonResources.browserDriver.findElement(By.cssSelector(
                CommonResources.cssSelectorComparativeRecordingQuestion));
    }
    private static List<WebElement> getVideoOutputs() {
        return getSimpleRecordingQuestion().findElements(
                By.cssSelector(CommonResources.cssSelectorSimplerecordingVideoRecordings));
    }
    private static WebElement getSimpleVideoRecordingStopButton() {
        return getSimpleRecordingQuestion().findElement(By.cssSelector(
                CommonResources.cssSelectorSimpleRecordingStopVideoRecordingButton));
    }

    private static WebElement getSimpleVideoRecordingStartButton() {
        return getSimpleRecordingQuestion().findElement(By.cssSelector(
                CommonResources.cssSelectorSimpleRecordingStartVideoRecordingButton));
    }

    private static WebElement getSimpleRecordingAudioRecordingBar() {
        try{
            return getSimpleRecordingQuestion().findElement(By.cssSelector(
                    CommonResources.cssSelectorSimpleRecordingAudioRecordingBar));
        }
        catch(NoSuchElementException n) {
            return null;
        }
    }
    private static WebElement getSimpleRecordingAudioStopButton() {
        return getSimpleRecordingQuestion().findElement(By.cssSelector(
                CommonResources.cssSelectorSimpleRecordingStopAudioRecordingButton));
    }

    private static WebElement getSimpleRecordingAudioStartButton() {
        return getSimpleRecordingQuestion().findElement(By.cssSelector(
                CommonResources.cssSelectorSimpleRecordingStartAudioRecordingButton));
    }

    private static WebElement getSimpleRecordingQuestion() {
        return CommonResources.browserDriver.findElement(By.cssSelector(
                CommonResources.cssSelectorSimpleRecordingQuestion));
    }

    private static WebElement getMatchingQuestion() {
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorMatchingQuestion));
    }

    private static List<WebElement> getMatchingQuestionSVG() {
        return getMatchingQuestion().findElements(By.cssSelector(CommonResources.cssSelectorSVG));
    }

    private static List<WebElement> getMatchingQuestionOptionsLeft() {
        return getMatchingQuestion().findElements(By.cssSelector(
                CommonResources.cssSelectorMatchingQuestionOptionsLeft));
    }

    private static List<WebElement> getMatchingQuesitonOptionsRight() {
        return getMatchingQuestion().findElements(By.cssSelector(
                CommonResources.cssSelectorMatchingQuestionOptionsRight));
    }

    private static WebElement getRankPlaceholder() {
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorrankPlaceholder));
    }

    private static WebElement getRankQuestion() {
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorRankQuestion));
    }

    private static List<WebElement> getRankQuestionOptions(){
        return getRankQuestion().findElements(By.cssSelector(CommonResources.cssSelectorRankQuestionOptions));
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
                CommonResources.cssSelectorQuestionTitle
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

    private static WebElement getEssayQuestion() throws InterruptedException {
        return CommonResources.browserDriver.findElement(By.cssSelector(
                CommonResources.cssSelectorEssayQuestion
        ));
    }

    private static WebElement getEssayQuestionInput() throws InterruptedException {
        return getEssayQuestion().findElement(By.cssSelector(
                CommonResources.cssSelectorEssayInput
        ));
    }

    private static List<WebElement> getMultipleChoiceQuestions() throws InterruptedException {
        return CommonResources.browserDriver.findElements(By.cssSelector(
                CommonResources.cssSelectorMultipleChoiceQuestions
        ));
    }

    private static List<WebElement> getMultipleChoiceQuestionsOptions(WebElement multipleChoiceQuestion) throws InterruptedException {
        return multipleChoiceQuestion.findElements(By.cssSelector(
                CommonResources.cssSelectorMultipleChoiceQuestionsOptions));
    }
}
