package base;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BaseTests {
    public static WebDriver driver;

    public void testcase_login(String user, String pass) {
        login(user, pass);
        boolean login_result = true;
        WebElement newCustomerBtn = null;
        try {
            newCustomerBtn = driver.findElement(By.xpath("//a[contains(text(), 'New Customer')]"));
        } catch (Exception e) {
//            throw new RuntimeException(e);
            login_result = false;
        }
        System.out.println(login_result+"\t - testcase Login, username = " + user + " and password = " + pass);
        if (login_result) {
            driver.navigate().back();
        }
    }

    public void login(String user, String pass) {
        WebElement UserID = driver.findElement(By.name("uid"));
        WebElement btnLogin = driver.findElement(By.name("btnLogin"));
        WebElement Password = driver.findElement(By.name("password"));

        UserID.sendKeys(user);
        Password.sendKeys(pass);
        btnLogin.click();
    }

    public String new_customer(String name, String gender, String dateOfBirth, String address, String city, String state, String pin, String mobile, String email, String pass) {

        String customerID = null;
        WebElement newCustomerBtn = null;
        try {
            newCustomerBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[2]/a"));
        } catch (Exception e) {
            login("mngr399176", "mugyten");
            newCustomerBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[2]/a"));
        }
        newCustomerBtn.click();

        WebElement CustomerName = driver.findElement(By.name("name"));
        WebElement Gender = driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[5]/td[2]/input[2]"));
        WebElement DateOfBirth = driver.findElement(By.name("dob"));
        WebElement Address = driver.findElement(By.name("addr"));
        WebElement City = driver.findElement(By.name("city"));
        WebElement State = driver.findElement(By.name("state"));
        WebElement Pin = driver.findElement(By.name("pinno"));
        WebElement MobileNumber = driver.findElement(By.name("telephoneno"));
        WebElement Email = driver.findElement(By.name("emailid"));
        WebElement Password = driver.findElement(By.name("password"));

        CustomerName.sendKeys(name);
        if (gender.equals("female") || gender.equals("Female")) {
            Gender.click();
        }
        DateOfBirth.sendKeys(dateOfBirth);
        Address.sendKeys(address);
        City.sendKeys(city);
        State.sendKeys(state);
        Pin.sendKeys(pin);
        MobileNumber.sendKeys(mobile);
        Email.sendKeys(email);
        Password.sendKeys(pass);

        driver.findElement(By.name("sub")).click();
        Boolean success = true;
        try {
            WebElement hintNext = driver.findElement(By.className("heading3"));
            success = true;
            customerID = driver.findElement(By.xpath("//*[@id=\"customer\"]/tbody/tr[4]/td[2]")).getAttribute("innerHTML");
//            System.out.println(customerID);
        } catch (Exception e) {
            success = false;
        }
        return customerID;
    }
    public void testcase_newCustomer(String name, String gender, String dateOfBirth, String address, String city, String state, String pin, String mobile, String email, String pass){
        String customerID = new_customer(name,gender,dateOfBirth,address,city,state,pin,mobile,email,pass);
        boolean success = true;
        if(customerID==null){
            success = false;
        }
        System.out.println(success + "\t - testcase New customer, CustomerID: " + customerID);
        if (success) {
            driver.findElement(By.xpath("//*[@id=\"customer\"]/tbody/tr[14]/td/a")).click();

        }
    }

    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resource/chromedriver");
        ChromeOptions options = new ChromeOptions();

        options.addExtensions(new File("resource/AdGuard.crx"));

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
        driver.get("https://www.demo.guru99.com/V4/");
//        driver.manage().window().maximize();
//        driver.manage ().window().setSize(new Dimension( 375, 812));
        System.out.println(driver.getTitle());
    }

    public static void main(String[] args) {
        BaseTests test = new BaseTests();
        test.setUp();
        Random random = new Random();
//        login("mngr399176", "mugyten");
        test.testcase_login("mngr39917678878878787", "mkamsda");
        test.testcase_login("mngr399257", "YzyhAha");
        test.testcase_login("mngr39917678878878787", "mkamsda");
        System.out.println("------------------");
        test.testcase_newCustomer("name", "female", "06/09/2001", "PhuLoc", "Hue", "Vietname", "111111", "0129238123", "oalllss6@p00o.com", "123qwe!@#");
        test.testcase_newCustomer("name", "female", "06/09/2001", "PhuLoc", "Hue", "Vietname", "111111", "0129238123", "e"+String.valueOf(random.nextInt(999))+"@gmail.com", "123qwe!@#");
        test.testcase_newCustomer("name", "female", "06/09/2001", "PhuLoc", "Hue", "Vietname", "111111", "0129238123", "ppooo@p00o.com", "123qwe!@#");
        test.testcase_newCustomer("name", "female", "06/09/2001", "PhuLoc", "Hue", "Vietname", "111111", "0129238123", "oalllss6@p00o.com", "123qwe!@#");
        test.testcase_newCustomer("name", "female", "06/09/2001", "PhuLoc", "Hue", "Vietname", "111111", "0129238123", "e"+String.valueOf(random.nextInt(999))+"@gmail.com", "123qwe!@#");
        System.out.println("Done");
        driver.quit();
    }
}