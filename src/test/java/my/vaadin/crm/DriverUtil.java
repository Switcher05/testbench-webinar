package my.vaadin.crm;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class DriverUtil {
	public static WebDriver getPreferredDriver() {
		try {
			String headless = System.getProperty("phantomjs");
			if (Boolean.TRUE.equals(Boolean.valueOf(headless))) {
				PhantomJSDriver driver = new PhantomJSDriver();
				driver.manage().window().setSize(new Dimension(1280, 720));
				return driver;

			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return new FirefoxDriver();
	}
}
