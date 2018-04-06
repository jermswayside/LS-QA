package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProfileSettingsTests {
    public static void editFirstName() throws InterruptedException {
        long startTime = System.nanoTime();
        UINavigation.clickProfile();

        WebElement firstNameInputField = getFirstNameInputField();

        String initialFirstNameValue = firstNameInputField.getAttribute("value");

        String currFirstName = " TestingFirstName ";

        firstNameInputField.clear();
        inputText(firstNameInputField, currFirstName);

        WebElement saveProfileButton = getSaveProfileButton();
        UINavigation.scrollTo(saveProfileButton);
        saveProfileButton.click();

        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        messageBox.click();

        firstNameInputField = getFirstNameInputField();

        String currFirstNameValue = firstNameInputField.getAttribute("value");


        if(!currFirstName.equals(currFirstNameValue)){
            System.out.println("Updated name did not match.");
            return;
        }

        firstNameCleanUp(initialFirstNameValue);
        long endTime = System.nanoTime();
        Utility.nanoToReadableTime(startTime, endTime);
    }

    public static void editLastName() throws InterruptedException {
        long startTime = System.nanoTime();
        UINavigation.clickProfile();
        WebElement lastNameInputField = getLastNameInputField();
        String initialLastNameValue = lastNameInputField.getAttribute("value");
        String currLastName = "TestingLastName";
        lastNameInputField.clear();
        inputText(lastNameInputField, currLastName);
        WebElement saveProfileButton = getSaveProfileButton();
        UINavigation.scrollTo(saveProfileButton);
        saveProfileButton.click();

        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        messageBox.click();

        lastNameInputField = getLastNameInputField();

        String currLastNameValue = lastNameInputField.getAttribute("value");

        if(!currLastNameValue.equals(currLastName)){
            System.out.println("Updated name did not match input.");
            return;
        }
        long endTime = System.nanoTime();
        Utility.nanoToReadableTime(startTime, endTime);
        lastNameCleanUp(initialLastNameValue);
    }

    public static void editPassword() throws InterruptedException {
        long startTime = System.nanoTime();
        UINavigation.clickProfile();

        WebElement oldPasswordInputField = getOldPasswordInputField();
        inputText(oldPasswordInputField, CommonResources.passwordTeacher);

        WebElement newPasswordInputField = getNewPasswordInputField();
        String newPassword = "1234";
        inputText(newPasswordInputField, newPassword);

        WebElement saveProfileButton = getSaveProfileButton();
        UINavigation.scrollTo(saveProfileButton);
        saveProfileButton.click();

        WebElement errorMessage = getErrorMessage();
        Utility.waitForVisible(errorMessage);

        newPasswordInputField.clear();
        newPassword = "12 3456";
        inputText(newPasswordInputField, newPassword);

        UINavigation.scrollTo(saveProfileButton);
        saveProfileButton.click();

        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        messageBox.click();

        oldPasswordInputField = getOldPasswordInputField();
        Utility.waitForVisible(oldPasswordInputField);

        Utility.logout();
        Utility.login(CommonResources.usernameTeacher, newPassword, CommonResources.browserDriver);

        UINavigation.clickProfile();

        long endTime = System.nanoTime();
        Utility.nanoToReadableTime(startTime, endTime);
        passwordCleanUp(newPassword, CommonResources.passwordTeacher);
    }

    public static void editEmail() throws InterruptedException {
        long startTime = System.nanoTime();
        UINavigation.clickProfile();

        WebElement emailInputField = getEmailInputField();
        emailInputField.clear();
        String newEmail = "qateacher@testing.c";
        inputText(emailInputField, newEmail);

        WebElement saveProfileButton = getSaveProfileButton();
        UINavigation.scrollTo(saveProfileButton);
        saveProfileButton.click();

        WebElement errorMessage = getErrorMessage();
        try {
            Utility.waitForVisible(errorMessage);
        }
        catch (TimeoutException t) {
            System.out.println("Validator did not appear for email.");
            return;
        }
        if(!errorMessage.getText().equals("Enter a valid email")){
            System.out.println("Wrong error message appeared for email.");
            return;
        }

        newEmail = "qateacher@testing.com";
        emailInputField.clear();
        inputText(emailInputField, newEmail);

        saveProfileButton.click();

        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        messageBox.click();

        emailInputField = getEmailInputField();
        String emailInEmailInputField = emailInputField.getAttribute("value");
        if(!emailInEmailInputField.equals(newEmail)) {
            System.out.println("Error");
        }
        long endTime = System.nanoTime();
        Utility.nanoToReadableTime(startTime, endTime);
    }

    public static void editTimezone() throws InterruptedException {
        long startTime = System.nanoTime();
        UINavigation.clickProfile();

        WebElement userBrowserTimezoneCheckbox = getUserBrowserTimezoneCheckbox();
        if(!userBrowserTimezoneCheckbox.isEnabled()) {
            userBrowserTimezoneCheckbox.click();
        }

        Select timezoneSelectField = getTimezoneSelectField();

        List<WebElement> timezoneOptions = getTimezonesOptions();


        int randNum = Utility.randomInt(0, timezoneOptions.size()-1);
        WebElement currTimezone = timezoneOptions.get(randNum);
        UINavigation.scrollTo(currTimezone);
        currTimezone.click();

        String currTimezoneName = timezoneSelectField.getFirstSelectedOption().getText();

        WebElement saveProfileButton = getSaveProfileButton();
        saveProfileButton.click();

        WebElement errorMessageBox = Utility.getMessageBox();
        Utility.waitForVisible(errorMessageBox);
        errorMessageBox.click();

        timezoneSelectField = getTimezoneSelectField();
        String newTimezoneName =timezoneSelectField.getFirstSelectedOption().getText();
        if(!newTimezoneName.equals(currTimezoneName)) {
            System.out.println("Error");
        }
        long endTime = System.nanoTime();
        Utility.nanoToReadableTime(startTime, endTime);
    }

    public static void editImage() throws InterruptedException {
        long startTime = System.nanoTime();
        UINavigation.clickProfile();

        Thread.sleep(1000);
        WebElement profileImage = getProfileImage();
        profileImage.click();

        String initialProfileImage = getProfileImageURL();
        WebElement uploadFileButton = getUploadFileButton();
        uploadFile(uploadFileButton, System.getProperty("user.dir") + "\\images\\badger.png");

        WebElement saveProfileButton = getSaveProfileButton();
        UINavigation.scrollTo(saveProfileButton);
        saveProfileButton.click();

        WebElement errorMessageBox = Utility.getMessageBox();
        Utility.waitForVisible(errorMessageBox);
        errorMessageBox.click();

        String newProfileImage = getProfileImageURL();
        if(initialProfileImage.equals(newProfileImage)) {
            System.out.println("Error");
            return;
        }

        long endTime = System.nanoTime();
        Utility.nanoToReadableTime(startTime, endTime);
        imageCleanUp();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void imageCleanUp() throws InterruptedException {
        WebElement profileImage = getProfileImage();
        Utility.waitForVisible(profileImage);
        profileImage.click();

        WebElement revertToRobohashButton = getRevertToRobohashButton();
        revertToRobohashButton.click();

        WebElement successMessageBox = Utility.getMessageBox();
        Utility.waitForVisible(successMessageBox);
        successMessageBox.click();

        Utility.waitForVisible(getProfileImage());
    }

    private static WebElement getRevertToRobohashButton() {
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorRevertToRobohashButton));
    }
    private static String getProfileImageURL() throws InterruptedException {
        return getProfileImage().getAttribute("style");
    }
    private static WebElement getUploadFileButton(){
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorUploadFileButton));
    }

    private static void uploadFile(WebElement input, String fileName) {
        input.sendKeys(fileName);
    }
    private static void passwordCleanUp(String newPassword, String oldPassword) throws InterruptedException{
        WebElement oldPasswordInputField = getOldPasswordInputField();
        inputText(oldPasswordInputField, newPassword);

        WebElement newPasswordInputField = getNewPasswordInputField();
        inputText(newPasswordInputField, oldPassword);

        WebElement saveProfileButton = getSaveProfileButton();
        UINavigation.scrollTo(saveProfileButton);
        saveProfileButton.click();

        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        messageBox.click();
    }

    private static void inputText(WebElement to, String text) {
        to.sendKeys(text);

    }

    private static WebElement getProfileImage() throws  InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorProfileImage);
    }

    private static List<WebElement> getTimezonesOptions() throws InterruptedException {
        return getTimezoneSelectField().getOptions();
    }

    private static WebElement getUserBrowserTimezoneCheckbox() throws InterruptedException {
        return getAllInputFields().get(6).findElement(By.cssSelector(CommonResources.cssSelectorUserBrowserTimezone));
    }
    private static Select getTimezoneSelectField() throws InterruptedException {
        return Utility.waitForSelectElementToExistByCssSelector(CommonResources.cssSelectorTimezoneSelectField);
    }

    private static WebElement getEmailInputField() throws InterruptedException {
        try {
            return getAllInputFields().get(5).findElement(By.cssSelector(CommonResources.cssSelectorInputField));
        }
        catch (IndexOutOfBoundsException i) {
            return null;
        }
    }

    private static WebElement getErrorMessage() throws InterruptedException {
        return Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorPasswordErrorMessage);
    }

    private static WebElement getFirstNameInputField() throws  InterruptedException {
        try {
            return getAllInputFields().get(1).findElement(By.cssSelector(CommonResources.cssSelectorInputField));
        }
        catch(IndexOutOfBoundsException i) {
            return null;
        }
    }

    private static WebElement getLastNameInputField() throws InterruptedException {
        try {
            return getAllInputFields().get(2).findElement(By.cssSelector(CommonResources.cssSelectorInputField));
        }
        catch(IndexOutOfBoundsException i) {
            return null;
        }
    }

    private static WebElement getOldPasswordInputField() throws InterruptedException {
        try {
            return getAllInputFields().get(3).findElement(By.cssSelector(CommonResources.cssSelectorInputField));
        }
        catch(IndexOutOfBoundsException i) {
            return null;
        }
    }

    private static WebElement getNewPasswordInputField() throws InterruptedException {
        try {
            return getAllInputFields().get(4).findElement(By.cssSelector(CommonResources.cssSelectorInputField));
        }
        catch(IndexOutOfBoundsException i) {
            return null;
        }
    }

    private static List<WebElement> getAllInputFields() throws InterruptedException {
        return Utility.waitForElementsToExistByCssSelector(CommonResources.cssSelectorAllInputs);
    }

    private static WebElement getSaveProfileButton() {
        return CommonResources.browserDriver.findElement(By.cssSelector(CommonResources.cssSelectorSaveProfileButton));
    }

    private static void firstNameCleanUp(String name) throws InterruptedException {
        WebElement firstNameInputField = getFirstNameInputField();
        firstNameInputField.clear();
        inputText(firstNameInputField, name);
        WebElement saveProfileButton = getSaveProfileButton();
        UINavigation.scrollTo(saveProfileButton);
        saveProfileButton.click();
        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        messageBox.click();
    }

    private static void lastNameCleanUp(String name) throws InterruptedException {
        WebElement lastNameInputField = getLastNameInputField();
        lastNameInputField.clear();
        inputText(lastNameInputField, name);
        WebElement saveProfileButton = getSaveProfileButton();
        UINavigation.scrollTo(saveProfileButton);
        saveProfileButton.click();
        WebElement messageBox = Utility.getMessageBox();
        Utility.waitForVisible(messageBox);
        messageBox.click();
    }


}
