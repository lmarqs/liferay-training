<%@ include file="./init.jsp" %>

<c:set var="entry" value="${SEARCH_CONTAINER_RESULT_ROW.object}"/>

<liferay-ui:icon-menu>

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

    <gb:if-entry-permission permissionChecker="${permissionChecker}" entryId="${entry.entryId}" actionId="PERMISSIONS">
        <liferay-security:permissionsURL
                modelResource="br.com.objective.training.model.Entry"
                modelResourceDescription="${entry.message}"
                resourcePrimKey="${entry.entryId}"
                var="permissionsURL"/>
        <liferay-ui:icon image="permissions" url="${permissionsURL}"/>
    </gb:if-entry-permission>

</liferay-ui:icon-menu>