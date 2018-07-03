/**
 * Created by Tatiana on 03.07.2018.
 */

import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.junit.*;
import java.io.IOException;
import org.openqa.selenium.NoSuchElementException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;


public class TestNOVA {

    private static WebDriver driver;

    @BeforeClass
    public static void createDriver() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:/server/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public static void quitDriver() {
        driver.quit();
    }

    @Test
    public void testGoogle() throws Exception {

        String page = "https://google.com";
        String pageSearch = "https://lenta.ru/";

        driver.get(page);
        availabilityOfPage(page);

        fillClick("id","lst-ib", "сайт lenta.ru");
        submitFormID("tsf");

        fillClick("xpath","//a[@href='" + pageSearch + "']", "");
        availabilityOfPage(pageSearch);

        Assert.assertEquals("Title does not match the expected.", "Lenta.ru - Новости России и мира сегодня", driver.getTitle());
    }

    @Test
    public void testLeonbets() throws Exception {

        String page = "https://leonbets.com";

        driver.get(page);
        availabilityOfPage(page);

        fillClick("xpath","//span[text()='English']", "");
        fillClick("xpath","//span[text()='Русский']", "");
        fillClick("linkText","Регистрация", "");
        fillClick("name","fname", "Вася");
        fillClick("name","lname", "Пупкинандзе");
        fillClick("name","b_day", "1");
        fillClick("name","b_month", "апреля");
        fillClick("name","b_year", "1925");
        fillClick("name","email", "vasya.pupkin66@bk.ru");
        fillClick("name","rpassword", "Vasya_1232");
        fillClick("name","cpassword", "Vasya_1232");
        fillClick("name","country", "Бермудские острова");
        fillClick("name","city", "Девоншир");
        fillClick("name","address", "Площадь Свободы, 25-17");
        fillClick("name","phone", "+3712000000");
        fillClick("name","currency", "USD");
        fillClick("name","terms", "");
        submitFormID("regButton");

        Assert.assertTrue("Registration is not successful.", driver.findElement(By.tagName("body")).getText().contains("Создание Вашего счета успешно завершено."));
    }


    private void  availabilityOfPage(String page) throws IOException {
        URL url = new URL(page);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        int responseCode = http.getResponseCode();
        Assert.assertEquals("Page is not available, code of response: " + responseCode, 200, responseCode);
    }

    private void fillClick(String mode, String parameter, String value) throws Exception {
        try {
            Method method = By.class.getMethod(mode, String.class);
            WebElement element = driver.findElement((By) method.invoke(null, parameter));

            if (!value.isEmpty()) {
                element.sendKeys(value);
            } else {
                element.click();
            }
        } catch (NoSuchElementException e) {
            System.out.print("WebElement " + mode + parameter + "/ was not found." );
            throw e;
        } catch (Exception e) {
            System.out.print("Cannot fill WebElement " + mode  + parameter);
            throw e;
        }
    }

    private void submitFormID(String parameter) throws Exception {
        try {
            driver.findElement(By.id(parameter)).submit();

        } catch (NoSuchElementException e) {
            System.out.print("WebElement with ID " + parameter + " was not found");
            throw e;
        } catch (Exception e) {
            System.out.print("Cannot submit WebElement with ID " + parameter);
            throw e;
        }
    }
}