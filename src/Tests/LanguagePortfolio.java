package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class LanguagePortfolio {
    public static void checkViewButton() throws InterruptedException{
        UINavigation.clickSkip();
        UINavigation.clickProfile();
        clickPortfolio();
        clickView();
        Thread.sleep(25000);
        List<WebElement> canDoStatements = getCanDoStatements();
        if(canDoStatements.size() == 0){
            System.out.println("Error");
        }
    }

    public static void checkAddQuizAttemptButton() throws InterruptedException {
        UINavigation.clickSkip();
        UINavigation.clickProfile();
        clickPortfolio();
        clickView();
        Thread.sleep(25000);
        List<WebElement> canDoStatements = getCanDoStatements();
        WebElement firstCanDo = canDoStatements.get(0);
        UINavigation.scrollTo(firstCanDo);
        firstCanDo.click();
        clickPostAComment(firstCanDo);
        List<WebElement> allCanDoButtons = getAllCanDoButtons();
        clickAddQuizAttemptButton(allCanDoButtons);
        String quizName = getFirstQuizText();
        clickFirstQuiz();
        clickAddQuizAttemptPopupProceedButton();
        clickSaveButton();
        Utility.waitForNotVisible(getLoadingSaveButton());
        WebElement commentBox = getCommentBox();
        if(commentBox == null){
            System.out.println("Error");
            return;
        }
        if(!commentBox.getText().contains(quizName)){
            System.out.println("Error");
            return;
        }
        cleanUp(commentBox);
    }

    public static void checkAddRecordingButton() throws InterruptedException {
        UINavigation.clickSkip();
        UINavigation.clickProfile();
        clickPortfolio();
        clickView();
        Thread.sleep(25000);
        List<WebElement> canDoStatements = getCanDoStatements();
        WebElement firstCanDo = canDoStatements.get(0);
        UINavigation.scrollTo(firstCanDo);
        firstCanDo.click();
        clickPostAComment(firstCanDo);
        List<WebElement> allCanDoButtons = getAllCanDoButtons();
        clickAddRecording(allCanDoButtons);
        clickStartRecording(allCanDoButtons);
        Thread.sleep(10000);
        clickStopRecording(allCanDoButtons);
        WebElement audioRecordingBar = getAudioRecordingBar();
        Thread.sleep(500);
        if(!audioRecordingBar.isDisplayed()){
            System.out.println("Error");
            return;
        }
        clickSaveButton();
        Utility.waitForNotVisible(getLoadingSaveButton());
        WebElement commentBox = getCommentBox();
        if(commentBox != null){
            WebElement commentBoxAudioRecording = getCommentBoxAudioRecording();
            if(!commentBoxAudioRecording.isDisplayed()){
                System.out.println("Error");
                return;
            }
        }
        cleanUp(commentBox);
    }

    public static void checkAddVideoRecordingButton() throws InterruptedException {
        UINavigation.clickSkip();
        UINavigation.clickProfile();
        clickPortfolio();
        clickView();
        Thread.sleep(25000);
        List<WebElement> canDoStatements = getCanDoStatements();
        WebElement firstCanDo = canDoStatements.get(0);
        UINavigation.scrollTo(firstCanDo);
        firstCanDo.click();
        clickPostAComment(firstCanDo);
        List<WebElement> allCanDoButtons = getAllCanDoButtons();
        clickAddVideoRecordingButton(allCanDoButtons);
        Thread.sleep(10000);
        clickStartVideoRecordingButton(allCanDoButtons);
        Thread.sleep(10000);
        clickStopVideoRecordingButton(allCanDoButtons);
        if(!getVideo().isDisplayed()){
            System.out.println("Error");
            return;
        }
        clickSaveButton();
        Utility.waitForNotVisible(getLoadingSaveButton());
        WebElement commentBox = getCommentBox();
        if(commentBox != null) {
            if(!getCommentBoxVideo(commentBox).isDisplayed()) {
                System.out.println("Error");
                return;
            }
        }
        cleanUp(commentBox);
    }

    public static void checkChangeProgressLink() throws InterruptedException {
        UINavigation.clickSkip();
        UINavigation.clickProfile();
        clickPortfolio();
        clickView();
        Thread.sleep(25000);
        List<WebElement> canDoStatements = getCanDoStatements();
        WebElement firstCanDo = canDoStatements.get(0);
        UINavigation.scrollTo(firstCanDo);
        firstCanDo.click();
        clickChangeProgress();
        clickInactiveProgressLevel();
        clickProgressSaveButton();
        Utility.waitForNotVisible(getLoadingProgressSaveButton());
        WebElement commentBox = getCommentBox();
        if(!commentBox.isDisplayed()) {
            System.out.println("Error");
            return;
        }
        cleanUp(commentBox);
    }

    public static void checkAddingComment() throws InterruptedException {
        UINavigation.clickSkip();
        UINavigation.clickProfile();
        clickPortfolio();
        clickView();
        Thread.sleep(25000);
        List<WebElement> canDoStatements = getCanDoStatements();
        WebElement firstCanDo = canDoStatements.get(0);
        UINavigation.scrollTo(firstCanDo);
        firstCanDo.click();
        clickPostAComment(firstCanDo);
        String text = "Testing Testing Testing";
        Thread.sleep(1000);
        enterTextInComment(text);
        clickSaveButton();
//        Utility.waitForNotVisible(getLoadingProgressSaveButton());
        WebElement commentBox = getCommentBox();
        if(!commentBox.isDisplayed()) {
            System.out.println("Error");
            return;
        }
        if(!getCommentText(commentBox).equals(text)){
            System.out.println("Error");
            return;
        }
        cleanUp(commentBox);
    }

    public static void checkFileUpload() throws InterruptedException {
        UINavigation.clickSkip();
        UINavigation.clickProfile();
        clickPortfolio();
        clickView();
        Thread.sleep(25000);
        List<WebElement> canDoStatements = getCanDoStatements();
        WebElement firstCanDo = canDoStatements.get(0);
        UINavigation.scrollTo(firstCanDo);
        firstCanDo.click();
        clickPostAComment(firstCanDo);
        List<WebElement> allCanDoButtons = getAllCanDoButtons();
        WebElement addFileButton = getAddFileButton(allCanDoButtons);
        fileUpload(addFileButton);
        Thread.sleep(5000);
        clickSaveButton();
        Utility.waitForNotVisible(getLoadingSaveButton());
        WebElement commentBox = getCommentBox();
        if(!commentBox.isDisplayed()) {
            System.out.println("Error");
            return;
        }
        if(!getCommentUploadText().contains("wednesday")){
            System.out.println("Error");
            return;
        }
        cleanUp(commentBox);
    }

    private static WebElement getAddQuizAttemptPopupProceedButton() {
        try {
            return CommonResources.browserDriver.findElement(
                    By.cssSelector(CommonResources.cssSelectorAddQuizAttemptPopupProceed));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static void clickAddQuizAttemptPopupProceedButton() throws InterruptedException {
        Thread.sleep(1000);
        getAddQuizAttemptPopupProceedButton().click();
        Thread.sleep(1000);
    }
    private static List<WebElement> getAddQuizAttemptPopupQuizzes() {
        try{
            return CommonResources.browserDriver.findElements(By.cssSelector(
                    CommonResources.cssSelectorAddQuizAttemptPopupQuizzes));
        }
        catch (NoSuchElementException n){
            return null;
        }
    }

    private static void clickFirstQuiz() throws InterruptedException {
        Thread.sleep(1000);
        getAddQuizAttemptPopupQuizzes().get(0).click();
    }

    private static String getFirstQuizText() {
        return getAddQuizAttemptPopupQuizzes().get(0).getText();
    }

    private static void fileUpload(WebElement addFileButton) {
        addFileButton.findElement(By.cssSelector("input")).sendKeys("C:\\Users\\Mike\\Desktop\\Memes\\wednesday.jpg");
    }

    private static String getCommentUploadText(){
        try{
            return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorCommentBoxUploadText)).getText();
        }
        catch (NoSuchElementException n){
            return "";
        }
    }

    private static String getCommentText(WebElement cb){
        return cb.findElement(By.cssSelector(CommonResources.cssSelectorCommentBoxText)).getText();
    }

    private static void enterTextInComment(String s){
        getCommentTextArea().sendKeys(s);
    }

    private static WebElement getCommentTextArea() {
        try {
            return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorTextArea));
        }
        catch (NoSuchElementException n){
            return null;
        }
    }

    private static WebElement getLoadingProgressSaveButton(){
        return CommonResources.browserDriver.findElement(
                By.cssSelector(CommonResources.cssSelectorProgressResourceEditLoadingSaveButton));
    }

    private static WebElement getCommentBoxVideo(WebElement cb) throws InterruptedException {
        Thread.sleep(500);
        try {
            return cb.findElement(By.cssSelector(CommonResources.cssSelectorCommentBoxVideo));
        }
        catch (NoSuchElementException n){
            return null;
        }
    }

    private static WebElement getVideo() throws InterruptedException {
        Thread.sleep(500);
        try {
            return CommonResources.browserDriver.findElements(By.cssSelector(CommonResources.cssSelectorVideo)).get(1);
        }
        catch (NoSuchElementException n){
            return null;
        }
    }
    private static void cleanUp(WebElement cb) throws InterruptedException {
        Actions builder = new Actions(CommonResources.browserDriver);
        builder.moveToElement(cb).perform();

        WebElement remove = getRemoveLink(cb);
        remove.click();
        Thread.sleep(500);
        CommonResources.browserDriver.switchTo().alert().accept();
        if(!getEvidenceRemovedText(cb).isDisplayed()){
            System.out.println("Error");
        }
    }

    private static void clickAddVideoRecordingButton(List<WebElement> buttons) throws InterruptedException {
        Thread.sleep(1000);
        getAddVideoRecordingButton(buttons).click();
    }

    private static void clickStartVideoRecordingButton(List<WebElement> buttons) throws InterruptedException {
        Thread.sleep(1000);
        getStartVideoRecordingButton(buttons).click();
    }

    private static void clickStopVideoRecordingButton(List<WebElement> buttons) throws InterruptedException {
        Thread.sleep(1000);
        getStopVideoRecordingButton(buttons).click();
    }

    private static WebElement getAddQuizAttemptButton(List<WebElement> buttons) {
        try{
            return buttons.get(4);
        }
        catch (IndexOutOfBoundsException i){
            return null;
        }
    }

    private static void clickAddQuizAttemptButton(List<WebElement> buttons) throws InterruptedException {
        Thread.sleep(1000);
        getAddQuizAttemptButton(buttons).click();
    }

    private static WebElement getAddFileButton(List<WebElement> buttons){
        try{
            return buttons.get(0);
        }
        catch (IndexOutOfBoundsException i){
            return null;
        }
    }

    private static void clickAddFileButton(WebElement button) throws InterruptedException{
        Thread.sleep(1000);
        button.click();
    }

    private static WebElement getStopVideoRecordingButton(List<WebElement> buttons) {
        try {
            return buttons.get(7);
        }
        catch (IndexOutOfBoundsException i){
            return null;
        }
    }
    private static WebElement getStartVideoRecordingButton(List<WebElement> buttons) {
        try {
            return buttons.get(6);
        }
        catch(IndexOutOfBoundsException i){
            return null;
        }
    }
    private static WebElement getAddVideoRecordingButton(List<WebElement> buttons) {
        try {
            return buttons.get(5);
        }
        catch (IndexOutOfBoundsException n) {
            return null;
        }
    }
    private static WebElement getEvidenceRemovedText(WebElement cb) {
        try {
            return cb.findElement(By.cssSelector(CommonResources.cssSelectorCommentBoxEvidenceRemovedText));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }

    private static WebElement getRemoveLink(WebElement cb) {
        try{
            return cb.findElement(By.cssSelector(CommonResources.cssSelectorCommentBoxRemove));
        }
        catch (NoSuchElementException n){
            return null;
        }
    }

    private static WebElement getCommentBoxAudioRecording(){
        try {
            return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorCommentBoxAudioRecording));
        }
        catch (NoSuchElementException n){
            return null;
        }
    }
    private static WebElement getCommentBox() throws InterruptedException {
        Thread.sleep(500);
        try {
            return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorCommentBox));
        }
        catch (NoSuchElementException n) {
            return null;
        }
    }
    private static void clickPortfolio() throws InterruptedException {
        Thread.sleep(2000);
        WebElement portfolio = Utility.waitForElementToExistByLinkText("Portfolio");
        portfolio.click();
    }

    private static void clickView() throws InterruptedException {
        Thread.sleep(3000);
        WebElement viewButtonCourse = Utility.waitForElementToExistByLinkText("VIEW");
        viewButtonCourse.click();
    }

    private static WebElement getAudioRecordingBar() {
        try{
            return CommonResources.browserDriver.findElement(
                    By.cssSelector(CommonResources.cssSelectorAudioRecordingBar));
        }
        catch (NoSuchElementException n){
            return null;
        }
    }
    private static List<WebElement> getCanDoStatements() throws InterruptedException{
        Thread.sleep(5000);
        List<WebElement> statements = Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorCanDoStatements);
        return statements;
    }

    private static List<WebElement> getProgressResources() throws InterruptedException {
        Thread.sleep(1000);
        return CommonResources.browserDriver.findElements(
                By.cssSelector(CommonResources.cssSelectorChangeProgressLink));
    }

    private static void clickChangeProgress() throws InterruptedException {
        Thread.sleep(500);
        List<WebElement> progressResources = getProgressResources();
        WebElement changeProgress = progressResources.get(progressResources.size()-1);
        changeProgress.click();
    }

    private static WebElement getProgressSaveButton() throws InterruptedException{
        Thread.sleep(500);
        List<WebElement> progressResources = getProgressResourcesEdit();
        return progressResources.get(progressResources.size()-1).findElement(
                By.cssSelector(CommonResources.cssSelectorProgressResourceEditSaveButton));
    }

    private static void clickProgressSaveButton() throws InterruptedException {
        Thread.sleep(500);
        getProgressSaveButton().click();
    }
    private static void clickInactiveProgressLevel() throws InterruptedException{
        List<WebElement> progressButtons = getProgressResourcesEdit();
        for(WebElement wb: progressButtons) {
            if (!wb.getAttribute("class").contains("active")) {
                wb.click();
                break;
            }
        }
    }

    private static List<WebElement> getProgressResourcesEdit() throws  InterruptedException {
        Thread.sleep(500);
        return CommonResources.browserDriver.findElements(
                By.cssSelector(CommonResources.cssSelectorProgressResourcesEdit));
    }

    private static WebElement getSaveButton() {
        try{
            return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorSaveButton));
        }
        catch (NoSuchElementException n){
            return null;
        }
    }

    private static WebElement getLoadingSaveButton() {
        try {
            return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorLoadingSaveButton));
        }
        catch (NoSuchElementException n){
            return null;
        }
    }

    private static void clickSaveButton() {
        WebElement save = getSaveButton();
        if(save != null){
            save.click();
        }
    }
    private static void clickPostAComment(WebElement button) throws InterruptedException {
        Thread.sleep(1000);
        WebElement postAComment = button.findElement(By.cssSelector(CommonResources.cssSelectorPostAComment));
        postAComment.click();
    }

    private static void clickAddRecording(List<WebElement> allButtons) throws InterruptedException {
        Thread.sleep(500);
        allButtons.get(1).click();
    }

    private static void clickStartRecording(List<WebElement> allButtons) throws InterruptedException {
        Thread.sleep(500);
        allButtons.get(2).click();
    }

    private static void clickStopRecording(List<WebElement> allButtons) throws InterruptedException {
        Thread.sleep(500);
        allButtons.get(3).click();
    }

    private static List<WebElement> getAllCanDoButtons() throws InterruptedException {
        Thread.sleep(500);
        List<WebElement> allCanDoButtons = CommonResources.browserDriver.findElements(
                By.cssSelector(CommonResources.cssSelectorAllCanDoButtons));
        for(WebElement button: allCanDoButtons){
            System.out.println(button.getText());
        }
        return allCanDoButtons;
    }
}
