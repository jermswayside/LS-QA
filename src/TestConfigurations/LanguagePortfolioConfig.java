package TestConfigurations;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.Utility;
import Tests.LanguagePortfolioTests;
import Objects.User;

public class LanguagePortfolioConfig {
    public static void checkViewButton() throws InterruptedException {
        LanguagePortfolioTests.viewButton();
    }

    public static void checkAddQuizAttemptButton() throws InterruptedException {
        LanguagePortfolioTests.addQuizAttemptButton();
    }

    public static void checkAddRecordingButton() throws InterruptedException {
        LanguagePortfolioTests.addRecordingButton();
    }

    public static void checkAddVideoRecordingButton() throws InterruptedException {
        LanguagePortfolioTests.addVideoRecordingButton();
    }

    public static void checkChangeProgressLink() throws InterruptedException {
        LanguagePortfolioTests.changeProgressLink();
    }

    public static void checkAddingComment() throws InterruptedException {
        LanguagePortfolioTests.addingComment();
    }

    public static void checkFileUpload() throws InterruptedException {
        LanguagePortfolioTests.fileUpload();
    }

    public static void checkAllLanguagePortfolioTests() throws InterruptedException {
        User student = new User(CommonResources.usernameStudent, CommonResources.passwordStudent);
        User teacher = new User(CommonResources.usernameTeacher, CommonResources.passwordTeacher);

        Utility.switchUsers(student, CommonResources.browserDriver);
        checkViewButton();
        Utility.switchUsers(teacher, CommonResources.browserDriver);

        Utility.switchUsers(student, CommonResources.browserDriver);
        checkAddQuizAttemptButton();
        Utility.switchUsers(teacher, CommonResources.browserDriver);

        Utility.switchUsers(student, CommonResources.browserDriver);
        checkAddRecordingButton();
        Utility.switchUsers(teacher, CommonResources.browserDriver);

        Utility.switchUsers(student, CommonResources.browserDriver);
        checkAddVideoRecordingButton();
        Utility.switchUsers(teacher, CommonResources.browserDriver);

        Utility.switchUsers(student, CommonResources.browserDriver);
        checkChangeProgressLink();
        Utility.switchUsers(teacher, CommonResources.browserDriver);

        Utility.switchUsers(student, CommonResources.browserDriver);
        checkAddingComment();
        Utility.switchUsers(teacher, CommonResources.browserDriver);

        Utility.switchUsers(student, CommonResources.browserDriver);
        checkFileUpload();
        Utility.switchUsers(teacher, CommonResources.browserDriver);
    }
}
