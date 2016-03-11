package my.vaadin.crm.ui.event;

import java.util.function.Consumer;

@FunctionalInterface
public interface SaveListener<T> extends Consumer<T> {

}
