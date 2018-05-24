package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import Objects.Test;

import java.util.ArrayList;

public class FlashcardTests {
    private static String currCat = CommonResources.getAllCategories().get(9);

    public static void arrowKeysNav() throws InterruptedException {
        Thread.sleep(1000);
        Test currTest = new Test(currCat, "Navigate to Next Card w/ Arrow Keys", "", "");
        long startTime = System.nanoTime();

        WebElement flashcard = getFlashCard();
        WebElement flashcardFront = getFlashcardFront(flashcard);
        String flashcardFrontContent = getFlashcardFrontContent(flashcardFront).getText();

        Actions action = new Actions(CommonResources.browserDriver);

        action.sendKeys(Keys.ARROW_LEFT).build().perform();
        Thread.sleep(1000);
        flashcard = getFlashCard();
        flashcardFront = getFlashcardFront(flashcard);
        String newFlashcardFrontContent = getFlashcardFrontContent(flashcardFront).getText();

        if (flashcardFrontContent.equals(newFlashcardFrontContent)) {
            System.out.println("Flashcard flipping to the left using keys didn't work.");
            if (CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
        }

        action.sendKeys(Keys.ARROW_RIGHT).build().perform();

        Thread.sleep(1000);

        flashcard = getFlashCard();
        flashcardFront = getFlashcardFront(flashcard);
        String oldFlashcardFrontContent = getFlashcardFrontContent(flashcardFront).getText();

        if (!flashcardFrontContent.equals(oldFlashcardFrontContent)) {
            System.out.println("Flashcard flipping to the right using keys didn't work.");
            if (CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
        }

        long endTime = System.nanoTime();
        switch (CommonResources.qaTestMode) {
            case "d":
                Utility.nanoToReadableTime(startTime, endTime);
                break;

            case "n":
                Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
                break;
        }
    }

    public static void arrowKeysFlip() throws InterruptedException {
        Thread.sleep(2000);
        Test currTest = new Test(currCat, "Flip w/ Arrow Keys", "", "");
        long startTime = System.nanoTime();

        Actions action = new Actions(CommonResources.browserDriver);

        action.sendKeys(Keys.ARROW_DOWN).build().perform();
        Thread.sleep(2000);

        WebElement flashcard = getFlashCard();

        Thread.sleep(2000);

        if (flashcard.getAttribute("style").contains("rotateX(0deg)")) {
            System.out.println("Flashcard did not flip.");
            if (CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
            return;
        }

        action.sendKeys(Keys.ARROW_UP).build().perform();
        Thread.sleep(2000);

        flashcard = getFlashCard();
        if (!flashcard.getAttribute("style").contains("rotateX(0deg)")) {
            System.out.println("Flashcard did not flip.");
            if (CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
            return;
        }

        long endTime = System.nanoTime();
        switch (CommonResources.qaTestMode) {
            case "d":
                Utility.nanoToReadableTime(startTime, endTime);
                break;

            case "n":
                Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
                break;
        }
    }

    public static void clickFlip() throws InterruptedException {
        Thread.sleep(2000);
        Test currTest = new Test(currCat, "Flip w/ Clicks", "", "");
        long startTime = System.nanoTime();

        WebElement flashcard = getFlashCard();
        Thread.sleep(1000);
        flashcard.click();


        String flashcardAttr = flashcard.getAttribute("class");
        if (!flashcardAttr.contains("flip")) {
            System.out.println("Flashcard did not flip.");
            if (CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
            return;
        }

        Thread.sleep(1000);

        flashcard = getFlashCard();
        flashcard.click();

        Thread.sleep(1000);

        flashcardAttr = flashcard.getAttribute("class");
        if (flashcardAttr.contains("flip")) {
            System.out.println("Flashcard did not flip back.");
            if (CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
            return;
        }

        long endTime = System.nanoTime();
        switch (CommonResources.qaTestMode) {
            case "d":
                Utility.nanoToReadableTime(startTime, endTime);
                break;

            case "n":
                Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
                break;
        }
    }

    public static void arrowUINav() throws InterruptedException {
        Thread.sleep(2000);
        Test currTest = new Test(currCat, "Flip w/ Clicks", "", "");
        long startTime = System.nanoTime();

        WebElement currFlashcard = getFlashCard();
        WebElement nextBtn = getFlashcardNextBtn();
        WebElement prevBtn = getFlashcardPrevBtn();

        WebElement currFlashcardFront = getFlashcardFront(currFlashcard);
        String flashcardFrontContent = getFlashcardFrontContent(currFlashcardFront).getText();

        nextBtn.click();
        Thread.sleep(1000);

        currFlashcard = getFlashCard();
        currFlashcardFront = getFlashcardFront(currFlashcard);
        String currFlashcardFrontContent = getFlashcardFrontContent(currFlashcardFront).getText();

        if (flashcardFrontContent.equals(currFlashcardFrontContent)) {
            System.out.println("Navigating to the next flashcard through arrow UI didn't work.");
            if (CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
            return;
        }

        prevBtn.click();
        Thread.sleep(1000);

        currFlashcard = getFlashCard();
        currFlashcardFront = getFlashcardFront(currFlashcard);
        currFlashcardFrontContent = getFlashcardFrontContent(currFlashcardFront).getText();

        if (!flashcardFrontContent.equals(currFlashcardFrontContent)) {
            System.out.println("Navigating to previous flashcard through arrow UI didn't work.");
            if (CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
            return;
        }

        long endTime = System.nanoTime();
        switch (CommonResources.qaTestMode) {
            case "d":
                Utility.nanoToReadableTime(startTime, endTime);
                break;

            case "n":
                Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
                break;
        }
    }

    public static void slider() throws InterruptedException {
        Thread.sleep(2000);
        Test currTest = new Test(currCat, "Check slider", "", "");
        long startTime = System.nanoTime();

        if (!getSlider().getText().equals("1")) {
            System.out.println("Slider does not start on 1.");
            if (CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            }
            return;
        }

        if (!slide("forward")) {
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }
        if (!slide("backward")) {
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        long endTime = System.nanoTime();
        switch (CommonResources.qaTestMode) {
            case "d":
                Utility.nanoToReadableTime(startTime, endTime);
                break;

            case "n":
                Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
                break;
        }

    }

    public static void learned() throws InterruptedException {
        Thread.sleep(2000);
        Test currTest = new Test(currCat, "Check \"Learned\" button", "", "");
        long startTime = System.nanoTime();

        if(!learnAll()){
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        if(getFinishedIcon() == null) {
            System.out.println("Finished icon did not appear.");
            Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
            return;
        }

        long endTime = System.nanoTime();
        switch (CommonResources.qaTestMode) {
            case "d":
                Utility.nanoToReadableTime(startTime, endTime);
                break;

            case "n":
                Utility.addTestToTests(currTest, CommonResources.pass, Utility.readableTime(startTime, endTime));
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static WebElement getFlashCard() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorFlashcard);
    }

    private static WebElement getFlashcardFront(WebElement fc) {
        return fc.findElement(By.cssSelector(CommonResources.cssSelectorFlashcardFront));
    }

    private static WebElement getFlashcardBack(WebElement fc) {
        return fc.findElement(By.cssSelector(CommonResources.cssSelectorFlashcardBack));
    }

    private static WebElement getFlashcardFrontContent(WebElement fcf) {
        return fcf.findElement(By.cssSelector(CommonResources.cssSelectorFlashcardFrontContent));
    }

    private static WebElement getFlashcardFrontLearned(WebElement fcf) {
        return fcf.findElement(By.cssSelector(CommonResources.cssSelectorLearned));
    }
    private static WebElement getFlashcardNextBtn() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorFlashcardNextBtn);
    }

    private static WebElement getFlashcardPrevBtn() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorFlashcardPrevBtn);
    }

    private static WebElement getSlider() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorSlider);
    }

    private static WebElement getSliderBar() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorSliderBar);
    }

    private static WebElement getSliderBarInput() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorSliderBarInput);
    }

