package TestConfigurations;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import Objects.User;
import Tests.ProfileSettingsTests;

public class ProfileSettingsConfig {
    public static void checkAllProfileSettingsTests() throws InterruptedException{
        User student = new User(CommonResources.usernameStudent, CommonResources.passwordStudent);
        User teacher = new User(CommonResources.usernameTeacher, CommonResources.passwordTeacher);

        checkEditFirstName();
        UINavigation.navToDash();
        UINavigation.clickSkip();

        checkEditLastName();
        UINavigation.navToDash();
        UINavigation.clickSkip();

        checkEditPassword();
        UINavigation.navToDash();
        UINavigation.clickSkip();

        checkEditEmail();
        UINavigation.navToDash();
        UINavigation.clickSkip();

        checkEditTimezone();
        UINavigation.navToDash();
        UINavigation.clickSkip();

        checkEditImage();
        UINavigation.navToDash();
        UINavigation.clickSkip();
    }

    public static void checkEditFirstName() throws InterruptedException {
        ProfileSettingsTests.editFirstName();
    }

    public static void checkEditLastName() throws InterruptedException {
        ProfileSettingsTests.editLastName();
    }

    public static void checkEditPassword() throws InterruptedException {
        ProfileSettingsTests.editPassword();
    }

    public static void checkEditEmail() throws InterruptedException {
        ProfileSettingsTests.editEmail();
    }

    public static void checkEditTimezone() throws InterruptedException {
        ProfileSettingsTests.editTimezone();
    }

    public static void checkEditImage() throws InterruptedException {
        ProfileSettingsTests.editImage();
    }
}
