package baitap;

import base.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Random;

public class B6Alert {
    public String catchAlert(String cmd){
        String alert = "";
        try{
            alert="Alert: "+driver.switchTo().alert().getText();
            if(cmd.equals("ok")){
                driver.switchTo().alert().accept();

            }
            else if(cmd.equals("cancel")){
                driver.switchTo().alert().dismiss();
            }
            else{
                driver.switchTo().alert().sendKeys(cmd);
                driver.switchTo().alert().accept();
            }
        } catch (Exception e) {
        }

        return alert;
    }
    public static WebDriver driver;
    public static Random random = new Random();
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resource/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        options.addExtensions(new File("resource/AdGuard.crx"));

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
        driver.get("http://demo.automationtesting.in/Alerts.html");
//        driver.manage().window().maximize();
//        driver.manage ().window().setSize(new Dimension( 375, 812));
        System.out.println("Opened page: " +driver.getTitle());
    }
    public void alertWithOK(){
        try{
            WebElement btn = driver.findElement(By.xpath("//*[@id=\"OKTab\"]/button"));
            btn.click();
            System.out.println("Alert With OK, \t"+catchAlert("ok"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void alertWithTextBox(String txt){
        try{
            WebElement btn = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[1]/ul/li[3]/a"));
            btn.click();
            WebElement btnAlert = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[3]/button"));
            btnAlert.click();
            System.out.println("Alert With Textbox, \t"+catchAlert(txt));
            String result = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[3]/p")).getAttribute("innerHTML");
            System.out.println("Result: "+result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void alertWithOKAndCancel(String ok){
        try{
            WebElement btn = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[1]/ul/li[2]/a"));
            btn.click();
            WebElement btnAlert = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[2]/button"));
            btnAlert.click();
//            Thread.sleep(5000);
            System.out.println("Alert With OK And Cancel, \t"+catchAlert(ok));
            String result = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[2]/p")).getAttribute("innerHTML");
            System.out.println("Result: "+result);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        B6Alert test = new B6Alert();
        test.setUp();
        test.alertWithOK();
        test.alertWithOKAndCancel("ok");
        test.alertWithOKAndCancel("cancel");
        test.alertWithTextBox("ThanhTuan");
        System.out.println("done");
        driver.quit();
    }
}
