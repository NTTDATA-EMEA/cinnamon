package io.magentys.cinnamon.webdriver.conditions;

import io.magentys.cinnamon.webdriver.conditions.Condition;

import java.util.Arrays;

public class OrCondition<T> extends Condition<T> {

    private final Condition<T>[] conditions;

    @SafeVarargs
    public OrCondition(final Condition<T>... conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean apply(final T t) {
        try {
            for (final Condition<T> condition : conditions) {
                if (condition.apply(t))
                    return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "or(" + Arrays.toString(conditions) + ")";
    }

}
