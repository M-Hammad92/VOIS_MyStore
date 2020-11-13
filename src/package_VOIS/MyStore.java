package package_VOIS;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MyStore 
{
		private WebDriver driver;
		
		@BeforeMethod
		public void openMyStore() 
		{
			System.out.println("Start Project");
			System.setProperty("webdriver.chrome.driver","C:\\chromedriver_win32\\chromedriver.exe");
			this.driver = new ChromeDriver();
			this.driver.manage().window().maximize();
			String MyStoreUrl = new String ("http://automationpractice.com/index.php");
			this.driver.navigate().to(MyStoreUrl);
		}

		
		@AfterMethod
		public void CloseMyStore()
		{
			System.out.println("CleanUp");
			this.driver.close();
		}


//Test_Case 1 : Create an Account		
		@Test (priority = 0)
		public void CreateAccount()  
		{
			//Home Page
			this.driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")).click();
			
			//Create & Sign-in Page
			WebDriverWait Wait1 = new WebDriverWait(driver, 30);
			Wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_create")));
			this.driver.findElement(By.id("email_create")).sendKeys("muhamed.hammad92@gmail.com");
			this.driver.findElement(By.xpath("//*[@id=\"SubmitCreate\"]/span")).click();
			
			// Creating Form
			WebDriverWait Wait2 = new WebDriverWait(driver, 30);
			Wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer_firstname")));
			
			this.driver.findElement(By.id("customer_firstname")).sendKeys("Muhamed");
			this.driver.findElement(By.id("customer_lastname")).sendKeys("Hammad");
			this.driver.findElement(By.id("passwd")).sendKeys("12345");
			this.driver.findElement(By.id("firstname")).sendKeys("Muhamed");
			this.driver.findElement(By.id("lastname")).sendKeys("Muhamed");
			this.driver.findElement(By.id("address1")).sendKeys("Negh.10");
			this.driver.findElement(By.id("city")).sendKeys("Cairo");
			Select state = new Select(this.driver.findElement(By.id("id_state")));
			state.selectByVisibleText("Alabama");
			this.driver.findElement(By.id("postcode")).sendKeys("35005");
			Select counry = new Select(this.driver.findElement(By.id("id_country")));
			counry.selectByVisibleText("United States");
			this.driver.findElement(By.id("phone_mobile")).sendKeys("01009017215");
			this.driver.findElement(By.id("alias")).sendKeys("aliasAddress");
			this.driver.findElement(By.xpath("//*[@id=\"submitAccount\"]/span")).click();
			
			//Asserting on creation successfully
			String WelcomeMessage;
			WelcomeMessage = driver.findElement(By.xpath("//*[@id=\"center_column\"]/p")).getText();
			Assert.assertEquals(WelcomeMessage, 
					"Welcome to your account. Here you can manage all of your personal information and orders.");
		}	
		
//Test_Case 2 : Login with Created Account
		@Test (priority = 1)
		public void Login() 
		{
			//Home Page
			this.driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")).click();
			
			//Create & Sign-in Page
			WebDriverWait Wait3 = new WebDriverWait(driver, 30);
			Wait3.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
			//Login credentials
			this.driver.findElement(By.id("email")).sendKeys("muhamed.hammad92@gmail.com");
			this.driver.findElement(By.id("passwd")).sendKeys("12345");
			this.driver.findElement(By.xpath("//*[@id=\"SubmitLogin\"]/span")).click();
			//Asserting on successful Login
			String UserProfile;
			UserProfile = driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a/span")).getText();
			Assert.assertEquals(UserProfile, "Muhamed Hammad");
		} 		

		
//Test_Case 3 : Cart Checkout 		
		@Test (priority = 2)
		public void CartCheckout()  
		{
			//Home Page
			this.driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")).click();
			//Create & Sign-in Page
			WebDriverWait Wait4 = new WebDriverWait(driver, 30);
			Wait4.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
			this.driver.findElement(By.id("email")).sendKeys("muhamed.hammad92@gmail.com");
			this.driver.findElement(By.id("passwd")).sendKeys("12345");
			this.driver.findElement(By.xpath("//*[@id=\"SubmitLogin\"]/span")).click();
			
			//T-Shirts category
			this.driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[3]/a")).click();
			
			//Scroll down to product
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0,900)");
			this.driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li/div/div[2]/h5/a")).click();
			//Scroll down to "Add to cart" button
			js.executeScript("window.scrollTo(0,500)");
			this.driver.findElement(By.xpath("//*[@id=\"add_to_cart\"]/button/span")).click();
			//Asserting on product is added to cart
			WebDriverWait Wait5 = new WebDriverWait(driver, 20);
			Wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2")));
			String AddedProduct;
			AddedProduct = driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2")).getText();
			Assert.assertEquals(AddedProduct, "Product successfully added to your shopping cart");
			//Proceed to checkout
			this.driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a/span")).click();
			//Summary
			this.driver.findElement(By.xpath("//*[@id=\"center_column\"]/p[2]/a[1]/span")).click();
			//Address
			this.driver.findElement(By.xpath("//*[@id=\"center_column\"]/form/p/button/span")).click();
			//Shipping
			this.driver.findElement(By.id("cgv")).click();
			this.driver.findElement(By.xpath("//*[@id=\"form\"]/p/button/span")).click();
			//Payment
			this.driver.findElement(By.xpath("//*[@id=\"HOOK_PAYMENT\"]/div[2]/div/p/a/span")).click();
			//Confirm
			this.driver.findElement(By.xpath("//*[@id=\"cart_navigation\"]/button/span")).click();
			//Asserting on completing the order
			String OrderCompleted;
			OrderCompleted = driver.findElement(By.xpath("//*[@id=\"center_column\"]/p[1]")).getText();
			Assert.assertEquals(OrderCompleted, "Your order on My Store is complete.");		
		} 

}
		
		

