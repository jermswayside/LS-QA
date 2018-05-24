package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import Objects.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuizTests {
    private static final String currCat = CommonResources.getAllCategories().get(8);

    public static void simpleTextQuestion() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Simple Text Question", "", "");
        long startTime = System.nanoTime();
        WebElement simpleTextQuestionInput = getSimpleTextQuestionInput();

        UINavigation.scrollTo(simpleTextQuestionInput);
        Thread.sleep(1000);

        simpleTextQuestionInput.click();

        if(!virtualKeyboardIsDisplayed(getSimpleTextQuestion())){
            System.out.println("Virtual keyboard did not appear for Simple Text/Question 1");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        WebElement simpleTextQuestionTitle = getSimpleTextQuestionTitle();

        UINavigation.scrollTo(simpleTextQuestionTitle);
        Thread.sleep(1000);

        simpleTextQuestionTitle.click();
        Thread.sleep(2000);

        if(virtualKeyboardIsDisplayed(getSimpleTextQuestion())){
            System.out.println("Virtual keyboard stayed appeared for Simple Text/Question 1");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }
        Thread.sleep(2000);
        simpleTextQuestionInput.sendKeys("correct");
//        if(!checkForSpinner()){
//            System.out.println("Spinner not working correctly.");
//            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
//            return;
//        }

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }


    public static void essayQuestion() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Essay Question", "", "");
        long startTime = System.nanoTime();
        WebElement essayQuestionInput = getEssayQuestionInput();
        Thread.sleep(1000);
        essayQuestionInput.sendKeys("correct");
//        if(!checkForSpinner()){
//            System.out.println("Spinner not working correctly.");
//            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
//            return;
//        }
        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }

    public static void clozeQuestion() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Cloze Quesiton", "", "");
        long startTime = System.nanoTime();
        List<WebElement> clozeQuestionInput = getClozeQuestionInput();

        List<WebElement> clozeQuestionFitb = new ArrayList<>();
        List<WebElement> clozeQuestionSelect = new ArrayList<>();

        for(int i = 0; i<clozeQuestionInput.size(); i++){
            WebElement currInput = clozeQuestionInput.get(i);
            List<WebElement> currChildElements = currInput.findElements(By.xpath("*"));
            for (int j = 0; j<currChildElements.size(); j++){
                WebElement currTag = currChildElements.get(j);
                System.out.println(currTag.getTagName());
                if(currTag.getTagName().equals("input")){
                    clozeQuestionFitb.add(currTag);
                }
                else if(currTag.getTagName().equals("select")){
                    clozeQuestionSelect.add(currTag);
                }
            }
        }

        for(int i = 0; i<clozeQuestionFitb.size(); i++) {
            WebElement currFitb = clozeQuestionFitb.get(i);
            UINavigation.scrollTo(currFitb);
            Thread.sleep(1000);

            currFitb.click();

            if (!virtualKeyboardIsDisplayed(getClozeQuestion())) {
                System.out.println("Virtual keyboard did not appeaar for Cloze question/Question 3");
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                return;
            }

            currFitb.sendKeys("correct");

//            if(!checkForSpinner()){
//                System.out.println("Spinner not working correctly.");
//                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
//                return;
//            }

            WebElement clozeQuestionTitle = getClozeQuestionTitle();
            UINavigation.scrollTo(clozeQuestionTitle);
            Thread.sleep(2000);

            clozeQuestionTitle.click();
            Thread.sleep(2000);

            if(virtualKeyboardIsDisplayed(getClozeQuestion())){
                System.out.println("Virtual keyboard stayed appeared for Cloze question/Question 3");
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                return;
            }
        }

        for(int i = 0; i<clozeQuestionSelect.size(); i++) {
            WebElement currSelect = clozeQuestionSelect.get(i);
            Select dropCloze = new Select(currSelect);
            Thread.sleep(1000);

            dropCloze.selectByVisibleText("correct");
//            if(!checkForSpinner()){
//                System.out.println("Spinner not working correctly.");
//                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
//                return;
//            }
        }

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }

    public static void multipleChoiceNoShuffle() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Multiple Choice No Shuffle", "", "");
        long startTime = System.nanoTime();
        WebElement multipleChoiceNoShuffle = getMultipleChoiceQuestions().get(0);
        UINavigation.scrollTo(multipleChoiceNoShuffle);
        List<WebElement> multipleChoiecNoShuffleOptions = getMultipleChoiceQuestionsOptions(multipleChoiceNoShuffle);
        for(int i = 0; i<multipleChoiecNoShuffleOptions.size(); i++){
            WebElement currOpt = multipleChoiecNoShuffleOptions.get(i);
            if (Objects.equals(currOpt.getText(), "correct")) {
                currOpt.click();
//                if(!checkForSpinner()){
//                    System.out.println("Spinner not working correctly.");
//                    Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
//                    return;
//                }
            }
        }
        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }

    public static void multipleChoiceWithShuffle() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Multiple Choice With Shuffle", "", "");
        long startTime = System.nanoTime();

        WebElement multipleChoiceNoShuffle = getMultipleChoiceQuestions().get(1);
        UINavigation.scrollTo(multipleChoiceNoShuffle);
        List<WebElement> multipleChoiecNoShuffleOptions = getMultipleChoiceQuestionsOptions(multipleChoiceNoShuffle);
        for(int i = 0; i<multipleChoiecNoShuffleOptions.size(); i++){
            WebElement currOpt = multipleChoiecNoShuffleOptions.get(i);
            if (Objects.equals(currOpt.getText(), "correct")) {
                currOpt.click();
//                if(!checkForSpinner()){
//                    System.out.println("Spinner not working correctly.");
//                    Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
//                    return;
//                }
            }
        }
        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }

    public static void multipleChoiceWithShuffleAndMultipleCorrect() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Multiple Choice w/ Shuffle and Multiple Corrects", "", "");
        long startTime = System.nanoTime();
        WebElement multipleChoiceNoShuffle = getMultipleChoiceQuestions().get(2);
        UINavigation.scrollTo(multipleChoiceNoShuffle);
        List<WebElement> multipleChoiecNoShuffleOptions = getMultipleChoiceQuestionsOptions(multipleChoiceNoShuffle);
        for(int i = 0; i<multipleChoiecNoShuffleOptions.size(); i++){
            WebElement currOpt = multipleChoiecNoShuffleOptions.get(i);
            if(!Objects.equals(currOpt.getText(), "incorrect")){
                currOpt.click();

            }
        }
