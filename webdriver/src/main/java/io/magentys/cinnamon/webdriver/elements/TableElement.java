package io.magentys.cinnamon.webdriver.elements;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface TableElement extends PageElement, Table {

    /**
     * Adapter to convert a cell in a pivot table into a given type
     *
     *
     * @param <T>
     */
    interface CellAdapter<T> {
        /**
         * @param columnHeading The WebElements which represents the column headings for the given cell. This caters for
         *            the situation where a cell contains multiple column heading rows
         * @param rowHeading The WebElement containing the row heading for the given cell, as specified by the first
         *            cell for a given row
         * @param cell The WebElement for the actual cell to convert
         * @return An adapted cell
         */
        T adapt(WebElement columnHeading, WebElement rowHeading, WebElement cell);
    }

    /**
     * Adapter to convert a cell in a pivot table into a given type. This adapter is for use where the table is complex,
     * containing multiple rows for the column header
     *
     *
     * @param <T>
     */
    interface MultiCellAdapter<T> {
        /**
         * @param columnHeading The WebElements which represents the column headings for the given cell. This caters for
         *            the situation where a cell contains multiple column heading rows
         * @param rowHeading The WebElement containing the row heading for the given cell, as specified by the first
         *            cell for a given row
         * @param cell The WebElement for the actual cell to convert
         * @return An adapted cell
         */
        T adapt(List<WebElement> columnHeading, WebElement rowHeading, WebElement cell);
    }

    interface MatchingCell {
        WebElement getColumn();

        WebElement getRow();

        WebElement getCell();
    }

}
