<%@include file="./init.jsp" %>

<liferay-portlet:renderURL varImpl="searchURL">
    <portlet:param name="mvcPath" value="/web/search.jsp"/>
</liferay-portlet:renderURL>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/web/view.jsp"/>
</portlet:renderURL>

<clay:management-toolbar
        selectable="false"
        searchActionURL="${searchURL}"
        clearResultsURL="${viewURL}"
        itemsTotal="${fn:length(entries)}"
/>

<liferay-ui:search-container delta="10" emptyResultsMessage="no-entries-were-found" total="${fn:length(entries)}">
    <liferay-ui:search-container-results results="${entries}"/>

    <liferay-ui:search-container-row
            className="br.com.objective.training.model.Entry"
            keyProperty="entryId"
            modelVar="entry"
            escapedModel="${true}"
    >
        <liferay-ui:search-container-column-text name="guestbook" value="${guestbookMap[entry.guestbookId]}"/>
        <liferay-ui:search-container-column-text property="message"/>
        <liferay-ui:search-container-column-text property="name"/>
        <liferay-ui:search-container-column-jsp path="/web/actions.jsp" align="right"/>
    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator/>
</liferay-ui:search-container>