//        if(!checkForSpinner()){
//            System.out.println("Spinner not working correctly.");
//            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
//            return;
//        }
        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }

    public static void rankQuestion() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Rank Question", "", "");
        long startTime = System.nanoTime();
        List<WebElement> rankQuestionOptions = getRankQuestionOptions();
        WebElement firstOption = rankQuestionOptions.get(1);
        WebElement secondOption = rankQuestionOptions.get(0);
        UINavigation.clickAndHold(firstOption, secondOption);

        WebElement rankPlaceholder = getRankPlaceholder();
        UINavigation.release(rankPlaceholder);

        rankQuestionOptions = getRankQuestionOptions();
        firstOption = rankQuestionOptions.get(0);
        if (!firstOption.getText().equals("Second Thing")) {
            System.out.println("Ranking Question not working properly.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }
        secondOption = rankQuestionOptions.get(0);
        firstOption = rankQuestionOptions.get(1);
        UINavigation.clickAndHold(secondOption, firstOption);
        rankPlaceholder = getRankPlaceholder();
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
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                return;
            }
        }
//        if(!checkForSpinner()){
//            System.out.println("Spinner not working correctly.");
//            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
//            return;
//        }

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }

    public static void matchingQuestion() {
        Test currTest = new Test(currCat, "Check Matching Question", "", "");
        long startTime = System.nanoTime();
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
                        Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                        return;
                    }

                }
            }
        }

//        if(!checkForSpinner()){
//            System.out.println("Spinner not working correctly.");
//            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
//            return;
//        }

        long endTime = System.nanoTime();

        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }

    public static void simpleRecordingQuestion() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Simple Recording Question", "", "");
        long startTime = System.nanoTime();
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

        UINavigation.browserAlertAction("OK");

        Thread.sleep(3000);
        WebElement simpleVideoRecordingStopButton = getSimpleVideoRecordingStopButton();

        if(!simpleVideoRecordingStopButton.isDisplayed()){
            System.out.println("Simple video recording start button did not display.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        Thread.sleep(10000);

        simpleVideoRecordingStopButton.click();

        Thread.sleep(1000);

        WebElement videoOutput = getVideoOutputs().get(1);
        if(!videoOutput.isDisplayed()){
            System.out.println("Simple Recording Question video is not displaying correctly");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }
//        if(!checkForSpinner()){
//            System.out.println("Spinner not working correctly.");
//            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
//            return;
//        }

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }

    public static void comparativeRecordingQuestion() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Comparative Recording Question", "", "");
        long startTime = System.nanoTime();
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
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        Thread.sleep(10000);

        stopButton.click();

        Thread.sleep(1000);
        if(!startButton.isDisplayed()){
            System.out.println("Start button in Comparative Recording Question did not appear.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        WebElement comparativeAudioBar = getComparativeAudioBar();
        if(comparativeAudioBar == null) {
            System.out.println("Audio Bar Object not created.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }
        else{
            if(!comparativeAudioBar.isDisplayed()){
                System.out.println("Audio bar not displayed.");
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                return;
            }
        }

//        if(!checkForSpinner()){
//            System.out.println("Spinner not working correctly.");
//            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
//            return;
//        }

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }

    public static void checkCorrectSaves() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Correct Saves", "", "");
        long startTime = System.nanoTime();
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
                    Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                    return;
                }
            }
        }
        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }

    public static void checkHyperlinks() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Hyperlinks", "", "");
        long startTime = System.nanoTime();
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
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                return;
            }
            if(!currQuestionTitle.isDisplayed()){
                System.out.println("Hyperlink not working correctly");
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                return;
            }
        }
        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }

    public static void checkSummary() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Summary", "", "");
        long startTime = System.nanoTime();
        List<WebElement> summaryItems = getSummaryItems();
        for(int i = 0; i<summaryItems.size(); i++) {
            WebElement currSummaryItem = summaryItems.get(i);
            if(!currSummaryItem.getAttribute("class").equals(CommonResources.summaryBoxChecked)){
                System.out.println("Summary item not checked/took too long to be checked.");
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                return;
            }
        }
        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")){
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static boolean checkForSpinner() {
        WebElement saveSpinner = getSaveSpinner();
        try {
            Utility.waitForVisible(saveSpinner);
            Utility.waitForNotVisible(saveSpinner);
            return true;
        }
        catch (TimeoutException t){
            return false;
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

    private static List<WebElement> getClozeQuestionInput() throws InterruptedException {
        return getClozeQuestion().findElements(By.cssSelector(CommonResources.cssSelectorClozeQuestionInput));
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

    public static WebElement getSubmitButton() {
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorQuizSubmitButton));
    }
}
