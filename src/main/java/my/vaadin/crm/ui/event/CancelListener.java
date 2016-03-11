package my.vaadin.crm.ui.event;

import java.util.function.Consumer;

@FunctionalInterface
public interface CancelListener<T> extends Consumer<T> {

}