package com.acme.samples.local.pages.table;

import com.acme.samples.local.stepdef.TableMatchParams;
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

    public List<TranslationTable> table2Content() {
        return table2.asList(TranslationTable.class);
    }

    public List<TranslationTable> tableContent(final String tableId) {
        return table(tableId).asList(TranslationTable.class);
    }

    public List<PivotValue> pivotContent(final String tableId) {
        return table(tableId).asPivot(pivotCellAdapter());
    }

    private CellAdapter<PivotValue> pivotCellAdapter() {
        return (columnHeading, rowHeading, cell) -> new PivotValue(rowHeading.getText(), columnHeading.getText(), cell.getText());
    }

    public List<PivotValue> pivotContent(final String tableId, final int colspan) {
        return table(tableId).withRowHeaderColspan(colspan).asPivot(pivotCellAdapter());
    }

    public List<PivotValue> pivotMulticellContent(final String tableId, final int n) {
        return table(tableId).withRowHeaderColspan(n).asPivot(new MultiCellAdapter<PivotValue>() {

            @Override
            public PivotValue adapt(final List<WebElement> columnHeading, final WebElement rowHeading, final WebElement cell) {
                if (columnHeading.size() == 2) {
                    // remove the "year" heading
                    columnHeading.remove(0);
                }

                return new PivotValue(rowHeading.getText(), columnHeading.get(0).getText(), cell.getText());
            }
        });
    }

    public MatchingCell getMatchingCell(final String tableId, final TableMatchParams tableParams) {
        return table(tableId).firstMatch(new CellMatcher(tableParams));
    }
}
