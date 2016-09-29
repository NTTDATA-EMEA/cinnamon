package io.magentys.cinnamon.webdriver.actions;

public interface KeyStrokeActions {

    Actions typeText(CharSequence... keysToSend);

    Actions replaceText(CharSequence... keysToSend);

    Actions deleteContent();
}