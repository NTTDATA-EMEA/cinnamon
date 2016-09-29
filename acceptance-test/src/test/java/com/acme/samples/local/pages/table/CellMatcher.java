package com.acme.samples.local.pages.table;

import com.acme.samples.local.stepdef.TableMatchParams;
import io.magentys.cinnamon.webdriver.elements.TableElement.CellAdapter;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

final class CellMatcher implements CellAdapter<Boolean> {

    private final TableMatchParams tableParams;

    CellMatcher(TableMatchParams tableParams) {
        this.tableParams = tableParams;
    }

    @Override
    public Boolean adapt(WebElement columnHeading, WebElement rowHeading, WebElement cell) {
        boolean colMatches = elementMatches(tableParams.getColumnHeading(), columnHeading);
        boolean rowMatches = elementMatches(tableParams.getRowHeading(), rowHeading);
        boolean valMatches = elementMatches(tableParams.getValue(), cell);

        return colMatches && rowMatches && valMatches;
    }

    private boolean elementMatches(final String expected, WebElement cell) {
        return !StringUtils.isNoneBlank(expected) || cell.getText().equals(expected);
    }
}