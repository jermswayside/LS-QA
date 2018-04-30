package Tests;

import HelpersAndUtilities.CommonResources;
import HelpersAndUtilities.Utility;
import Objects.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class BookCountTests {
    private static Test currTest = new Test(CommonResources.getAllCategories().get(1), "Check Book Count", "", "");

    public static void checkBookCount() throws InterruptedException{
            long start = System.nanoTime();
            System.out.println("Checking course book count...");

            WebElement newCourse = Utility.waitForElementToExistByCssSelector(CommonResources.cssSelectorNewCourse);
            Utility.waitForClickable(newCourse);
            newCourse.click();
            System.out.println("Clicked \"New Course\".");
            Thread.sleep(2000);

            WebElement content = CommonResources.browserDriver.findElement(
                    By.cssSelector(CommonResources.cssSelectorCourseContent));
            int booksCount = content.findElements(By.xpath("*")).size();
            System.out.println("Finding books count...");
            System.out.println(String.format("Books found: %d", booksCount));

            if (booksCount < 3){
                System.out.println("There aren't enough books loaded.  Contact the administrator.");
                if(CommonResources.qaTestMode.equals("n")){
                    Utility.addTestToTests(currTest, CommonResources.fail, CommonResources.na);
                }
                return;
            }
            long end = System.nanoTime();
            if(CommonResources.qaTestMode.equals("n")) {
                String time = Utility.readableTime(start, end);
                Utility.addTestToTests(currTest, CommonResources.pass, time);
            }
            else if(CommonResources.qaTestMode.equals("d")) {
                Utility.nanoToReadableTime(start, end);
            }
    }
}