    private static WebElement getFinishedIcon() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorFinishedIcon);
    }
    private static boolean learnAll() throws InterruptedException {
        WebElement sliderBarInput = getSliderBarInput();
        int maxFlashcards = Integer.parseInt(sliderBarInput.getAttribute("max"))+1;

        ArrayList<String> flashcardContent = new ArrayList<>();
        for(int i = 0; i<maxFlashcards; i++) {
            WebElement flashcard = getFlashCard();
            WebElement flashcardFront = getFlashcardFront(flashcard);
            WebElement flashcardFrontLearned = getFlashcardFrontLearned(flashcardFront);

            String flashcardFrontContent = getFlashcardFrontContent(flashcardFront).getText();

            if (flashcardContent.contains(flashcardFrontContent)) {
                System.out.println("Shown a duplicate flashcard");
                return false;
            }

            flashcardContent.add(flashcardFrontContent);

            flashcardFrontLearned.click();

            Thread.sleep(1000);
        }
        return true;
    }

    private static boolean slide(String direction) throws InterruptedException {
        WebElement sliderBar = getSliderBar();
        WebElement sliderBarInput = getSliderBarInput();

        ArrayList<String> flashcardContent = new ArrayList<>();

        Dimension sliderBarDimensions = sliderBar.getSize();
        int maxSlidingDistance = Integer.parseInt(sliderBarInput.getAttribute("max"));
        int sliderBarWidth = sliderBarDimensions.width;
        WebElement slider = getSlider();
        for (int i = Integer.parseInt(slider.getText());;) {
            WebElement flashcard = getFlashCard();
            WebElement flashcardFront = getFlashcardFront(flashcard);
            String flashcardFrontContent = getFlashcardFrontContent(flashcardFront).getText();

            slider = getSlider();

            if (!slider.getText().equals(Integer.toString(i))) {
                System.out.println("Slider number not displayed correctly.");
                return false;
            }

            if (flashcardContent.contains(flashcardFrontContent)) {
                System.out.println("Shown a duplicate flashcard");
                return false;
            }

            flashcardContent.add(flashcardFrontContent);

            Actions action = new Actions(CommonResources.browserDriver);
            slider = getSlider();
            switch (direction) {
                case "forward":
                    if(i == maxSlidingDistance+1) {
                        return true;
                    }
                    action.clickAndHold(slider)
                            .moveByOffset(sliderBarWidth / (maxSlidingDistance + 1), 0)
                            .release()
                            .build()
                            .perform();
                    i++;

                    break;

                case "backward":
                    if(i == 1) {
                        return true;
                    }
                    action.clickAndHold(slider)
                            .moveByOffset(-(sliderBarWidth / (maxSlidingDistance + 1)), 0)
                            .release()
                            .build()
                            .perform();
                    i--;

                    break;
            }
            Thread.sleep(1000);
        }
    }
}

