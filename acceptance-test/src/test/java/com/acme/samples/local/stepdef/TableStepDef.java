package com.acme.samples.local.stepdef;

import com.acme.samples.local.context.TableContext;
import com.acme.samples.local.pages.table.TablePage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;
import io.magentys.cinnamon.webdriver.elements.Table.RowAdapter;
import io.magentys.cinnamon.webdriver.elements.TableElement.MatchingCell;
import org.fest.assertions.api.Assertions;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@ScenarioScoped
public class TableStepDef {

    private final TablePage page;
    private final TableContext ctx;

    @Inject
    public TableStepDef(final TablePage page, final TableContext ctx) {
        this.page = page;
        this.ctx = ctx;
    }

    @Given("I have navigated to the local page {string}")
    public void i_have_navigated_to_the_local_page(final String url) throws Throwable {
        page.navigateTo(url);
        page.waitUntilPageLoaded();
    }

    @When("I look in {string} for {string}")
    public void i_look_in_for(final String tableId, final String string) throws Throwable {
        final String rowContent = page.findRowContaining(tableId, string);
        ctx.setRowContent(rowContent);
    }

    @Then("I {maybe} find {string} in the same row")
    public void i_should_find_in_the_same_row(final String maybe, final String expectedText) throws Throwable {
        final boolean should = "should".equals(maybe);
        Assert.assertEquals(should, ctx.getRowContent().contains(expectedText));
    }

    @When("I click on the button in table {string} for row {string}")
    public void i_click_on_the_button_in_table_for_row(final String tableId, final String rowContent) throws Throwable {
        page.clickButtonForRow(tableId, rowContent);
    }

    @Then("the result {string} should be displayed")
    public void the_result_should_be_displayed(final String result) throws Throwable {
        Assert.assertTrue(page.containsResult(result));
    }

    @Then("table2 should contain:")
    public void table_should_contain(final List<Map<String, String>> expected) throws Throwable {
        Assert.assertEquals(expected, page.table2Content());
    }

    @Then("table {string} should contain:")
    public void table_should_contain(final String tableId, final List<Map<String, String>> expected) throws Throwable {
        Assert.assertEquals(expected, page.tableContent(tableId));
    }

    @Then("pivot table {string} should contain:")
    public void pivot_table_should_contain(final String tableId, final List<Map<String, String>> expected) throws Throwable {
        Assert.assertEquals(expected, page.pivotContent(tableId));
    }

    @Then("pivot table {string} with colspan of {int} should contain:")
    public void pivot_table_with_colspan_of_should_contain(final String tableId, final int n, final List<Map<String, String>> expected)
            throws Throwable {
        Assert.assertEquals(expected, page.pivotContent(tableId, n));
    }

    @Then("multicell pivot table {string} with colspan of {int} should contain:")
    public void multicell_pivot_table_with_colspan_of_should_contain(final String tableId, final int n, final List<Map<String, String>> expected)
            throws Throwable {
        Assert.assertEquals(expected, page.pivotMulticellContent(tableId, n));
    }

    @When("I choose to adapt {string} using a row adapter")
    public void i_choose_to_adapt_using_a_row_adapter(final String id) throws Throwable {
        final RowAdapter<Object[]> adapter = (columnHeaders, cells) -> new Object[] { columnHeaders, cells };

        final List<Object[]> adapted = page.table(id).asList(adapter);

        ctx.setAdapted(adapted);
    }

    @Then("the row adapter shall be called {int} times")
    public void the_row_adapter_shall_be_called_times(final int n) throws Throwable {
        Assertions.assertThat(ctx.getAdapted()).hasSize(n);
    }

    @Then("the adapter shall be passed {int} {string} column headers each time")
    public void the_adapter_shall_be_passed_column_headers_each_time(final int count, final String expectedTag) throws Throwable {
        verifyAdaptedItems(0, count, expectedTag);
    }

    @Then("the adapter shall be passed {int} {string} cells each time")
    public void the_adapter_shall_be_passed_cells_each_time(final int count, final String expectedTag) throws Throwable {
        verifyAdaptedItems(1, count, expectedTag);
    }

    private void verifyAdaptedItems(final int adaptedIdx, final int count, final String expectedTag) {
        final List<Object[]> adapted = ctx.getAdapted();

        for (final Object[] invocation : adapted) {

            @SuppressWarnings("unchecked") final List<WebElement> itemsToCheck = (List<WebElement>) invocation[adaptedIdx];

            Assertions.assertThat(itemsToCheck).hasSize(count);

            for (final WebElement element : itemsToCheck) {
                Assertions.assertThat(element.getTagName().equals(expectedTag));
            }
        }
    }

    @When("I search for the first cell in {string} that matches:")
    public void i_search_for_the_first_cell_in_that_matches(String table, List<Map<String, String>> params) throws Throwable {
        // There should only be 1 row
        TableMatchParams tableParams = TableMatchParams.fromMap(params.get(0));

        try {
            MatchingCell matchingCell = page.getMatchingCell(table, tableParams);
            ctx.setMatchingCell(matchingCell);
        } catch (NoSuchElementException e) {
            // no match
        }
    }

    @Then("the found cell should be:")
    public void the_found_cell_should_be(List<Map<String, String>> params) throws Throwable {
        // should only be 1
        TableMatchParams expected = TableMatchParams.fromMap(params.get(0));

        MatchingCell matchingCell = ctx.getMatchingCell();

        Assertions.assertThat(matchingCell.getColumn().getText()).isEqualTo(expected.getColumnHeading());
        Assertions.assertThat(matchingCell.getRow().getText()).isEqualTo(expected.getRowHeading());
        Assertions.assertThat(matchingCell.getCell().getText()).isEqualTo(expected.getValue());
    }

    @Then("no matching cell shall be found")
    public void no_matching_cell_shall_be_found() throws Throwable {
        Assertions.assertThat(ctx.getMatchingCell()).isNull();
    }
}