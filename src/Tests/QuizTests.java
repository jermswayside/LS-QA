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
        long startTime = System.nanoTime();
        String url = "";
        if(CommonResources.siteChoiceEntry == 1) {
            url = "https://stagelearningsite.waysidepublishing.com/explorer/5344303/quiz/5238761/start";
        }
        if(CommonResources.siteChoiceEntry == 2) {
            url = "https://learningsite.waysidepublishing.com/explorer/5238731/quiz/5238761/start";
        }
        if(CommonResources.siteChoiceEntry == 3) {
            System.out.println("Dev does not have QA Textbook");
            return;
        }
        CommonResources.browserDriver.get(url);

        boolean correct = true;
        boolean incorrect = false;

        WebElement start = getStart();
        start.click();

        simpleTextQuestion(incorrect); //Question 1

        essayQuestion(incorrect); //Question 2

        clozeQuestion(incorrect); //Question 3

        multipleChoiceNoShuffle(incorrect); //Question 4

        multipleChoiceNoWithShuffle(incorrect); //Question 5

        multipleChoiceWithShuffleAndMultipleCorrect(incorrect); //Question 6

        rankQuestion(incorrect); //Question 7

        matchingQuestion(incorrect); //Question 8

        simpleRecordingQuestion(incorrect); //Question 9

        comparativeRecordingQuestion(incorrect); //Question 10



        checkHyperlinks();

        simpleTextQuestion(correct); //Question 1

        essayQuestion(correct); //Question 2

        clozeQuestion(correct); //Question 3

        multipleChoiceNoShuffle(correct); //Question 4

        multipleChoiceNoWithShuffle(correct); //Question 5

        multipleChoiceWithShuffleAndMultipleCorrect(correct); //Question 6

        rankQuestion(correct); //Question 7

        matchingQuestion(correct); //Question 8

        simpleRecordingQuestion(correct); //Question 9

        comparativeRecordingQuestion(correct); //Question 10

        checkSummary();

        WebElement submitButton = getSubmitButton();
        submitButton.click();

        Thread.sleep(1000);

        CommonResources.browserDriver.switchTo().alert().accept();

        checkCorrectSaves();

        long endTime = System.nanoTime();
        Utility.nanoToReadableTime(startTime, endTime);
    }


    private static void checkCorrectSaves() throws InterruptedException {
        List<WebElement> questionsTableQuestions = getQuestionsTableQuestions();

        for(int i = 0; i<questionsTableQuestions.size(); i++) {
            WebElement currQuestion = questionsTableQuestions.get(i);
            List<WebElement> questionFields = getQuestionFields(currQuestion);
            String questionTitle = questionFields.get(0).getText();
            String questionGrade = questionFields.get(1).getText();
            if(!questionTitle.equals("Question 2") &&
                    !questionTitle.equals("Question 9") &&
                    !questionTitle.equals("Question 10") &&
                    !questionTitle.equals("Description")) {
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

    private static void simpleTextQuestion(boolean correct) throws InterruptedException {
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

        if(correct) {
            simpleTextQuestionInput.clear();
            Thread.sleep(2000);
            simpleTextQuestionInput.sendKeys("correct");
            checkForSpinner();
        }
        else{
            simpleTextQuestionInput.sendKeys("incorrect");
        }
    }


    private static void essayQuestion(boolean correct) throws InterruptedException {
        WebElement essayQuestionInput = getEssayQuestionInput();
        if(correct) {
            essayQuestionInput.clear();
            Thread.sleep(1000);
            essayQuestionInput.sendKeys("correct");
            checkForSpinner();
        }
        else{
            essayQuestionInput.sendKeys("incorrect");
        }
        Thread.sleep(2000);
    }


    private static void clozeQuestion(boolean correct) throws InterruptedException {
        WebElement clozeQuestionInput = getClozeQuestionInput();

        UINavigation.scrollTo(clozeQuestionInput);
        Thread.sleep(1000);

        clozeQuestionInput.click();

        if(!virtualKeyboardIsDisplayed(getClozeQuestion())) {
            System.out.println("Virtual keyboard did not appeaar for Cloze question/Question 3");
            System.exit(0);
        }

        if(correct) {
            clozeQuestionInput.clear();
            Thread.sleep(1000);
            clozeQuestionInput.sendKeys("correct");
        }
        else {
            clozeQuestionInput.sendKeys("incorrect");
        }

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
        if(correct) {
            dropCloze.selectByVisibleText("correct");
            checkForSpinner();
        }
        else{
            dropCloze.selectByVisibleText("incorrect");
        }
    }

    private static void multipleChoiceNoShuffle(boolean correct) throws InterruptedException {
        WebElement multipleChoiceNoShuffle = getMultipleChoiceQuestions().get(0);
        UINavigation.scrollTo(multipleChoiceNoShuffle);
        List<WebElement> multipleChoiecNoShuffleOptions = getMultipleChoiceQuestionsOptions(multipleChoiceNoShuffle);
        for(int i = 0; i<multipleChoiecNoShuffleOptions.size(); i++){
            WebElement currOpt = multipleChoiecNoShuffleOptions.get(i);
            if(correct) {
                if (Objects.equals(currOpt.getText(), "correct")) {
                    currOpt.click();
                    checkForSpinner();
                }
            }
            else {
                if (!Objects.equals(currOpt.getText(), "correct")){
                    currOpt.click();
                }
            }
        }
    }

    private static void multipleChoiceNoWithShuffle(boolean correct) throws InterruptedException {
        WebElement multipleChoiceNoShuffle = getMultipleChoiceQuestions().get(1);
        UINavigation.scrollTo(multipleChoiceNoShuffle);
        List<WebElement> multipleChoiecNoShuffleOptions = getMultipleChoiceQuestionsOptions(multipleChoiceNoShuffle);
        for(int i = 0; i<multipleChoiecNoShuffleOptions.size(); i++){
            WebElement currOpt = multipleChoiecNoShuffleOptions.get(i);
            if(correct) {
                if (Objects.equals(currOpt.getText(), "correct")) {
                    currOpt.click();
                    checkForSpinner();
                }
            }
            else {
                if (!Objects.equals(currOpt.getText(), "correct")){
                    currOpt.click();
                }
            }

        }
    }

    private static void multipleChoiceWithShuffleAndMultipleCorrect(boolean correct) throws InterruptedException {
        WebElement multipleChoiceNoShuffle = getMultipleChoiceQuestions().get(2);
        UINavigation.scrollTo(multipleChoiceNoShuffle);
        List<WebElement> multipleChoiecNoShuffleOptions = getMultipleChoiceQuestionsOptions(multipleChoiceNoShuffle);
        for(int i = 0; i<multipleChoiecNoShuffleOptions.size(); i++){
            WebElement currOpt = multipleChoiecNoShuffleOptions.get(i);
            if(correct) {
                currOpt.click();
            }
            else{
                if(Objects.equals(currOpt.getText(), "incorrect")){
                    currOpt.click();
                }
            }
        }
        if(correct){
            checkForSpinner();
        }
    }

    private static void rankQuestion(boolean correct) throws InterruptedException {
        List<WebElement> rankQuestionOptions = getRankQuestionOptions();
        WebElement firstOption = rankQuestionOptions.get(1);
        WebElement secondOption = rankQuestionOptions.get(0);

        if(!correct) {
            UINavigation.clickAndHold(firstOption, secondOption);

            WebElement rankPlaceholder = getRankPlaceholder();
            UINavigation.release(rankPlaceholder);

            rankQuestionOptions = getRankQuestionOptions();
            firstOption = rankQuestionOptions.get(0);
            if (!firstOption.getText().equals("Second Thing")) {
                System.out.println("Ranking Question not working properly.");
                System.exit(0);
            }
        }
        else {
            secondOption = rankQuestionOptions.get(0);
            firstOption = rankQuestionOptions.get(1);
            UINavigation.clickAndHold(secondOption, firstOption);
            WebElement rankPlaceholder = getRankPlaceholder();
            Thread.sleep(2000);
            UINavigation.release(rankPlaceholder);


            rankQuestionOptions = getRankQuestionOptions();
            firstOption = rankQuestionOptions.get(0);
            if (!firstOption.getText().equals("First Thing")) {
                secondOption = rankQuestionOptions.get(0);
                firstOption = rankQuestionOptions.get(1);
                UINavigation.clickAndHoldAndMoveByOffset(secondOption, firstOption, 0, 10);
                rankPlaceholder = getRankPlaceholder();
                UINavigation.release(rankPlaceholder);
                Thread.sleep(1000);
                if(!firstOption.getText().equals("First Thing")) {
                    System.out.println("Ranking Question rearrangement didn't work properly.");
                    System.exit(0);
                }
            }
            checkForSpinner();
        }
    }

    private static void matchingQuestion(boolean correct) {
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
                    if(correct) {
                        break;
                    }
                    else{
                        return;
                    }
                }
            }
        }
    }

    private static void simpleRecordingQuestion(boolean correct) throws InterruptedException {
        WebElement simpleRecordingQuestion = getSimpleRecordingQuestion();
        UINavigation.scrollTo(simpleRecordingQuestion);

        Thread.sleep(1000);

//        WebElement simpleAudioRecordingStartButton = getSimpleRecordingAudioStartButton();
//        simpleAudioRecordingStartButton.click();
//
//        Thread.sleep(3000);
//        WebElement simpleAudioRecordingStopButton = getSimpleRecordingAudioStopButton();
//        if(!simpleAudioRecordingStopButton.isDisplayed()){
//            System.out.println("Simple audio recording stop button did not display.");
//            System.exit(0);
//        }
//
//        Thread.sleep(10000);
//        simpleAudioRecordingStopButton.click();
//
//        Thread.sleep(2000);
//        WebElement simpleRecordingAudioRecordingBar = getSimpleRecordingAudioRecordingBar();
//        if(simpleRecordingAudioRecordingBar == null) {
//            System.out.println("Audio recording bar did not appear.");
//            System.exit(0);
//        }

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

        if(correct) {
            checkForSpinner();
        }
    }

    private static void comparativeRecordingQuestion(boolean correct) throws InterruptedException {
        WebElement comparativeRecord = getComparativeRecordingQuestion();
        UINavigation.scrollTo(comparativeRecord);
        Thread.sleep(1000);

        WebElement startButton = getComparativeStartButton();
        startButton.click();

        Thread.sleep(2000);

        WebElement stopButton = getComparativeStopButton();
        Thread.sleep(1000);
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

        if(correct) {
            checkForSpinner();
        }
    }

    private static void checkForSpinner() {
        WebElement saveSpinner = getSaveSpinner();
        Utility.waitForVisible(saveSpinner);
        Utility.waitForNotVisible(saveSpinner);
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

    private static WebElement getSaveSpinner() {
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.saveSpinner));
    }
}
