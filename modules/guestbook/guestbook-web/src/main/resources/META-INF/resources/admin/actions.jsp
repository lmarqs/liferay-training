<%@ include file="./init.jsp" %>

<c:set scope="request" value="${SEARCH_CONTAINER_RESULT_ROW.object}" var="guestbook" />

<liferay-ui:icon-menu>
	<portlet:renderURL var="editURL">
		<portlet:param name="guestbookId" value="${guestbook.guestbookId}" />
		<portlet:param name="mvcPath" value="/admin/edit.jsp" />
	</portlet:renderURL>

	<liferay-ui:icon image="edit" message="Edit" url="${editURL}" />

	<portlet:actionURL name="deleteGuestbook" var="deleteURL">
		<portlet:param name="guestbookId" value="${guestbook.guestbookId}" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="${deleteURL}" />

	<portlet:renderURL var="viewGuessbookURL">
		<portlet:param name="entryId" value="${guestbook.guestbookId}" />
		<portlet:param name="mvcPath" value="/admin/edit.jsp" />
	</portlet:renderURL>

	<liferay-ui:icon message="View" url="${viewGuessbookURL}" />

	<gb:if-guestbook-permission actionId="PERMISSIONS" guestbookId="${guestbook.guestbookId}" permissionChecker="${permissionChecker}">
		<liferay-security:permissionsURL
			modelResource="com.liferay.blade.samples.guestbook.model.Guestbook"
			modelResourceDescription="${guestbook.name}"
			resourcePrimKey="${guestbook.guestbookId}"
			var="permissionsURL"
		/>

		<liferay-ui:icon image="permissions" url="${permissionsURL}" />
	</gb:if-guestbook-permission>
</liferay-ui:icon-menu>