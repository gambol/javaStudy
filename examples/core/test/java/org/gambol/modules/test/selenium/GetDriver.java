package org.gambol.modules.test.selenium;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * 方便查看是远程server，还是本地server
 */
public class GetDriver {

    private GetDriver() {};

    private static GetDriver instance;

    private final String REMOTE_URL = "http://xxxxx/wd/hub";

    private final boolean isRemote = true;

    public static GetDriver getInstance() {
        if (instance == null) {
            instance = new GetDriver();
        }

        return instance;
    }

    public WebDriver getRemoteDriver() throws Exception{
        return new RemoteWebDriver(new URL(REMOTE_URL), 	DesiredCapabilities.firefox());
    }

    public WebDriver getLocalDriver() throws Exception {
        return new FirefoxDriver();
    }

    public WebDriver driver() throws Exception{
        if (isRemote) {
            return getRemoteDriver();
        } else {
            return getLocalDriver();
        }
    }
}