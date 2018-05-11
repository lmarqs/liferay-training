<%@include file="./init.jsp" %>

<jsp:useBean id="SEARCH_CONTAINER_RESULT_ROW" class="com.liferay.portal.kernel.dao.search.ResultRow" scope="request"/>
<c:set var="guestbook" value="${SEARCH_CONTAINER_RESULT_ROW.object}"/>

<liferay-ui:icon-menu>

    <portlet:renderURL var="editURL">
        <portlet:param name="guestbookId" value="guestbook.guestbookId}"/>
        <portlet:param name="mvcPath" value="/admin/edit.jsp"/>
    </portlet:renderURL>

    <liferay-ui:icon image="edit" message="Edit" url="${editURL}"/>

    <portlet:actionURL name="deleteGuestbook" var="deleteURL">
        <portlet:param name="guestbookId" value="${guestbook.guestbookId}"/>
    </portlet:actionURL>

    <liferay-ui:icon-delete url="${deleteURL}"/>

</liferay-ui:icon-menu>