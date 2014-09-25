package org.gambol.modules.test.selenium;


  import java.io.BufferedReader;
  import java.io.InputStreamReader;
  import java.net.HttpURLConnection;
  import java.net.URL;
  import java.util.Iterator;
  import java.util.List;
  import java.util.Set;
  import java.util.concurrent.TimeUnit;

  import org.openqa.selenium.By;
  import org.openqa.selenium.JavascriptExecutor;
  import org.openqa.selenium.WebDriver;
  import org.openqa.selenium.WebElement;


public class CommonFunction {

    public final static int SHORT_TIMEOUT = 1;
    public final static int NORMAL_TIMEOUT = 5;
    public final static int LONG_TIMEOUT = 30;

    public static WebDriver loginSupplier(WebDriver driver) {
        try {
            driver.findElement(By.cssSelector("a[item=signin]")).click();
        } catch (Exception e) {

        }

        return login(driver, "xxx", "xxx");
    }

    public static WebDriver loginQunarSuper(WebDriver driver) {
        return login(driver, "xxx", "xxx");
    }

    public static void logout(WebDriver driver, String originWindow) {
        driver.switchTo().window(originWindow);
        driver.findElement(By.linkText("退出")).click();
    }

    /**
     * 填写用户信息 并点击下一步
     *
     * @param driver
     */
    public static void addContactInfo(WebDriver driver) {
        driver.findElement(By.id("contactUser")).sendKeys("test");
        driver.findElement(By.id("contactMobile")).sendKeys("13800138000");
        driver.findElement(By.id("contactEmail")).sendKeys("zz@a.com");
        driver.findElement(By.name("userMessage")).sendKeys("test");

        //	driver.findElement(By.linkText("下一步")).click();
    }

    /**
     * 点击 去支付按钮
     *
     * @param driver
     */
    public static void clickGoPaying(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(LONG_TIMEOUT, TimeUnit.SECONDS);
        driver.findElement(By.id("goPaying")).click();
        driver.manage().timeouts().implicitlyWait(NORMAL_TIMEOUT, TimeUnit.SECONDS);
    }

    public static WebElement getQunarPayMoney(WebDriver driver) {
        return driver.findElement(By.cssSelector("li.big"));
    }

    /**
     * 点击立即预订
     *
     * @param driver
     */
    public static void orderNow(WebDriver driver) {
        driver.findElement(By.linkText("立即预订")).click();
    }

    /**
     * 取招行里的钱
     *
     * @param driver
     * @return
     */
    public static WebElement getBankPayMoney(WebDriver driver) {
        return driver.findElement(By.xpath("//span/div/table/tbody/tr[4]/td[2]"));
    }

    /**
     * l-djb2c2.dev.cn6的机器 无法直接连接hsuggest.qunar.com，因此只能去掉这个限制
     * @param driver
     * @param id
     */
    public static void removeRestrict(WebDriver driver, String id) {
        String changeDepartureJs = "$('#" + id + "').attr('data-jvalidator-pattern', 'not_empty') ";
        ((JavascriptExecutor) driver).executeScript(changeDepartureJs);
    }

    public static void addArriveForGroup(WebDriver driver, String id) throws Exception{

        driver.findElement(By.id(id)).sendKeys("c");

        Thread.sleep(300);
        // tr data-ind="0"
        // 每个东西的suggest no不一样。。
        // 跟团游是5
        // 自由行是3
        driver.findElement(By.xpath("//div[@class='q-suggest'][5]/table/tbody/tr/td")).click();
        driver.findElement(By.xpath("(//img[@alt='添加'])[2]")).click();
    }

    public static void addArriveForFree(WebDriver driver, String id, String suggestNo) throws Exception{

        driver.findElement(By.id(id)).sendKeys("d");

        Thread.sleep(300);
        // tr data-ind="0"
        // 每个东西的suggest no不一样。。
        // 跟团游是5
        // 自由行是3 或者 6
        driver.findElement(By.xpath("//div[@class='q-suggest'][" + suggestNo +  "]/table/tbody/tr/td")).click();
        try {
            driver.findElement(By.xpath("(//img[@alt='添加'])")).click();
        } catch(Exception e) {

        }
    }

    /**
     * 在富文本编辑器中，只能用js，给某一个隐藏域赋值
     * @param driver
     * @param id
     * @param content
     * @throws Exception
     */

    public static void addContentInRichEdit(WebDriver driver, String id, String content) throws Exception {
        String js = "document.getElementById('" + id + "').value='" + content + "';";
        ((JavascriptExecutor) driver).executeScript(js);
    }

