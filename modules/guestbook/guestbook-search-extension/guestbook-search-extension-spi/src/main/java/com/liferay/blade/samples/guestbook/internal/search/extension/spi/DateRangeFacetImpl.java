package com.liferay.blade.samples.guestbook.internal.search.extension.spi;

import com.liferay.blade.samples.guestbook.internal.search.extension.api.DateRangeFacet;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.filter.FilterBuilders;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateRangeFacetImpl extends RangeFacet implements DateRangeFacet {

    DateRangeFacetImpl(String fieldName, SearchContext searchContext, FilterBuilders filterBuilders) {
        super(searchContext);

        setFieldName(fieldName);

        _filterBuilders = filterBuilders;
    }


    @Override
    protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {

        if (Validator.isNull(_from) && Validator.isNull(_to)) {
            return null;
        }

        DateRangeFilterBuilder dateRangeFilterBuilder = _filterBuilders.dateRangeFilterBuilder();

        dateRangeFilterBuilder.setFieldName(getFieldName());

        if (Validator.isNotNull(_from)) {
            dateRangeFilterBuilder.setFrom(_from);
        }

        if (Validator.isNotNull(_to)) {
            dateRangeFilterBuilder.setTo(_to);
        }

        dateRangeFilterBuilder.setIncludeLower(true);
        dateRangeFilterBuilder.setIncludeUpper(true);

        return new BooleanClauseImpl<>(dateRangeFilterBuilder.build(), BooleanClauseOccur.MUST);
    }

    @Override
    public String getAggregationName() {
        return getFieldName();
    }

    @Override
    public String[] getSelections() {
        return new String[0];
    }

    @Override
    public void select(String... selections) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAggregationName(String aggregationName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFrom(String from) {
        _from = formatDate(from);
    }

    @Override
    public void setTo(String to) {
        _to = formatDate(to);
    }

    private String formatDate(String date) {
        try {
            DateFormat df1 = new SimpleDateFormat("yyyyMMdd000000");
            DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

            return df1.format(df2.parse(date));
        } catch (ParseException ignored) {
            // silence is golden
        }
        return date;
    }

    private final FilterBuilders _filterBuilders;
    private String _from;
    private String _to;

}