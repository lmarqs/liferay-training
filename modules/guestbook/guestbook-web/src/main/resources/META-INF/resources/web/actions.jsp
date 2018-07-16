<%@ include file="./init.jsp" %>

<c:set value="${SEARCH_CONTAINER_RESULT_ROW.object}" var="entry" />

<liferay-ui:icon-menu>
	<portlet:renderURL var="viewEntryURL">
		<portlet:param name="entryId" value="${entry.entryId}" />
		<portlet:param name="mvcPath" value="/web/edit.jsp" />
	</portlet:renderURL>

	<liferay-ui:icon message="View" url="${viewEntryURL}" />

	<gb:if-entry-permission actionId="UPDATE" entryId="${entry.entryId}" permissionChecker="${permissionChecker}">
		<portlet:renderURL var="editURL">
			<portlet:param name="entryId" value="${entry.entryId}" />
			<portlet:param name="mvcPath" value="/web/edit.jsp" />
		</portlet:renderURL>

		<liferay-ui:icon image="edit" message="Edit" url="${editURL}" />
	</gb:if-entry-permission>

	<gb:if-entry-permission actionId="DELETE" entryId="${entry.entryId}" permissionChecker="${permissionChecker}">
		<portlet:actionURL name="deleteEntry" var="deleteURL">
			<portlet:param name="entryId" value="${entry.entryId}" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="${deleteURL}" />
	</gb:if-entry-permission>

	<gb:if-entry-permission actionId="PERMISSIONS" entryId="${entry.entryId}" permissionChecker="${permissionChecker}">
		<liferay-security:permissionsURL
			modelResource="com.liferay.blade.samples.guestbook.model.Entry"
			modelResourceDescription="${entry.message}"
			resourcePrimKey="${entry.entryId}"
			var="permissionsURL"
		/>

		<liferay-ui:icon image="permissions" url="${permissionsURL}" />
	</gb:if-entry-permission>
</liferay-ui:icon-menu>