    /**
     * 填写一个单个用户的用户信息
     *
     * @param driver
     */
    public static void fillUpOneUserInfo(WebDriver driver) {
        driver.findElement(By.name("name")).sendKeys("55");
        driver.findElement(By.cssSelector("div.csStitle")).click();
        driver.findElement(By.linkText("护照")).click();
        driver.findElement(By.name("idNo")).clear();
        driver.findElement(By.name("idNo")).sendKeys("111");

        driver.findElement(By.id("submitBtn")).click();
    }

    /**
     * 进入添加产品页
     *
     * @param driver
     */
    public static void clickAddNewProduct(WebDriver driver) {
        driver.findElement(By.id("mainmenu_2")).click();
        driver.findElement(By.linkText("添加新产品")).click();
    }

    /**
     * 填写所有用户的用户信息
     */
    public static void fillUpAllUserInfo(WebDriver driver) {
        List<WebElement> nameElements = driver.findElements(By.name("name"));
        for (WebElement e : nameElements) {
            e.sendKeys("test");
        }

        List<WebElement> idTypeElements = driver.findElements(By.cssSelector("div.contact_info div.csStitle"));
        for (WebElement e : idTypeElements) {
            e.click();
            driver.findElement(By.linkText("军官证")).click();

			/*
			 * maybe useful later if (i == 1) {
			 * driver.findElement(By.linkText("军官证")).click(); } else { //String
			 * xpath = "//a[contains(text(),'回乡证')])[" + i + "]";
			 * //driver.findElement(By.xpath(xpath)).click();
			 * driver.findElement(By.linkText("军官证")).click(); }
			 */
        }

        List<WebElement> idNoElements = driver.findElements(By.name("idNo"));
        for (WebElement e : idNoElements) {
            e.sendKeys("111");
        }


        //	driver.findElement(By.id("submitBtn")).click();

    }

    /**
     * 选择不同的银行
     *
     * @param driver
     */
    public static void chooseBankChooser(WebDriver driver) {
        driver.findElement(By.id("debit2-check-ICBCC")).click();
        driver.findElement(By.id("debit2-check-CMBCE")).click();
    }

    /**
     * 打开一个新页面，并且跳到最后一个页面
     *
     * @param driver
     */
    public static void goToLastWindow(WebDriver driver) {
        // new window
        Set<String> windows = driver.getWindowHandles();
        Iterator<String> it = windows.iterator();
        String a = "";
        while (it.hasNext()) {
            a = it.next();
        }
        driver.switchTo().window(a);
    }

    /**
     * 从仓库中上线一个最新产生的产品
     *
     * @param driver
     */
    public static void onlineProduct(WebDriver driver) {

        driver.findElement(By.linkText("仓库中的产品")).click();
        switchToShowInfo(driver);
        driver.findElement(By.id("checkbox")).click();
        driver.findElement(By.xpath("//a[2]/span")).click();
        driver.switchTo().alert().accept();
        driver.switchTo().alert().accept();
    }

    /**
     * 在登录页面登录
     *
     * @param driver
     * @param username
     * @param password
     * @return
     */
    public static WebDriver login(WebDriver driver, String username, String password) {

        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.manage().timeouts().implicitlyWait(SHORT_TIMEOUT, TimeUnit.SECONDS);
        try {
            driver.findElement(By.cssSelector("input.button")).click();
        } catch (Exception e) {
            driver.findElement(By.id("submitBtn")).click();
        }

        driver.manage().timeouts().implicitlyWait(NORMAL_TIMEOUT, TimeUnit.SECONDS);
        return driver;
    }

