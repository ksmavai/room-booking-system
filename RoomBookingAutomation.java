import org.openqa.selenium.By;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RoomBookingAutomation {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "/Users/chrome-driver-location");


        WebDriver driver = new ChromeDriver();

        try {

            driver.get("https://carletonu.libcal.com/");
            System.out.println("Navigated to LibCal homepage.");
            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            WebElement bookStudyRoomsLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.linkText("book study rooms on floors 2-5 in the library")));
            bookStudyRoomsLink.click();
            System.out.println("Clicked on 'book study rooms' link.");

            JavascriptExecutor js = (JavascriptExecutor) driver;

            for (int i = 0; i < 5; i++) {
                js.executeScript("window.scrollBy(0, 1000);");
                Thread.sleep(1000);
                System.out.println("Scrolled down by 1000 pixels.");
            }
            System.out.println("Scrolled to the end of the page.");

            WebElement roomLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@href='/space/26873']")));
            roomLink.click();
            System.out.println("Clicked on room '464 (Capacity 8)' link.");
            Thread.sleep(1000);

            WebElement goToDateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Go To Date')]")));
            goToDateButton.click();
            Thread.sleep(1000);

            LocalDate today = LocalDate.now();
            LocalDate oneWeekFromNow = today.plusDays(7);

            String todayDate = String.valueOf(today.getDayOfMonth());
            String nextWeekDate = String.valueOf(oneWeekFromNow.getDayOfMonth());
            System.out.println("Today's Date: " + todayDate);
            System.out.println("One Week From Now: " + nextWeekDate);

            List<WebElement> allDates = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//td[contains(@class, 'day')]")));

            WebElement todayElement = null;
            WebElement nextWeekElement = null;

            for (WebElement date : allDates) {
                String dateText = date.getText();
                String dateClass = date.getAttribute("class");

                if (dateText.equals(todayDate) && dateClass.contains("today day")) {
                    todayElement = date;
                    System.out.println("Located today's element: " + dateText);
                } else if (dateText.equals(nextWeekDate) && !dateClass.contains("old")) {
                    nextWeekElement = date;
                    System.out.println("Located next week's element: " + dateText);
                }
            }

            if (todayElement == null || nextWeekElement == null) {
                throw new Exception("Could not locate one or both dates on the calendar.");
            }

            nextWeekElement.click();
            System.out.println("Clicked on next week's date.");
            Thread.sleep(1000);

            WebElement timeSlot = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@aria-label, '11:30am')]")));
            timeSlot.click();
            System.out.println("Clicked on the 11:30 AM timeslot.");
            Thread.sleep(1000);

            WebElement dropdownElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.tagName("select")));
            System.out.println("Located dropdown.");

            Select dropdown = new Select(dropdownElement);
            int totalOptions = dropdown.getOptions().size();
            dropdown.selectByIndex(totalOptions - 1);
            System.out.println("Selected last option in dropdown.");
            Thread.sleep(1000);

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("submit_times")));
            submitButton.click();
            System.out.println("Clicked on 'Submit Times' button.");
            Thread.sleep(2000);

            WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("userNameInput")));
            usernameField.sendKeys("username"); //Didn't input actual username for obvious reasons
            System.out.println("Entered username.");

            // Locating password field and inputting password
            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("passwordInput")));
            passwordField.sendKeys("password"); //Didn't input actual password for obvious reasons
            System.out.println("Entered password.");

            WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("submitButton")));
            signInButton.click();
            System.out.println("Clicked on 'Sign In' button.");
            Thread.sleep(2000);

            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Continue')]")));
            continueButton.click();
            System.out.println("Clicked on the 'Continue' button");

            WebElement submitBookingButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Submit my Booking')]")));
            submitBookingButton.click();
            System.out.println("Booking has been submitted!");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            driver.quit();
        }

    }
}
