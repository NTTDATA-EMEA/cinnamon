package io.magentys.cinnamon.webdriver.elements;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.cucumber.datatable.DataTable;
import io.magentys.cinnamon.webdriver.elements.TableElement.CellAdapter;
import io.magentys.cinnamon.webdriver.elements.TableElement.MatchingCell;
import io.magentys.cinnamon.webdriver.elements.TableElement.MultiCellAdapter;
import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static io.magentys.cinnamon.webdriver.WebDriverUtils.unwrapDriver;
import static org.openqa.selenium.support.pagefactory.ElementLocator.constructFrom;

public class TableElementImpl implements Table {

    private final ElementCache cache;
    private final By columnFinder = By.cssSelector("thead th");
    private final By rowLocator = By.cssSelector("tbody tr");
    private final By cellLocator = By.cssSelector("td,th");

    private int rowHeaderColspan = 1;

    public TableElementImpl(final WebElement element) {
        this(constructFrom(unwrapDriver(element), element), element);
    }

    public TableElementImpl(final ElementLocator elementLocator, final WebElement element) {
        this.cache = new ElementCache(elementLocator, element);
    }

    @Override
    public Table withRowHeaderColspan(final int n) {
        rowHeaderColspan = n;
        return this;
    }

    @Override
    public DataTable asDataTable() {
        return DataTable.create(this.asUntypedTable());
    }

    private List<List<String>> asUntypedTable() {
        final List<WebElement> columnHeaderElements = cache.getElement().findElements(columnFinder);
        final List<WebElement> rows = findAllRows();

        final List<List<String>> mappedRows = new LinkedList<>();

        mappedRows.add(
            columnHeaderElements.stream()
                .map(headerElement -> headerElement.getText())
                .collect(Collectors.toCollection(LinkedList::new))
        );


        for (final WebElement row : rows) {
            final List<WebElement> cells = row.findElements(cellLocator);
                mappedRows.add(
                    cells.stream()
                        .map(element -> element.getText())
                        .collect(Collectors.toCollection(LinkedList::new))
                );

        }

        return mappedRows;
    }

    @Override
    public <T> List<T> asList(final Class<T> type) {
        return asList(new ColumnHeadingToClassFieldMappingAdapter<>(type));
    }

    @Override
    public <T> List<T> asList(final RowAdapter<T> adapter) {

        final List<WebElement> columnHeaderElements = cache.getElement().findElements(columnFinder);
        final List<WebElement> rows = findAllRows();

        final List<T> mappedRows = new LinkedList<>();

        for (final WebElement row : rows) {
            final List<WebElement> cells = row.findElements(cellLocator);

            mappedRows.add(adapter.adapt(columnHeaderElements, cells));
        }

        return mappedRows;

    }

    //TODO JavaDoc
    @Override
    public DataTable asPivotDataTable(List<String> headers) {
        return DataTable.create(asUntypedPivot(headers));
    }

    private CellAdapter<List<String>> pivotCellAdapter() {
        return (columnHeading, rowHeading, cell) -> Arrays.asList(rowHeading.getText(), columnHeading.getText(), cell.getText());
    }

    private List<List<String>> asUntypedPivot(List<String> headers) {
        final CellVisitor<List<List<String>>> collatingCellAdapter = new CellVisitor<List<List<String>>>() {
            final List<List<String>> adaptedCells = new LinkedList<>();

            CellAdapter<List<String>> adapter = pivotCellAdapter();

            @Override
            public void visit(final List<WebElement> columnHeaders, final WebElement rowHeader, final WebElement cell) {
                // Adapt the first column header only - maintains backward
                // compatability for CellAdapter
                adaptedCells.add(adapter.adapt(columnHeaders.get(0), rowHeader, cell));
            }

            @Override
            public List<List<String>> result() {
                return adaptedCells;
            }

            @Override
            public boolean isFinished() {
                // process all cells
                return false;
            }
        };

        final List<List<String>> cells = visitCells(collatingCellAdapter);

        cells.add(0, headers);

        return cells;
    }

