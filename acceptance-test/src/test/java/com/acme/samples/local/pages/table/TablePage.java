package com.acme.samples.local.pages.table;

import com.acme.samples.local.stepdef.TableMatchParams;
import io.cucumber.datatable.DataTable;
import io.magentys.cinnamon.conf.Env;
import io.magentys.cinnamon.webdriver.collections.PageElementCollection;
import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.elements.TableElement;
import io.magentys.cinnamon.webdriver.elements.TableElement.CellAdapter;
import io.magentys.cinnamon.webdriver.elements.TableElement.MatchingCell;
import io.magentys.cinnamon.webdriver.elements.TableElement.MultiCellAdapter;
import io.magentys.cinnamon.webdriver.support.FindByKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static io.magentys.cinnamon.webdriver.Browser.open;
import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.*;

public class TablePage {

    @FindByKey("table.container")
    PageElementCollection tableContainer;

    @FindByKey("table.table2")
    TableElement table2;

    @FindByKey("table.result")
    PageElement result;

    private final String baseUrl;

    @Inject
    public TablePage(final Env env) {
        baseUrl = env.config.getString("local-pages-url");
    }

    public void navigateTo(final String url) {
        open(baseUrl + url);
    }

    public String findRowContaining(final String tableId, final String string) {
        return table(tableId).all("table.container.table.row").first(textContains(string)).text();
    }

    public TableElement table(final String id) {
        final PageElement container = tableContainer.filter(attributeEquals("id", id)).first();
        return container.element("table.container.table");
    }

    public void waitUntilPageLoaded() {
        tableContainer.first().waitUntil(displayed);
    }

    public void clickButtonForRow(final String tableId, final String rowContent) {
        final PageElement button = table(tableId).all("table.container.table.row").first(textContains(rowContent)).element(By.tagName("button"));
        button.click();
    }

    public boolean containsResult(final String text) {
        return result.text().contains(text);
    }

    public DataTable table2Content() {
        return table2.asDataTable();
    }

    public DataTable tableContent(final String tableId) {
        return table(tableId).asDataTable();
    }

    public DataTable pivotContent(final String tableId) {
        return table(tableId).asPivotDataTable(Arrays.asList("colour", "year", "value"));
    }

    public DataTable pivotContent(final String tableId, final int colspan) {
        return table(tableId).withRowHeaderColspan(colspan).asPivotDataTable(Arrays.asList("colour", "year", "value"));
    }

    public DataTable pivotMulticellContent(final String tableId, final int colspan) {
        return table(tableId).withRowHeaderColspan(colspan).asPivotDataTable(Arrays.asList("colour", "year", "value"));
    }

    public MatchingCell getMatchingCell(final String tableId, final TableMatchParams tableParams) {
        return table(tableId).firstMatch(new CellMatcher(tableParams));
    }
}
