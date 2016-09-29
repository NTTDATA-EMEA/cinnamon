package io.magentys.cinnamon.webdriver.conditions;

import com.google.common.base.Predicate;

public abstract class Condition<T> implements Predicate<T> {

    @Override
    public abstract boolean apply(T t);

    public AndCondition<T> and(final Condition<T> condition) {
        return new AndCondition<>(this, condition);
    }

    public OrCondition<T> or(final Condition<T> condition) {
        return new OrCondition<>(this, condition);
    }
}
