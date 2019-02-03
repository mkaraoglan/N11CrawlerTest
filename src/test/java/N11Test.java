
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.*;

public class N11Test {
    private String email = "mktestdeneme@gmail.com";
    private String password = "MK123456";
    private String webSite = "https://www.n11.com/";
    private String searchProduct = "samsung";

    private N11Crawler n11Crawler;
    private SeleniumConfig config;

    @Before
    public void setUp(){
        config = new SeleniumConfig();
        n11Crawler = new N11Crawler(config, webSite, email, password, searchProduct);
    }

    @Test
    public void homePageTest() {
        n11Crawler.openWebPage();
        assertEquals("wrapper home",config.getDriver().findElement(By.id("wrapper")).getAttribute("class"));
    }

    @Test
    public void searchResultTest() {
        n11Crawler.searchResult();
        String searchResultText = config.getDriver().findElement(By.xpath("//*[@id='contentListing']/div/div/div[2]/section/div[1]/div[1]")).getText();
        assertTrue(searchResultText.contains("sonuç bulundu."));
    }


    @Test
    public void secondPageTest() {
        n11Crawler.secondPage();
        assertEquals("2",config.getDriver().findElement(By.id("currentPage")).getAttribute("value"));
    }

    @Test
    public void addFavoritesTest() {
        try {
            n11Crawler.favoritesPage();
            assertEquals(n11Crawler.getProductId(),config.getDriver().findElement(By.xpath("//*[@id='view']/ul/li[1]/div[1]")).getAttribute("id"));
        } finally {
            addFavoriteTearDown();
            String Result = config.getDriver().findElement(By.xpath("//*[@id='watchList']/div")).getText();
            assertEquals("İzlediğiniz bir ürün bulunmamaktadır.",Result);
        }

    }


    private void addFavoriteTearDown() {
        config.getDriver().findElement(By.xpath("//*[@id='@productId']/div[3]/span".replace("@productId", n11Crawler.getProductId()))).click();
        config.getDriver().navigate().refresh();
    }

    @After
    public void tearDown() {
        config.closeDriver();
    }
}
