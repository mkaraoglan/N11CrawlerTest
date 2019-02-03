import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class N11Crawler {

    private WebDriver driver;
    private String website;
    private String email, password;
    private String productId;
    private String searchProduct;

    public N11Crawler(SeleniumConfig config, String website, String email, String password, String searchProduct) {
        this.website = website;
        this.email = email;
        this.password = password;
        this.searchProduct = searchProduct;
        this.driver = config.getDriver();
    }

    public void openWebPage(){
        driver.get(website);
    }

    public void searchResult(){
        openWebPage();
        driver.findElement(By.xpath("//*[@id='header']/div/div/div[2]/div[2]/div[2]/div/div/a[1]")).click();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("searchData")).sendKeys(searchProduct);
        driver.findElement(By.xpath("//*[@id='header']/div/div/div[2]/div[1]/a")).click();
    }

    public void secondPage(){
        searchResult();
        driver.findElement(By.xpath("//*[@id='contentListing']/div/div/div[2]/div[3]/a[2]")).click();
    }

    public void favoritesPage(){
        secondPage();
        driver.findElement(By.xpath("//div[@id='view']/ul/li[3]/div[1]/div[2]/span[@class='textImg followBtn']")).click();
        productId = driver.findElement(By.xpath("//div[@id='view']/ul/li[3]/div[1]")).getAttribute("id");
        Actions action = new Actions(driver);
        WebElement we = driver.findElement(By.xpath("//*[@id='header']/div/div/div[2]/div[2]/div[2]/div[1]"));
        action.moveToElement(we).moveToElement(driver.findElement(By.xpath("//*[@id='header']/div/div/div[2]/div[2]/div[2]/div[2]/div/a[2]"))).click().build().perform();
        driver.findElement(By.xpath("//*[@id='myAccount']/div[3]/ul/li[1]/div/a")).click();
    }

    public String getProductId(){
        return productId;
    }
}
