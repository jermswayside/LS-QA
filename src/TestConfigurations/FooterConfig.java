package TestConfigurations;

import HelpersAndUtilities.UINavigation;
import Tests.FooterTests;

public class FooterConfig {
    public static void checkContactUs() throws InterruptedException {
        FooterTests.contactUs();
    }

    public static void checkFAQ() throws InterruptedException {
        FooterTests.FAQ();
    }

    public static void checkStore() throws InterruptedException {
        FooterTests.store();
    }

    public static void checkSamples() throws InterruptedException {
        FooterTests.samples();
    }

    public static void checkVisitWayside() throws InterruptedException {
        FooterTests.visitWayside();
    }

    public static void checkPrivacyPolicy() throws InterruptedException {
        FooterTests.privacyPolicy();
    }

    public static void checkToC() throws InterruptedException {
        FooterTests.ToC();
    }

    public static void checkReleaseNotes() throws InterruptedException {
        FooterTests.releaseNotes();
    }

    public static void checkAllFooterTests() throws InterruptedException {
        FooterTests.contactUs();
        FooterTests.FAQ();
        FooterTests.store();

        FooterTests.samples();
        UINavigation.navToDash();

        FooterTests.visitWayside();
        FooterTests.privacyPolicy();
        FooterTests.ToC();
        FooterTests.releaseNotes();

        UINavigation.navToDash();
    }
}
