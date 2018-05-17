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


    <portlet:renderURL var="viewEntryURL">
        <portlet:param name="entryId" value="${entry.entryId}"/>
        <portlet:param name="mvcPath" value="/web/edit.jsp"/>
    </portlet:renderURL>
    <liferay-ui:icon message="View" url="${viewEntryURL}"/>


    <gb:if-entry-permission permissionChecker="${permissionChecker}" entryId="${entry.entryId}" actionId="UPDATE">
        <portlet:renderURL var="editURL">
            <portlet:param name="entryId" value="${entry.entryId}"/>
            <portlet:param name="mvcPath" value="/web/edit.jsp"/>
        </portlet:renderURL>
        <liferay-ui:icon image="edit" message="Edit" url="${editURL}"/>
    </gb:if-entry-permission>


    <gb:if-entry-permission permissionChecker="${permissionChecker}" entryId="${entry.entryId}" actionId="DELETE">
        <portlet:actionURL name="deleteEntry" var="deleteURL">
            <portlet:param name="entryId" value="${entry.entryId}"/>
        </portlet:actionURL>
        <liferay-ui:icon-delete url="${deleteURL}"/>
    </gb:if-entry-permission>

    <gb:if-guestbook-permission permissionChecker="${permissionChecker}" guestbookId="${guestbook.guestbookId}" actionId="PERMISSIONS">
        <liferay-security:permissionsURL
                modelResource="br.com.objective.training.model.Guestbook"
                modelResourceDescription="${guestbook.name}"
                resourcePrimKey="${guestbook.guestbookId}"
                var="permissionsURL"/>
        <liferay-ui:icon image="permissions" url="${permissionsURL}"/>
    </gb:if-guestbook-permission>
</liferay-ui:icon-menu>