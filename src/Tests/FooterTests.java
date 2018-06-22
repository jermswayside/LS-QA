package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.WebElement;
import Objects.Test;

import java.util.ArrayList;

public class FooterTests {
    private static String currCat = CommonResources.getAllCategories().get(10);

    public static void contactUs() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Contact Us", "", "");
        long startTime = System.nanoTime();

        WebElement contactUs = Utility.waitForElementToExistByLinkText("Contact Us");

        String mailTo = "mailto:support@waysidepublishing.com";
        if(!contactUs.getAttribute("href").equals(mailTo)){
            System.out.println("Contact Us link incorrect.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(startTime, endTime);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    public static void FAQ() throws InterruptedException {
        Test currTest = new Test(currCat, "Check FAQ", "", "");
        long startTime = System.nanoTime();
        WebElement FAQ = Utility.waitForElementToExistByLinkText("FAQ");

        UINavigation.scrollTo(FAQ);

        FAQ.click();

        Thread.sleep(1000);

        ArrayList<String> tabs = new ArrayList<>(CommonResources.browserDriver.getWindowHandles());

        if(tabs.size() < 2) {
            System.out.println("FAQ didn't open a new tab.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        CommonResources.browserDriver.switchTo().window(tabs.get(1));

        String FAQURL = "";

        switch (CommonResources.siteChoiceEntry) {
            case 1: FAQURL = "https://stagelearningsite.waysidepublishing.com/frequently-asked-questions";
            break;

            case 2: FAQURL = "https://learningsite.waysidepublishing.com/frequently-asked-questions";
            break;
        }

        if(!CommonResources.browserDriver.getCurrentUrl().equals(FAQURL)){
            System.out.println("Link to FAQ is wrong.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        CommonResources.browserDriver.close();
        CommonResources.browserDriver.switchTo().window(tabs.get(0));

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(startTime, endTime);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    public static void store() throws InterruptedException {
        long startTime = System.nanoTime();
        Test currTest = new Test(currCat, "Check Store", "", "");
        WebElement store = Utility.waitForElementToExistByLinkText("Store");

        UINavigation.scrollTo(store);

        store.click();

        Thread.sleep(1000);

        ArrayList<String> tabs = new ArrayList<>(CommonResources.browserDriver.getWindowHandles());
        if(tabs.size() < 2) {
            System.out.println("Store didn't open a new tab.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }


        CommonResources.browserDriver.switchTo().window(tabs.get(1));

        String storeURL = "https://shop.waysidepublishing.com/";
        if(!CommonResources.browserDriver.getCurrentUrl().equals(storeURL)){
            System.out.println("Link to Store is wrong.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        CommonResources.browserDriver.close();
        CommonResources.browserDriver.switchTo().window(tabs.get(0));

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(startTime, endTime);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    public static void samples() throws InterruptedException {
        long startTime = System.nanoTime();
        Test currTest = new Test(currCat, "Check Samples", "", "");
        WebElement samples = Utility.waitForElementToExistByLinkText("Samples");

        UINavigation.scrollTo(samples);

        samples.click();

        Thread.sleep(1000);

        String samplesURL = "";
        switch (CommonResources.siteChoiceEntry) {
            case 1: samplesURL = "https://stagelearningsite.waysidepublishing.com/samples";
            break;

            case 2: samplesURL = "https://learningsite.waysidepublishing.com/samples";
        }

        if(!CommonResources.browserDriver.getCurrentUrl().equals(samplesURL)){
            System.out.println("Link to Samples is wrong.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(startTime, endTime);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    public static void visitWayside() throws InterruptedException {
        long startTime = System.nanoTime();
        Test currTest = new Test(currCat, "Check Store", "", "");
        WebElement compWebsite = Utility.waitForElementToExistByLinkText("Visit Wayside");

        UINavigation.scrollTo(compWebsite);

        compWebsite.click();

        Thread.sleep(1000);

        ArrayList<String> tabs = new ArrayList<>(CommonResources.browserDriver.getWindowHandles());
        if(tabs.size() < 2) {
            System.out.println("Store didn't open a new tab.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        CommonResources.browserDriver.switchTo().window(tabs.get(1));

        String compSiteURL = "https://waysidepublishing.com/";
        if(!CommonResources.browserDriver.getCurrentUrl().equals(compSiteURL)){
            System.out.println("Link to Wayside is wrong.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        CommonResources.browserDriver.close();
        CommonResources.browserDriver.switchTo().window(tabs.get(0));

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(startTime, endTime);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    public static void privacyPolicy() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Privacy Policy", "", "");
        long startTime = System.nanoTime();
        WebElement privacyPolicy = Utility.waitForElementToExistByLinkText("Privacy Policy");

        UINavigation.scrollTo(privacyPolicy);

        privacyPolicy.click();

        Thread.sleep(1000);

        ArrayList<String> tabs = new ArrayList<>(CommonResources.browserDriver.getWindowHandles());
        if(tabs.size() < 2) {
            System.out.println("Privacy Policy didn't open a new tab.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        CommonResources.browserDriver.switchTo().window(tabs.get(1));

        String privacyPolicyURL = "";

        switch(CommonResources.siteChoiceEntry) {
            case 1: privacyPolicyURL = "https://stagelearningsite.waysidepublishing.com/privacy-policy";
            break;

            case 2: privacyPolicyURL = "https://learningsite.waysidepublishing.com/privacy-policy";
        }

        if(!CommonResources.browserDriver.getCurrentUrl().equals(privacyPolicyURL)){
            System.out.println("Link to Wayside is wrong.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        CommonResources.browserDriver.close();
        CommonResources.browserDriver.switchTo().window(tabs.get(0));

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(startTime, endTime);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    public static void ToC() throws InterruptedException {
        Test currTest = new Test(currCat, "Check ToC", "", "");
        long startTime = System.nanoTime();
        WebElement termsAndConditions = Utility.waitForElementToExistByLinkText("Terms and Conditions");

        UINavigation.scrollTo(termsAndConditions);

        termsAndConditions.click();

        Thread.sleep(1000);

        ArrayList<String> tabs = new ArrayList<>(CommonResources.browserDriver.getWindowHandles());
        if(tabs.size() < 2) {
            System.out.println("Terms and Conditions didn't open a new tab.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        CommonResources.browserDriver.switchTo().window(tabs.get(1));

        String ToCURL = "";

        switch(CommonResources.siteChoiceEntry) {
            case 1: ToCURL = "https://stagelearningsite.waysidepublishing.com/terms-full";
            break;

            case 2: ToCURL = "https://learningsite.waysidepublishing.com/terms-full";
            break;
        }

        if(!CommonResources.browserDriver.getCurrentUrl().equals(ToCURL)){
            System.out.println("Link to Terms and Conditions is wrong.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        CommonResources.browserDriver.close();
        CommonResources.browserDriver.switchTo().window(tabs.get(0));

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(startTime, endTime);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }

    public static void releaseNotes() throws InterruptedException {
        Test currTest = new Test(currCat, "Check Release Notes", "", "");
        long startTime = System.nanoTime();
        WebElement releaseNotes = Utility.waitForElementToExistByLinkText("Release Notes");

        UINavigation.scrollTo(releaseNotes);

        releaseNotes.click();

        Thread.sleep(1000);

        ArrayList<String> tabs = new ArrayList<>(CommonResources.browserDriver.getWindowHandles());
        if(tabs.size() < 2) {
            System.out.println("Release Notes didn't open a new tab.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        CommonResources.browserDriver.switchTo().window(tabs.get(1));

        String releaseNotesURL = "";

        switch(CommonResources.siteChoiceEntry) {
            case 1: releaseNotesURL = "https://stagelearningsite.waysidepublishing.com/release-notes";
            break;

            case 2: releaseNotesURL = "https://learningsite.waysidepublishing.com/release-notes";
        }

        if(!CommonResources.browserDriver.getCurrentUrl().equals(releaseNotesURL)){
            System.out.println("Link to Release Notes is wrong.");
            if(CommonResources.qaTestMode.equals("n")) {
                Utility.addTestToTests(currTest, CommonResources.pass, CommonResources.na);
            }
            return;
        }

        CommonResources.browserDriver.close();
        CommonResources.browserDriver.switchTo().window(tabs.get(0));

        long endTime = System.nanoTime();
        if(CommonResources.qaTestMode.equals("d")) {
            Utility.nanoToReadableTime(startTime, endTime);
        }
        else if(CommonResources.qaTestMode.equals("n")){
            String time = Utility.readableTime(startTime, endTime);
            Utility.addTestToTests(currTest, CommonResources.pass, time);
        }
    }
}
