import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.Utility;
import TestConfigurations.*;

import java.io.IOException;


public class qaTestNormal {
    public static void run() throws InterruptedException, IOException {

        CreateCourseConfig.checkAllCreateCourseTests();

        BookCountConfig.checkAllBookCountTests();

        AssignmentsConfig.checkAllAssignmentsTests();

        FlexTextConfig.checkAllFlexTextTests();

        ContentManagerConfig.checkAllContentManagerTests();

        LanguagePortfolioConfig.checkAllLanguagePortfolioTests();

        ProfileSettingsConfig.checkAllProfileSettingsTests();

        CourseSettingsConfig.checkAllCourseSettingsTests();

        QuizConfig.checkAllQuizTests();

        FlashcardConfig.checkAllFlashcardTests();

        Utility.printPretty();

        CommonResources.browserDriver.close();
    }
}
