package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.converter.CamelCaseFieldNameConverter;
import io.magentys.cinnamon.converter.CollectionsToMapConverter;
import io.magentys.cinnamon.converter.ListConverter;
import io.magentys.cinnamon.converter.MapToObjectReflectionConverter;
import io.magentys.cinnamon.webdriver.elements.Table.RowAdapter;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

import static io.magentys.cinnamon.webdriver.elements.WebElementConverter.elementConverter;

/**
 * Adapter to convert a row of cells into an object based perform the column headings.
 * <p>
 * Each column heading shall be converted into a camel case field name. The given AdaptedType should contain String
 * fields for each of the converted column headers. Any converted field names not found shall be ignored. The conversion
 * process uses reflection to set the properties.
 */
final class ColumnHeadingToClassFieldMappingAdapter<AdaptedType> implements RowAdapter<AdaptedType> {
    private final Class<AdaptedType> type;
    private final ListConverter<String, String> columnHeaderToFieldNameConverter;
    private List<String> fieldNames;

    ColumnHeadingToClassFieldMappingAdapter(final Class<AdaptedType> type) {
        this.type = type;
        columnHeaderToFieldNameConverter = new ListConverter<>(new CamelCaseFieldNameConverter());
    }

    @Override
    public AdaptedType adapt(final List<WebElement> columnHeaderElements, final List<WebElement> cells) {
        convertColumnHeadersOnceOnly(columnHeaderElements);

        final List<String> cellTexts = elementConverter().getTextsFrom(cells);
        final Map<String, String> map = new CollectionsToMapConverter().convert(fieldNames, cellTexts);
        final MapToObjectReflectionConverter<AdaptedType> converter = new MapToObjectReflectionConverter<>(type);

        return converter.convert(map);
    }

    private void convertColumnHeadersOnceOnly(final List<WebElement> columnHeaderElements) {
        // Lazily convert the column headers - they should always be the same
        if (fieldNames == null) {
            final List<String> columnHeaders = elementConverter().getTextsFrom(columnHeaderElements);
            fieldNames = columnHeaderToFieldNameConverter.convert(columnHeaders);
        }
    }
}