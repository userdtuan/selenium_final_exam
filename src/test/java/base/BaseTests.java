package base;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BaseTests {
    private WebDriver driver;

    public void testcase_login(String user, String pass){

    }
    public void login(String user, String pass) {
        WebElement UserID = driver.findElement(By.name("uid"));
        WebElement btnLogin = driver.findElement(By.name("btnLogin"));
        WebElement Password = driver.findElement(By.name("password"));

        UserID.sendKeys(user);
        Password.sendKeys(pass);
        btnLogin.click();

        boolean login_result = true;
        WebElement newCustomerBtn = null;
        try {
            newCustomerBtn = driver.findElement(By.xpath("//a[contains(text(), 'New Customer')]"));
        } catch (Exception e) {
//            throw new RuntimeException(e);
            login_result = false;
        }
        if (login_result) {
            System.out.println("dang nhap voi username = " + user + " va password = " + pass + " thanh cong");
            driver.navigate().back();
//            newCustomerBtn.click();
        } else {
            System.out.println("dang nhap voi username = " + user + " va password = " + pass + " that bai");
        }
    }

    public void new_customer(String name, String gender, String dateOfBirth, String address, String city, String state, String pin, String mobile, String email, String pass) {
        login("mngr399176", "mugyten");
//        driver.findElement(By.xpath("//a[contains(text(), 'New Customer')]")).click();
//        WebElement CustomerName = driver.findElement(By.name("name"));
//        WebElement Gender = driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[5]/td[2]/input[2]"));
//        WebElement DateOfBirth = driver.findElement(By.name("dob"));
//        WebElement Address = driver.findElement(By.name("addr"));
//        WebElement City = driver.findElement(By.name("city"));
//        WebElement State = driver.findElement(By.name("state"));
//        WebElement Pin = driver.findElement(By.name("pinno"));
//        WebElement MobileNumber = driver.findElement(By.name("telephoneno"));
//        WebElement Email = driver.findElement(By.name("emailid"));
//        WebElement Password = driver.findElement(By.name("password"));
//
//        CustomerName.sendKeys(name);
//        if (gender.equals("female") || gender.equals("Female")) {
//            Gender.click();
//        }
//        DateOfBirth.sendKeys(dateOfBirth);
//        Address.sendKeys(address);
//        City.sendKeys(city);
//        State.sendKeys(state);
//        Pin.sendKeys(pin);
//        MobileNumber.sendKeys(mobile);
//        Email.sendKeys(email);
//        Password.sendKeys(pass);
    }

    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resource/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.demo.guru99.com/V4/");
//        driver.manage().window().maximize();
//        driver.manage ().window().setSize(new Dimension( 375, 812));
        System.out.println(driver.getTitle());

        login("mngr399176", "mugyten");
        login("mngr39917678878878787", "mkamsda");
        login("mngr399257", "YzyhAha");
        login("mngr39917678878878787", "mkamsda");
        new_customer("name", "female", "06/09/2001", "PhuLoc", "Hue", "Vietname", "111111", "0129238123", "dttuan@mail.com", "123qwe!@#");
        System.out.println("Done");

    }

    public static void main(String[] args) {
        BaseTests test = new BaseTests();
        test.setUp();

        Scanner sc = new Scanner(System.in);
        while (true) {
            String cmd = sc.nextLine();  // Read user input
        }
    }
}