    /**
     * 切换到show_info这个iframe里
     *
     * @param driver
     */
    public static void switchToShowInfo(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(SHORT_TIMEOUT, TimeUnit.SECONDS);

        try {
            driver.switchTo().frame("show_info");
        } catch (Exception e) {
            // e.printStackTrace();
        }

        driver.manage().timeouts().implicitlyWait(NORMAL_TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * 增加按类型的库存
     *
     * @param driver
     */
    public static void addTypeStock(WebDriver driver, String title) throws Exception {
        driver.manage().timeouts().implicitlyWait(SHORT_TIMEOUT, TimeUnit.SECONDS);
        driver.findElement(By.name("title")).sendKeys(title + "weekdays");
        try {
            driver.findElement(By.id("market_price")).sendKeys("444");
        } catch (Exception e) {
            try {
                Thread.sleep(1000);
                driver.findElement(By.name("market_price")).sendKeys("444");
            } catch (Exception ee) {

            }
        }

        driver.findElement(By.name("qunar_price")).sendKeys("222");
        driver.findElement(By.name("count")).sendKeys("22");

        List<WebElement> actionElementList = driver.findElements(By.id("action_no"));
        if (actionElementList != null && actionElementList.size() != 0) {
            actionElementList.get(0).sendKeys("22");
        }

        driver.findElement(By.cssSelector("a.save_btn")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector("a.go_add_btn")).click();

        driver.findElement(By.name("title")).sendKeys(title + "weekend");
        try {
            driver.findElement(By.id("market_price")).sendKeys("555");
        } catch (Exception e) {
            try {
                Thread.sleep(1000);
                driver.findElement(By.name("market_price")).sendKeys("555");
            } catch (Exception ee) {

            }
        }

        driver.findElement(By.name("qunar_price")).sendKeys("242");
        driver.findElement(By.name("count")).sendKeys("224");

        actionElementList = driver.findElements(By.id("action_no"));
        if (actionElementList != null && actionElementList.size() != 0) {
            actionElementList.get(0).sendKeys("23");
        }
        driver.findElement(By.cssSelector("a.save_back_btn")).click();
        driver.manage().timeouts().implicitlyWait(NORMAL_TIMEOUT, TimeUnit.SECONDS);
    }

    public static void addResourceId(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(CommonFunction.SHORT_TIMEOUT, TimeUnit.SECONDS);
        try {
            driver.findElement(By.id("resource_id")).sendKeys("123");
        } catch (Exception e) {

        }
        driver.manage().timeouts().implicitlyWait(CommonFunction.NORMAL_TIMEOUT, TimeUnit.SECONDS);
    }

    public static void getConnection(String url) throws Exception{
        WebDriver wd = GetDriver.getInstance().getRemoteDriver();
        wd.get(url);
        wd.quit();
    }

    public static void applyTuan(WebDriver driver, String productId) throws Exception {
        // 找到该产品
        //老的申请团购方式
		/*
		List<WebElement> elementList = driver.findElements(By.id("checkbox"));
		boolean find = false;
		for (WebElement element : elementList) {
			if (productId.equals(element.getAttribute("data-id"))) {
				element.click();
				find = true;
				break;
			}
		}
		if (!find) {
			throw new Exception("供应商点击申请团购时没有找到productId=" + productId + "的产品");
		}

		// 点击申请团购
		driver.findElement(By.xpath("//a[5]/span")).click();
		Thread.sleep(100);
		driver.findElement(By.id("applyBtn")).click();
		driver.switchTo().alert().accept();
		*/
        List<WebElement> elementList = driver.findElements(By.linkText("申请团购"));
        boolean find = false;
        for (WebElement element : elementList) {
            if (productId.equals(element.getAttribute("data-id"))) {
                element.click();

                find = true;
                break;
            }
        }
        if (!find) {
            throw new Exception("供应商点击申请团购时没有找到productId=" + productId + "的产品");
        }

        driver.findElement(By.linkText("申请")).click();
        driver.switchTo().alert().accept();

    }

    /**
     * 审核团品
     */
    public static void CheckTuan(WebDriver driver, String productId, String originWindow) throws Exception {
        driver.findElement(By.id("mainmenu_29")).click();
        driver.findElement(By.linkText("转团购产品")).click();
        originWindow = driver.getWindowHandle();
        driver.switchTo().frame("show_info");
        // 找到该团品,点击审核产品
        List<WebElement> elementList = driver.findElements(By.linkText("审核产品"));
        boolean find = false;
        for (WebElement element : elementList) {
            if (productId.equals(element.getAttribute("data-id"))) {
                find = true;
                element.click();
                break;
            }
        }
        if (!find) {
            throw new Exception("管理员审核产品时没有找到productId=" + productId + "的产品");
        }

        Thread.sleep(500);
        driver.findElement(By.id("submitBtn")).click();
        driver.switchTo().alert().accept();
    }



    /**
     * 供应商点击 "发布上线"
     */

    public static void supplierPublishProduct(WebDriver driver, String productId) throws Exception {

        driver.findElement(By.id("mainmenu_2")).click();
        driver.findElement(By.linkText("转团购产品")).click();
        driver.switchTo().frame("show_info");
        List<WebElement> elementList = driver.findElements(By.linkText("发布上线"));
        boolean find = false;
        for (WebElement element : elementList) {
            if (productId.equals(element.getAttribute("data-id"))) {
                find = true;
                element.click();
                break;
            }
        }
        if (!find) {
            throw new Exception("供应商点击发布上线时没有找到productId=" + productId + "的产品");
        }
        driver.findElement(By.id("confirmBtn")).click();
        driver.switchTo().alert().accept();
        driver.findElement(By.id("closeBtn")).click();
    }
}
