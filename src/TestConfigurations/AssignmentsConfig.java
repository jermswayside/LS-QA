package TestConfigurations;

import HelpersAndUtilities.UINavigation;
import HelpersAndUtilities.Utility;
import Tests.AssignmentTests;

public class AssignmentsConfig {
    public static void checkAllAssignmentsTests() throws InterruptedException {
        checkSelectAllAssignments();
        UINavigation.navToDash();

        checkSelectEachAssignments();
        UINavigation.navToDash();

        checkConfirmAssignments();
        UINavigation.navToDash();

        checkDeleteAssignments();
        UINavigation.navToDash();

        checkEditAssignments();
        UINavigation.navToDash();

        checkArchiveAssignments();
        UINavigation.navToDash();
    }

    public static void checkArchiveAssignments() throws InterruptedException {
        AssignmentTests.archiveAssignment();
        Utility.simpleDeleteAssignments();
    }

    public static void checkEditAssignments() throws InterruptedException {
        AssignmentTests.editAssignments();
        Utility.simpleDeleteAssignments();
    }

    public static void checkDeleteAssignments() throws InterruptedException {
        AssignmentTests.deleteAssignments();
    }

    public static void checkConfirmAssignments() throws InterruptedException {
        AssignmentTests.confirmAssignments();
        UINavigation.navToAssignment();
        Utility.simpleDeleteAssignments();
    }

    public static void checkSelectAllAssignments() throws InterruptedException {
        AssignmentTests.selectAllAssignments();
        Utility.simpleDeleteAssignments();
    }

    public static void checkSelectEachAssignments() throws InterruptedException {
        AssignmentTests.selectEachAssignments();
        Utility.simpleDeleteAssignments();
    }
}
