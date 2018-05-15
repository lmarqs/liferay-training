<%@include file="./init.jsp" %>

<c:set var="guestbook" value="${SEARCH_CONTAINER_RESULT_ROW.object}" scope="request"/>

<liferay-ui:icon-menu>

    <portlet:renderURL var="editURL">
        <portlet:param name="guestbookId" value="${guestbook.guestbookId}"/>
        <portlet:param name="mvcPath" value="/admin/edit.jsp"/>
    </portlet:renderURL>

    <liferay-ui:icon image="edit" message="Edit" url="${editURL}"/>

    <portlet:actionURL name="deleteGuestbook" var="deleteURL">
        <portlet:param name="guestbookId" value="${guestbook.guestbookId}"/>
    </portlet:actionURL>

    <liferay-ui:icon-delete url="${deleteURL}"/>

</liferay-ui:icon-menu>