    @Override
    public <T> List<T> asPivot(final CellAdapter<T> adapter) {

        final CellVisitor<List<T>> collatingCellAdapter = new CellVisitor<List<T>>() {
            final List<T> adaptedCells = new LinkedList<>();

            @Override
            public void visit(final List<WebElement> columnHeaders, final WebElement rowHeader, final WebElement cell) {
                // Adapt the first column header only - maintains backward
                // compatability for CellAdapter
                adaptedCells.add(adapter.adapt(columnHeaders.get(0), rowHeader, cell));
            }

            @Override
            public List<T> result() {
                return adaptedCells;
            }

            @Override
            public boolean isFinished() {
                // process all cells
                return false;
            }
        };

        return visitCells(collatingCellAdapter);
    }

    @Override
    public <T> List<T> asPivot(final MultiCellAdapter<T> adapter) {
        final List<T> adaptedCells = new LinkedList<>();

        final List<WebElement> rows = findAllRows();

        for (final WebElement row : rows) {
            final List<WebElement> cells = row.findElements(cellLocator);

            final WebElement rowHeader = cells.remove(0);

            // +1 for index to ordinal
            // +roHeaderColspan to skip the row header column(s)
            final int columnOrdinalOffset = 1 + rowHeaderColspan;
            for (int i = 0; i < cells.size(); i++) {
                final List<WebElement> columnHeaders = cache.getElement().findElements(byColumn(i + columnOrdinalOffset));

                final WebElement cell = cells.get(i);

                adaptedCells.add(adapter.adapt(columnHeaders, rowHeader, cell));
            }
        }

        return adaptedCells;
    }

    private By byColumn(final int ordinal) {
        return By.cssSelector(String.format("thead th:nth-child(%s)", ordinal));
    }

    private List<WebElement> findAllRows() {
        return cache.getElement().findElements(rowLocator);
    }

    public List<WebElement> searchForRows(final Condition<WebElement> condition) {
        final Iterable<WebElement> allRows = Iterables.filter(findAllRows(), condition);
        return Lists.newArrayList(allRows);
    }

    @Override
    public MatchingCell firstMatch(final CellAdapter<Boolean> matcher) throws NoSuchElementException {

        final CellVisitor<MatchingCell> firstMatchVisitor = new CellVisitor<MatchingCell>() {

            private MatchingCell firstMatch = null;

            @Override
            public void visit(final List<WebElement> columnHeaders, final WebElement rowHeader, final WebElement cell) {

                for (final WebElement columnHeader : columnHeaders) {
                    if (matcher.adapt(columnHeader, rowHeader, cell)) {
                        firstMatch = new MatchingCell() {

                            @Override
                            public WebElement getColumn() {
                                return columnHeader;
                            }

                            @Override
                            public WebElement getRow() {
                                return rowHeader;
                            }

                            @Override
                            public WebElement getCell() {
                                return cell;
                            }

                        };

                        // We're done
                        return;
                    }
                }
            }

            @Override
            public MatchingCell result() {
                return firstMatch;
            }

            @Override
            public boolean isFinished() {
                return firstMatch != null;
            }

        };

        final MatchingCell result = visitCells(firstMatchVisitor);

        if (result == null) {
            throw new NoSuchElementException("No matching cell found");
        }

        return result;
    }

    private <T> T visitCells(final CellVisitor<T> v) {
        final List<WebElement> rows = findAllRows();

        for (final WebElement row : rows) {
            final List<WebElement> cells = row.findElements(cellLocator);

            final WebElement rowHeader = cells.remove(0);

            // +1 for index to ordinal
            // +roHeaderColspan to skip the row header column(s)
            final int columnOrdinalOffset = 1 + rowHeaderColspan;
            for (int i = 0; i < cells.size(); i++) {
                final List<WebElement> columnHeaders = cache.getElement().findElements(byColumn(i + columnOrdinalOffset));

                final WebElement cell = cells.get(i);
                v.visit(columnHeaders, rowHeader, cell);

                if (v.isFinished()) {
                    return v.result();
                }

            }
        }

        return v.result();
    }

    private interface CellVisitor<T> {
        /**
         * Visit a particular cell
         *
         * @param columnHeaders
         * @param rowHeader
         * @param cell
         */
        void visit(List<WebElement> columnHeaders, WebElement rowHeader, WebElement cell);

        /**
         * Return the current result for this visitor.
         *
         * @return
         */
        T result();

        /**
         * Has this visitor finished processing? Allows a shortcut to prevent traversing across all cells.
         * <p>
         * If all cells should be traversed, return false.
         *
         * @return
         */
        boolean isFinished();
    }

}
