package my.vaadin.crm.ui;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.UI;

/**
 *
 */
@Theme("mytheme")
@SpringUI
public class MyUI extends UI {

	@Autowired
	private SpringViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		Navigator navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
		setNavigator(navigator);
		if (navigator.getState().isEmpty()) {
			navigator.navigateTo(MainView.VIEW_NAME);
		}
	}

	@WebServlet(value = "/*", asyncSupported = true)
	public static class Servlet extends SpringVaadinServlet {
	}

	@WebListener
	public static class MyContextLoaderListener extends ContextLoaderListener {
	}

	@Configuration
	@EnableVaadin
	public static class MyConfiguration {
	}
}
