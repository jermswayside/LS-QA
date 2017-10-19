package HelpersAndUtilities;

import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommonResources {
    //Usernames and passwords
    public static final String usernameStudent = "qastudent";
    public static final String passwordStudent = "1234";

    public static final String usernameTeacher = "qateacher";
    public static final String passwordTeacher = "1234";

    public static final String usernameFail = "helloworld";
    public static final String passwordFail = "helloworld";

    public static final String usernameStudent1 = "qastudent1";
    public static final String passwordStudent1 = "1234";

    //Browser driver placeholder
    public static WebDriver browserDriver = null;

    //chromedriver.exe and geckodriver.exe file paths
    public static final String chromeDriver = "webdriver.chrome.driver";
    public static final String geckoDriver = "webdriver.gecko.driver";
    public static final String pathFFDriver = "C:\\Users\\Mike\\IdeaProjects\\Test\\drivers\\geckodriver.exe";
    public static final String pathChromeDriver = "C:\\Users\\Mike\\IdeaProjects\\Test\\drivers\\chromedriver.exe";

    //URL
    public static String urlDevLS = "https://learningsite.waysidepublishing.com/";

    //Login Page CSS classes and selectors
    public static String cssClassButtonLogin = "Login_Form";
    public static String cssSelectorUsername = "input.ws-input-1:nth-child(1)";
    public static String cssSelectorPassword = "input.ws-input-1:nth-child(2)";

    //Dashboard classes and selectors
    public static String cssSelectorPopupSkip = "div.ws-popup-wrapper__footer > div.skip-b > div.icon-link.Btn_Skip";
    public static String cssSelectorNewCourse = ".Btn_Textbooks";
    public static String cssSelectorDashboardCourses = "#Dashboard_Courses";
    public static String cssSelectorViewCourse = "#Dashboard_Courses > div > a.ws-courses-item-title.app-navigation";
    public static String cssLinkTextLogout = "Logout";
    public static String cssLinkTextCourseName = "asd";

    //New Course classes, selectors, xpath, and variables
    public static String cssSelectorCourseContent = ".Content";
    public static String cssSelectorCreateCourse = ".Content > div:nth-child(1) > div:nth-child(5) > div:nth-child(1)";
    public static String cssSelectorCreateCoursePopUp = "button.ws-button__positive";
    public static String cssSelectorMessageBox = "#Message_Box > div:nth-child(1)";
    public static String cssSelectorCourseTitle = ".content-b > div:nth-child(1) > input:nth-child(2)";
    public static String cssXpathStartDate = "//input[@placeholder='Start date']";
    public static String courseTitle = "Hello World";
    public static String courseCreationDupTitleMsg = "Another teacher has already used this course name. Please choose a different course name";
    public static String cssSelectorCurrDay = "table.ui-datepicker-calendar > tbody > tr > td.ui-datepicker-today";
    public static String cssSelectorTermsBox = ".content-b > p:nth-child(3) > input:nth-child(2)";
    public static String cssSelectorCourse = "#Dashboard_Courses > div:nth-child(1) > div:nth-child(6) > a:nth-child(1)";
    public static String cssSelectorCourseStudent = "#Dashboard_Courses > div > div.ws-courses-item-bottom > a";

    //Current course classes, selectors, xpath, and variables
    public static String cssSelectorCogWheel = "//*[@id=\"CourseContent\"]/div[2]/div[1]/div/div[1]/div/a[2]";
    public static String cssXpathAssignmentTab = "//*[@id=\"CourseContent\"]/div[2]/div[1]/div/div[2]/a[3]";
    public static String cssSelectorTrashCan = "div.col.buttons-b > div.ws-btn-2.Btn_Edit_Mode";
    public static String cssSelectorCheckAllBox = "div.checkbox-b";
    public static String cssLinkedTextRemove = "Remove selected";
    public static String cssSelectorRemoveConfirm = "div.Btn_Popup_Confirm";

    //Assignment classes, selectors, xpath, and variables
    public static String cssSelectorAddAssignment = ".Btn_Add_Assignment";
    public static ArrayList<String> icons = new ArrayList(){{
        add("item-icon fa fa-file-text-o");
        add("item-icon fa fa-file-audio-o");
        add("item-icon wsi-icon-quiz");
        add("item-icon fa fa-file-video-o");
        add("item-icon wsi-icon-flashcard");
        add("item-icon fa fa-file-code-o");
        add("item-icon fa fa-comments-o");
    }};
    public static String cssSelectorFolders = "div.course-content-tree > ul.jqtree-tree > li.jqtree-folder";
    public static String cssSelectorFoldersCloser = "div.course-content-tree > ul.jqtree-tree > li.jqtree-folder > div.jqtree_common";
    public static String cssSelectorFoldersLvl2 = "div.course-content-tree > ul.jqtree-tree > li.jqtree-folder > ul.jqtree_common > li.jqtree-folder";
    public static String cssSelectorFoldersLvl2Closer = "div.course-content-tree > ul.jqtree-tree > li.jqtree-folder > ul.jqtree_common > li.jqtree-folder > div.jqtree_common";
    public static String cssSelectorX = "div.ws-popup-btn-close:nth-child(2)";
    public static String cssSelectorFoldersLvl3 = "div.course-content-tree > ul.jqtree-tree > li.jqtree-folder > ul.jqtree_common > li.jqtree-folder > ul.jqtree_common > li.jqtree_common";
    public static String cssSelectorIcons = "div.course-content-tree > ul.jqtree-tree > li.jqtree-folder > ul.jqtree_common > li.jqtree-folder > ul.jqtree_common > li.jqtree_common > div.jqtree_common > span.jqtree_common > i.item-icon";
    public static String cssSelectorDropContainer = "div.Selected_Assignments";
    public static String cssSelectorNextStep = "div.bottom-b > div.Btn_Next_Step";
    public static String cssSelectorNextNextStep = "div.step-b.active > div.bottom-b >  div.Btn_Next_Step";
    public static String cssSelectorEditNextStep = "#Popup_Create_Assignment > div.ws-assignment-popup-wrapper.Popup_Body > div.ws-popup-content > div.step-b.active > div.bottom-b > div.Btn_Next_Step.ws-button__positive";
    public static String cssSelectorAssign = "div.step-b.active > div.bottom-b > div.Btn_Assign";
    public static String cssXpathChooseDate = "//input[@placeholder='Choose date']";
    public static String cssSelectorAssignments = "div.Assignments > div.ws-table-wrapper.ws-assignments-table >" +
            " table.ws-table > tbody > tr.ws-table-row";

    public static String cssSelectorAssignmentTitle = "td.title > div > a";
    public static String cssSelectorStudentAssignmentTitle = "td > a";
    public static String cssSelectorStudentCheckBox = "#Popup_Create_Assignment >" +
            " div.ws-assignment-popup-wrapper.Popup_Body > div.ws-popup-content > div.step-b.active >" +
            " div.left-b.full-width > div.content-b > div > div:nth-child(3) > div.ws-user-index > span";

    public static String cssSelectorEdit = "#CourseAssignment > div.ws-page >" +
            " div.ws-page-content.ws-layout-wrapper >" +
            " div.Assignments > div > table > tbody > tr.ws-table-row > td:nth-child(6) >" +
            " a.icon-link.Btn_Edit_Assignment";

    public static String cssSelectorAssignees = "#CourseAssignment > div.ws-page >" +
            " div.ws-page-content.ws-layout-wrapper > div.Assignments >" +
            " div > table > tbody > tr.ws-table-row > td:nth-child(5) > a";

    public static String cssSelectorSave = "#Popup_Create_Assignment > div.ws-assignment-popup-wrapper.Popup_Body >" +
            " div.ws-popup-content > div.step-b.active > div.bottom-b >" +
            " div.Btn_Save_Assignment.ws-button__positive > div";

    public static String cssSelectorAllAssigned = "#Popup_Content > div.ws-content-popup-wrapper.Popup_Body >" +
            " div.ws-popup-content > div.top-b > div.content-b.Popup_Content > div > div.item-b";

    public static String cssSelectorClose = "#Popup_Content > div.ws-content-popup-wrapper.Popup_Body >" +
            " div.ws-popup-content > div.bottom-b > div.Btn_Close.ws-button__negative";

    public static ArrayList<String> assignedAssignments = new ArrayList<>();
    public static String cssSelectorArchive = "#CourseAssignment > div.ws-page >" +
            " div.ws-page-content.ws-layout-wrapper > div.Assignments > div > table > tbody > tr.ws-table-row >" +
            " td:nth-child(6) > a.icon-link.Btn_Archive_Assignment";

    public static String cssSelectorArchiveYes = "div:nth-child(13) > div.ws-popup-wrapper >" +
            " div.ws-popup-wrapper__footer > div.Btn_Popup_Confirm.ws-button__positive > div";

    public static String cssSelectorArchiveMessageBox = "div.visible.success";
    public static String archiveSuccessMessage = "Assignment archived";
    public static String cssSelectorShowArchived = "#CourseAssignment > div.ws-page >" +
            " div.ws-page-content.ws-layout-wrapper > div:nth-child(5) > a";

    public static String cssSelectorArchivedAssignments = "#CourseAssignment > div.ws-page >" +
            " div.ws-page-content.ws-layout-wrapper > div.Assignments > div >" +
            " table > tbody > tr.ws-table-row.assignment-archived";
}
