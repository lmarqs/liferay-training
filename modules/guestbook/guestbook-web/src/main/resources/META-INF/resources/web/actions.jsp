<%@include file="./init.jsp" %>

<c:set var="entry" value="${SEARCH_CONTAINER_RESULT_ROW.object}"/>


<liferay-ui:icon-menu>

    <portlet:renderURL var="editURL">
        <portlet:param name="entryId" value="${entry.entryId}"/>
        <portlet:param name="mvcPath" value="/web/edit.jsp"/>
    </portlet:renderURL>

    <liferay-ui:icon image="edit" message="Edit" url="${editURL}"/>

    <portlet:actionURL name="deleteEntry" var="deleteURL">
        <portlet:param name="guestbookId" value="${entry.entryId}"/>
    </portlet:actionURL>

    <liferay-ui:icon-delete url="${deleteURL}"/>

</liferay-ui:icon-menu>