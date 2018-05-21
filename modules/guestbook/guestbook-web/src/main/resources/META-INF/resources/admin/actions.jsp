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


    <portlet:renderURL var="viewGuessbookURL">
        <portlet:param name="entryId" value="${guestbook.guestbookId}"/>
        <portlet:param name="mvcPath" value="/admin/edit.jsp"/>
    </portlet:renderURL>
    <liferay-ui:icon message="View" url="${viewGuessbookURL}"/>


    <gb:if-guestbook-permission permissionChecker="${permissionChecker}" guestbookId="${guestbook.guestbookId}" actionId="PERMISSIONS">
        <liferay-security:permissionsURL
                modelResource="br.com.objective.training.model.Guestbook"
                modelResourceDescription="${guestbook.name}"
                resourcePrimKey="${guestbook.guestbookId}"
                var="permissionsURL"/>
        <liferay-ui:icon image="permissions" url="${permissionsURL}"/>
    </gb:if-guestbook-permission>
</liferay-ui:icon-menu>