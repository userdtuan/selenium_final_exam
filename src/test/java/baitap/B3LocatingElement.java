package baitap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Random;

public class B3LocatingElement {
    public static WebDriver driver;
    public static Random random = new Random();
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resource/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://demoqa.com/automation-practice-form");
        System.out.println("Opened page: " +driver.getTitle());
    }
    public void practiceFrom(String fname,String lname,String mail,String radio,String unum,String birth,String subject,String[] checkbox,String addr){
        WebElement firstName = driver.findElement(By.id("firstName"));
        WebElement lastName = driver.findElement(By.id("lastName"));
        WebElement userEmail = driver.findElement(By.id("userEmail"));
        WebElement radio1 = driver.findElement(By.xpath("//*[@id=\"gender-radio-1\"]"));
        WebElement radio2 = driver.findElement(By.xpath("//*[@id=\"gender-radio-2\"]"));
        WebElement radio3 = driver.findElement(By.xpath("//*[@id=\"gender-radio-3\"]"));
        WebElement userNumber = driver.findElement(By.id("userNumber"));
        WebElement dateOfBirthInput = driver.findElement(By.id("dateOfBirthInput"));
        WebElement subjectsInput = driver.findElement(By.id("subjectsInput"));
        WebElement checkBox1 = driver.findElement(By.id("hobbies-checkbox-1"));
        WebElement checkBox2 = driver.findElement(By.id("hobbies-checkbox-2"));
        WebElement checkBox3 = driver.findElement(By.id("hobbies-checkbox-3"));
        WebElement currentAddress = driver.findElement(By.id("currentAddress"));
        WebElement select3 = driver.findElement(By.id("react-select-3-input"));
        WebElement select4 = driver.findElement(By.id("react-select-4-input"));

        firstName.sendKeys(fname);
        lastName.sendKeys(lname);
        userEmail.sendKeys(mail);
        radio1.click();
//        if(radio.equals("male")){
//            radio1.click();
//        } else if (radio.equals("female")) {
//            radio2.click();
//        }
//        else {
//            radio3.click();
//        }
        userNumber.sendKeys(unum);
        dateOfBirthInput.sendKeys(birth);
        subjectsInput.sendKeys(subject);
        for(int i=0; i<=checkbox.length; i++){
            if(checkbox[i].equals("sport")){
                checkBox1.click();
            }
            else if(checkbox[i].equals("reading")){
                checkBox2.click();
            }
            else{
                checkBox3.click();
            }
        }
        currentAddress.sendKeys(addr);




    }
    public static void main(String[] args){
        B3LocatingElement test = new B3LocatingElement();
        test.setUp();
        String arr[] = {"sport", "reading"};
        test.practiceFrom("Tran", "Quoc Nguyen", "nguyen@gmail.com", "male", "021238182", "12/12/2021", "Math", arr,"DaNanf" );
//        driver.quit();
    }
}
