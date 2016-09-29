/*
Copyright 2007-2009 Selenium committers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.openqa.selenium.support.pagefactory;

import io.magentys.cinnamon.webdriver.Timeout;
import io.magentys.cinnamon.webdriver.conditions.Condition;
import io.magentys.cinnamon.webdriver.support.pagefactory.PageElementLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.magentys.cinnamon.webdriver.WebDriverUtils.getElementXPath;

public interface ElementLocator {

    WebElement findElement();

    List<WebElement> findElements();

    WebDriver getWebDriver();

    SearchContext getSearchContext();

    By getBy();

    Condition<WebElement> getCondition();

    Timeout getTimeout();

    static ElementLocator constructFrom(final WebDriver webDriver, final WebElement element) {
        final By by = By.xpath(getElementXPath(webDriver, element));
        return new PageElementLocator(webDriver, by);
    }

    static ElementLocator constructFrom(final WebDriver webDriver, final WebElement element, final Condition<WebElement> condition,
            final Timeout timeout) {
        final By by = By.xpath(getElementXPath(webDriver, element));
        return new PageElementLocator(webDriver, by, condition, timeout);
    }
}