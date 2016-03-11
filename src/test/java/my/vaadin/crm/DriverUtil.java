package my.vaadin.crm;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class DriverUtil {
	public static WebDriver getPreferredDriver() {
		try {
			String headless = System.getProperty("phantomjs");
			if (Boolean.TRUE.equals(Boolean.valueOf(headless))) {
				return new PhantomJSDriver();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return new FirefoxDriver();
	}
}
