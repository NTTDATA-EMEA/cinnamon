package io.magentys.cinnamon.webdriver.actions;

public interface GenericAction<T> {

    void perform(T target);
}
