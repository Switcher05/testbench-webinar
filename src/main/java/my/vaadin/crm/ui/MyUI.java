package my.vaadin.crm.ui;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import my.vaadin.crm.data.DatabaseInit;

/**
 *
 */
@Theme("mytheme")
@CDIUI("")
public class MyUI extends UI {

	@Inject
	private CDIViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		Navigator navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
		setNavigator(navigator);
		if (navigator.getState().isEmpty()) {
			navigator.navigateTo(MainView.VIEW_NAME);
		}
	}

	@WebListener
	private static class MyUIServlectContextListener implements ServletContextListener {

		@Inject
		private DatabaseInit init;

		@Override
		public void contextDestroyed(ServletContextEvent arg0) {
			// do nothing
		}

		@Override
		public void contextInitialized(ServletContextEvent arg0) {
			init.initDatabaseIfEmpty();
		}
	}
}
