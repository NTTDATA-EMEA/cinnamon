package com.acme.samples.local.context;

import io.magentys.cinnamon.webdriver.elements.TableElement.MatchingCell;

import java.util.List;

public class TableContext {

    private String rowContent = "";
    private List<Object[]> adapted;
    private MatchingCell matchingCell;

    public String getRowContent() {
        return rowContent;
    }

    public void setRowContent(final String rowContent) {
        this.rowContent = rowContent;
    }

    public List<Object[]> getAdapted() {
        return adapted;
    }

    public void setAdapted(final List<Object[]> adapted) {
        this.adapted = adapted;
    }

    public void setMatchingCell(MatchingCell matchingCell) {
        this.matchingCell = matchingCell;

    }

    public MatchingCell getMatchingCell() {
        return matchingCell;
    }
}
