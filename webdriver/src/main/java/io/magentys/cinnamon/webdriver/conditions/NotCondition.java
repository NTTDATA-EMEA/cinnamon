package io.magentys.cinnamon.webdriver.conditions;

import io.magentys.cinnamon.webdriver.conditions.Condition;

public class NotCondition<T> extends Condition<T> {

    private final Condition<T> condition;

    public NotCondition(final Condition<T> condition) {
        this.condition = condition;
    }

    @Override
    public boolean apply(final T t) {
        try {
            return !condition.apply(t);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "not(" + condition.toString() + ")";
    }

}
