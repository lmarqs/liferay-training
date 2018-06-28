<%@ include file="./init.jsp" %>

<liferay-ui:success key="guestbookAdded" message="msg.guestbook-added" />
<liferay-ui:success key="guestbookUpdated" message="msg.guestbook-updated" />
<liferay-ui:success key="guestbookDeleted" message="msg.guestbook-deleted" />

<liferay-ui:search-container total="${total}">
    <liferay-ui:search-container-results results="${results}"/>

    <liferay-ui:search-container-row className="com.liferay.blade.samples.guestbook.model.Guestbook" modelVar="guestbook">
        <gb:if-guestbook-permission
                permissionChecker="${permissionChecker}" guestbookId="${guestbook.guestbookId}" actionId="VIEW">
            <liferay-ui:search-container-column-text property="name"/>
            <liferay-ui:search-container-column-status property="status" />
            <liferay-ui:search-container-column-jsp align="right" path="/admin/actions.jsp"/>
        </gb:if-guestbook-permission>
    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator/>
</liferay-ui:search-container>

<aui:button-row>
    <portlet:renderURL var="addGuestbookURL">
        <portlet:param name="mvcPath" value="/admin/edit.jsp"/>
        <portlet:param name="redirect" value="currentURL"/>
    </portlet:renderURL>
    <aui:button onClick="${addGuestbookURL}" value="Add Guestbook"/>
</aui:button-row>