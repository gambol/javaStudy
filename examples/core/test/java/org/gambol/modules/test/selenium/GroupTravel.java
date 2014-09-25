package org.gambol.modules.test.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * 跟团游相关的函数
 *
 * @author zhenbao.zhou
 *
 */
public class GroupTravel {

    // 产生一个跟团游产品, 返回产生加密后的ID
    public String generateAnGroupProduct(WebDriver driver, String title) throws Exception {
        String originWindow = driver.getWindowHandle();
        CommonFunction.loginSupplier(driver);
        CommonFunction.clickAddNewProduct(driver);

        driver.switchTo().frame("show_info");
        driver.findElement(By.id("p_function_group")).click();
        driver.findElement(By.id("title")).sendKeys(title);
        driver.findElement(By.id("day")).sendKeys("1");
        driver.findElement(By.xpath("(//input[@id='advance_day_2'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@id='advance_day_2'])[2]")).sendKeys("1");
        CommonFunction.removeRestrict(driver, "departure");
        CommonFunction.addArriveForFree(driver, "departure", "4");
		/*
		driver.findElement(By.id("arrive")).sendKeys("c");

		Thread.sleep(300);
		driver.findElement(By.xpath("(//img[@alt='添加'])[2]")).click();
		*/
        CommonFunction.addArriveForGroup(driver, "arrive");

        // 上传产品主图
        String js = "document.getElementById('grouptrip-mainpic').value='201206/22/C.JhS1_t5fT73_C1VwJ';";
        ((JavascriptExecutor) driver).executeScript(js);

        driver.findElement(By.id("recommendation")).sendKeys("副标题你好");
        //	driver.findElement(By.id("recommendation_2")).sendKeys("副标题你好");
        js = "document.getElementById('feature-group').value = '特色asd 吧'";
        try {
            ((JavascriptExecutor) driver).executeScript(js);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Select(driver.findElement(By.id("totraffic_group"))).selectByVisibleText("火车去");
        new Select(driver.findElement(By.xpath("(//select[@id='backtraffic'])[2]"))).selectByVisibleText("火车回");

        //driver.findElement(By.xpath("//form[@id='frm']/table[3]/tbody/tr/td/a")).click();
        driver.findElement(By.xpath("//form[@id='frm']/table[3]/tbody/tr/td/a[2]")).click();

        addSchedule(driver);

        Thread.sleep(300);
        CommonFunction.switchToShowInfo(driver);

        try {
            driver.findElement(By.linkText("价格/库存")).click();
        } catch(Exception e) {
            Thread.sleep(2000);
            driver.findElement(By.linkText("价格/库存")).click();
        }

        try {
            driver.findElement(By.linkText("批量设置")).click();
        } catch(Exception e) {
            Thread.sleep(2000);
            driver.findElement(By.linkText("价格/库存")).click();
        }

        driver.findElement(By.cssSelector("input[type=\"checkbox\"]")).click();
        driver.findElement(By.xpath("//span[@class='a_blue js_checkbox_detail_all hide']/input")).click();
        driver.findElement(By.id("market_price")).sendKeys("111");
        driver.findElement(By.id("adult_price")).sendKeys("11");
        driver.findElement(By.id("count")).sendKeys("11");
        driver.findElement(By.id("normal_min_buy_count")).sendKeys("1");
        driver.findElement(By.id("normal_max_buy_count")).sendKeys("11");
        driver.findElement(By.id("js_save_detail")).click();

        driver.switchTo().alert().dismiss();

        driver.switchTo().window(originWindow);
        driver.findElement(By.linkText("仓库中的产品")).click();
        CommonFunction.switchToShowInfo(driver);
        // 获取产品ID
        String href = driver.findElement(By.linkText(title)).getAttribute("href");
        int index = href.indexOf("id=");
        String productId = href.substring(index + 3);
        return productId;

    }

    public void addSchedule(WebDriver driver) throws Exception {
        driver.findElement(By.id("day_title_1")).sendKeys("day 1");
        String js = "document.getElementById('description_1').value = 'hha content'";
        try {
            ((JavascriptExecutor) driver).executeScript(js);
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.findElement(By.cssSelector("a.save_next_btn")).click();

    }
}
