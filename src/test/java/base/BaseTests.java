package base;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.Random;

public class BaseTests {
    public static WebDriver driver;
    public static Random random = new Random();
    public String catchAlert(){
        String alert = "";
        try{
            alert=", Alert: "+driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
        } catch (Exception e) {
        }

        return alert;
    }
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
        System.out.println(login_result+"\t : testcase Login, username = " + user + " and password = " + pass);
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
        System.out.println(success + "\t : testcase New customer, CustomerID: " + customerID);
        if (success) {
            driver.findElement(By.xpath("//*[@id=\"customer\"]/tbody/tr[14]/td/a")).click();

        }
    }

    public String new_account(String customerID, String accountType, int initialDeposit){
        driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[5]/a")).click();
        WebElement cusID = driver.findElement(By.name("cusid"));
        Select accType = new Select(driver.findElement(By.name("selaccount")));
        WebElement initDeposit = driver.findElement(By.name("inideposit"));
        WebElement button = driver.findElement(By.name("button2"));

        cusID.sendKeys(customerID);
        if(accountType.equals("Current")||accountType.equals("current")){
            accType.selectByIndex(1);
        }
        else {
            accType.selectByIndex(0);
        }
//        accType.
        initDeposit.sendKeys(String.valueOf(initialDeposit));
        button.click();
        return driver.findElement(By.xpath("//*[@id=\"account\"]/tbody/tr[4]/td[2]")).getAttribute("innerHTML");
    }

    public void testcase_newAccount(String accountType, int initialDeposit){
        boolean success = true;
        String accID = null;
        String customerID = null;
        while (customerID==null){
            customerID = new_customer("name", "female", "06/09/2001", "PhuLoc", "Hue", "Vietname", "111111", "0129238123", "e"+String.valueOf(random.nextInt(9999))+"@gmail.com", "123qwe!@#");
        }
        try
        {
            accID = new_account(customerID, accountType, initialDeposit);
        } catch (Exception e) {
            success = false;
        }
        if(accID==null||accID.isEmpty()){
            success = false;
        }
        System.out.println(success + "\t : testcase New Account, AccountID: " + accID);

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
    public void testcase_deposit(int accountID, int amount, String description) throws InterruptedException {
        WebElement depositBtn = null;
        Boolean success = true;
        try {
            depositBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[8]/a"));
        } catch (Exception e) {
            login("mngr399176", "mugyten");
            depositBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[8]/a"));
        }
        depositBtn.click();
        WebElement accID = driver.findElement(By.name("accountno"));
        WebElement amountt = driver.findElement(By.name("ammount"));
        WebElement descrt = driver.findElement(By.name("desc"));
        accID.sendKeys(String.valueOf(accountID));
        amountt.sendKeys(String.valueOf(amount));
        descrt.sendKeys(description);
        driver.findElement(By.name("AccSubmit")).click();
//        WebElement check = driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[1]/td/p"));
//        System.out.println(check.getAttribute("innerHTML"));
//        Alert alert = driver.switchTo().alert();
        String alertMessage= "";
        try{
            alertMessage= driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
        } catch (Exception e) {
        }
        // Displaying alert message
//        System.out.println(alertMessage);
        if(alertMessage.equals("Account does not exist")){
            success=false;
        }
        System.out.println(success + "\t : testcase Desposit, Alert: "+alertMessage);

    }
    public void testcase_withdrawal(int accountID, int amount, String description) throws InterruptedException {
        WebElement depositBtn = null;
        Boolean success = true;
        try {
            depositBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[9]/a"));
        } catch (Exception e) {
            login("mngr399176", "mugyten");
            depositBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[9]/a"));
        }
        depositBtn.click();
        WebElement accID = driver.findElement(By.name("accountno"));
        WebElement amountt = driver.findElement(By.name("ammount"));
        WebElement descrt = driver.findElement(By.name("desc"));
        accID.sendKeys(String.valueOf(accountID));
        amountt.sendKeys(String.valueOf(amount));
        descrt.sendKeys(description);
        driver.findElement(By.name("AccSubmit")).click();
//        WebElement check = driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[1]/td/p"));
//        System.out.println(check.getAttribute("innerHTML"));
//        Alert alert = driver.switchTo().alert();
        String alertMessage= "";
        try{
            alertMessage= driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
        } catch (Exception e) {
        }
        // Displaying alert message
//        System.out.println(alertMessage);
        if(alertMessage.equals("Account does not exist")){
            success=false;
        }
        System.out.println(success + "\t : testcase Withdrawal, Alert: "+alertMessage);

    }
    public void testcase_fundTransfer(int payers_account, int payee_account, int amount, String description) {
        WebElement depositBtn = null;
        Boolean success = true;
        try {
            depositBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[10]/a"));
        } catch (Exception e) {
            login("mngr399176", "mugyten");
            depositBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[10]/a"));
        }
        depositBtn.click();
        WebElement payersaccount = driver.findElement(By.name("payersaccount"));
        WebElement payeeaccount = driver.findElement(By.name("payeeaccount"));
        WebElement ammount = driver.findElement(By.name("ammount"));
        WebElement descrt = driver.findElement(By.name("desc"));
        payersaccount.sendKeys(String.valueOf(payers_account));
        payeeaccount.sendKeys(String.valueOf(payee_account));
        ammount.sendKeys(String.valueOf(amount));
        descrt.sendKeys(description);
        driver.findElement(By.name("AccSubmit")).click();
        String alertMessage= catchAlert();
        if(alertMessage!=""){
            success=false;
        }
        // Displaying alert message
//        System.out.println(alertMessage);
        System.out.println(success + "\t : testcase FundTransfer"+alertMessage);

    }
    public void testcase_customizeStatement(int accountno, String fdate, String tdate, int amountlowerlimit, int numtransaction) {
        WebElement depositBtn = null;
        Boolean success = true;
        try {
            depositBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[14]/a"));
        } catch (Exception e) {
            login("mngr399176", "mugyten");
            depositBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[14]/a"));
        }
        depositBtn.click();
        WebElement welm_accountno = driver.findElement(By.name("accountno"));
        WebElement welm_fdate = driver.findElement(By.name("fdate"));
        WebElement welm_tdate = driver.findElement(By.name("tdate"));
        WebElement welm_amountlowerlimit = driver.findElement(By.name("amountlowerlimit"));
        WebElement welm_numtransaction = driver.findElement(By.name("numtransaction"));
        welm_accountno.sendKeys(String.valueOf(accountno));
        welm_fdate.sendKeys(fdate);
        welm_tdate.sendKeys(tdate);
        welm_amountlowerlimit.sendKeys(String.valueOf(amountlowerlimit));
        welm_numtransaction.sendKeys(String.valueOf(numtransaction));

        driver.findElement(By.name("AccSubmit")).click();
        String alertMessage= catchAlert();
        if(alertMessage!=""){
            success=false;
        }
        System.out.println(success + "\t : testcase CustomizeStatement, Alert: "+alertMessage);

    }
    public void testcase_logout() {
        Boolean success = true;
        WebElement depositBtn = null;
        try {
            depositBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[15]/a"));
        } catch (Exception e) {
            login("mngr399176", "mugyten");
            depositBtn = driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[15]/a"));
        }
        depositBtn.click();
//        You Have Succesfully Logged Out!!
        String alertMessage= catchAlert();
        if(alertMessage.contains("Succesfully Logged Out")){

        }
        else {
            success = false;
        }
        // Displaying alert message
//        System.out.println(alertMessage);
        System.out.println(success + "\t : testcase Logout"+alertMessage);
    }
    public void quit(){
        driver.quit();

    }

    public static void main(String[] args) throws InterruptedException {
        BaseTests test = new BaseTests();
        test.setUp();
        test.testcase_login("mngr39917678878878787", "mkamsda");
        test.testcase_login("mngr399257", "YzyhAha");
        test.testcase_login("mngr39917678878878787", "mkamsda");
        System.out.println("------------------");
        test.testcase_newCustomer("name", "female", "06/09/2001", "PhuLoc", "Hue", "Vietname", "111111", "0129238123", "oalllss6@p00o.com", "123qwe!@#");
        test.testcase_newCustomer("name", "female", "06/09/2001", "PhuLoc", "Hue", "Vietname", "111111", "0129238123", "e"+String.valueOf(random.nextInt(999))+"@gmail.com", "123qwe!@#");
        test.testcase_newAccount("Current", 5000);
        test.testcase_newAccount("Savings", 5000);
        System.out.println("------------------");
        test.testcase_deposit(3,1212,"assasa");
        test.testcase_deposit(4,1212,"mkmk");
        System.out.println("------------------");
        test.testcase_withdrawal(3,1212,"assasa");
        test.testcase_withdrawal(5,1212,"ppppp");
        System.out.println("------------------");
        test.testcase_fundTransfer(2,3,1212,"assasa");
        test.testcase_fundTransfer(5,4,1212,"mkmk");
        System.out.println("------------------");
        test.testcase_customizeStatement(2,"01/12/2020","01/12/2022",1000, 21);
        test.testcase_customizeStatement(5,"01/12/2020","01/12/2020",9000, 40);
        System.out.println("------------------");
        test.testcase_logout();

        System.out.println("Done");
        Thread.sleep(5000);
        test.quit();
    }
}