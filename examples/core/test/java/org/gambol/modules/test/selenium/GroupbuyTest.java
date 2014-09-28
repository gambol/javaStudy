package org.gambol.modules.test.selenium;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.*;

import org.openqa.selenium.*;


/**
 * 跟团游
 *
 * @author zhenbao.zhou
 */
public class GroupbuyTest extends TestCase {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = GetDriver.getInstance().driver();
        driver.manage().timeouts().implicitlyWait(CommonFunction.NORMAL_TIMEOUT, TimeUnit.SECONDS);
        Thread.sleep(1000);
    }

    @Test
    public void test() throws Exception {
        try {
            driver.get("http://baidu.com/");
            driver.findElement(By.cssSelector("div.sinfo")).click();
            driver.findElement(By.cssSelector("td.pink > div.box > ul > li.adult > em")).click();
            driver.findElement(By.linkText("立即预订")).click();
            //		driver.findElement(By.cssSelector("div.csStitle")).click();
            //		driver.findElement(By.linkText("4人")).click();

            driver.findElement(By.cssSelector("span.increase")).click();

            CommonFunction.addContactInfo(driver);

            CommonFunction.fillUpAllUserInfo(driver);
            CommonFunction.loginSupplier(driver);
            driver.findElement(By.linkText("确认订单")).click();
            CommonFunction.clickGoPaying(driver);

            CommonFunction.goToLastWindow(driver);

            System.out.println("跟团游购买测试OK");
        } catch (Exception e) {
            e.printStackTrace();
            fail("跟团游购买测试错误");
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            System.out.println("跟团游购买测试错误");
            fail(verificationErrorString);
        }
    }

}