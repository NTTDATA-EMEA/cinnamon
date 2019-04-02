package io.magentys.cinnamon.webdriver.elements;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface Table {

    /**
     * Adapter to convert a row into the specified type.
     *
     * @param <AdaptedResult> The type this adapter returns
     */
    interface RowAdapter<AdaptedResult> {
        AdaptedResult adapt(List<WebElement> columnHeaders, List<WebElement> cells);
    }

    /**
     * Convert the rows of this table into a list of the given type. Converts the column headings of the table into
     * field names. Reflection is used to set the properties perform the class - setters are not required.
     * <p>
     * The column heading is converted to a field name using the following rules: - fields are changed to camel case,
     * using spaces/illegal characters to identify capitalisation - spaces are removed - illegal characters are removed
     * ('/')
     * <p>
     * Examples
     * <table>
     * <th>
     * <tr>
     * <td>column heading</td>
     * <td>field name</td>
     * </tr>
     * </th>
     * <tr>
     * <td>Foo bar baz</td>
     * <td>fooBarBaz</td>
     * </tr>
     * <tr>
     * <td>foobarbaz</td>
     * <td>foobarbaz</td>
     * </tr>
     * <tr>
     * <td>Foo/Bar</td>
     * <td>fooBar</td>
     * </tr>
     * </table>
     * <p>
     * Any column headings that do not correspond to a field in the given type are ignored.
     *
     * @param type The type representing the rows of the list. Must have a public no-argument constructor.
     * @return
     */
    <T> List<T> asList(Class<T> type);

    /**
     * Adapt the rows of this table into a list of items using the given type adapter
     * <p>
     * The adapter shall be called with the list of column headers elements. The number of elements in the
     * <code>columnHeaders</code> list shall equal the number of <code>cell</code> elements.
     * <p>
     * Example - for the following table:
     * <table>
     * <thead>
     * <tr>
     * <th>a</th>
     * <th>b</th>
     * </tr>
     * </thead> <tbody>
     * <tr>
     * <td>1</td>
     * <td>2</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>4</td>
     * </tr>
     * <tr>
     * <td>5</td>
     * <td>6</td>
     * </tr>
     * </tbody>
     * </table>
     * <p>
     * <pre>
     * adapt shall be called 3 times with the equivalent of:
     * adapt([<th>a</th>,<th>b</th>],[<td>1</td><td>2</td>])
     * adapt([<th>a</th>,<th>b</th>],[<td>3</td><td>4</td>])
     * adapt([<th>a</th>,<th>b</th>],[<td>5</td><td>6</td>])
     * </pre>
     */
    <T> List<T> asList(RowAdapter<T> adapter);

    /**
     * Convert this pivot table into a list of cells using the given adapter. It will iterate through each row in the
     * table, adapting all the cells (apart from the first cell, which is considered the row heading)
     *
     * @param adapter An adapter that will convert the cells to the required type
     * @return
     */
    <T> List<T> asPivot(TableElement.CellAdapter<T> adapter);

    <T> List<T> asPivot(TableElement.MultiCellAdapter<T> adapter);

    /**
     * Set the number of column headers to ignore for each rowHeader when using pivot tables.
     *
     * @param n
     * @return
     */
    Table withRowHeaderColspan(int n);

    /**
     * Find the first cell (columnHeader,rowHeader,intersecting cell) that matches the given matcher, such that
     * {@link TableElement.CellAdapter#adapt(org.openqa.selenium.WebElement, org.openqa.selenium.WebElement, org.openqa.selenium.WebElement)}
     * returns true.
     * <p>
     * If the table contains multiple headers for a column (including spanning), then each cell shall be called in turn
     * with each of the column headers. So, a table with <em>i</em> rows, <em>j</em> columns, and <em>l</em> column
     * headers for each row; the matcher shall be called a maximum of <em>j * k * l</em> times. The matcher shall be
     * called for column headers in the order that they are located (with the top-most column header invoked first).
     * Note, that in this case, the MatchingCell will contain the first column header which matched.
     *
     * @throws NoSuchElementException if no cell matching the parameters is found
     */
    TableElement.MatchingCell firstMatch(TableElement.CellAdapter<Boolean> matcher) throws NoSuchElementException;

}