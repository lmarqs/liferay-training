<%@ include file="./init.jsp" %>

<p>
    <b><liferay-ui:message key="msg.caption"/></b>
</p>

<liferay-ui:success key="entryAdded" message="msg.entry-added"/>
<liferay-ui:success key="entryUpdated" message="msg.entry-updated"/>
<liferay-ui:success key="entryDeleted" message="msg.entry-deleted"/>

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
/>

<clay:navigation-bar navigationItems="${navigationItems}"/>

<gb:if-guestbook-permission
        permissionChecker="${permissionChecker}" guestbookId="${guestbookId}" actionId="VIEW">
    <liferay-ui:search-container total="${total}">
        <liferay-ui:search-container-results results="${results}"/>

        <liferay-ui:search-container-row className="br.com.objective.training.model.Entry" modelVar="entry">

            <gb:if-entry-permission
                    permissionChecker="${permissionChecker}" entryId="${entry.entryId}" actionId="VIEW">
                <liferay-ui:search-container-column-text property="message"/>
                <liferay-ui:search-container-column-text property="name"/>
                <liferay-ui:search-container-column-text property="email"/>
                <liferay-ui:search-container-column-status property="status"/>
                <liferay-ui:search-container-column-jsp align="right" path="/web/actions.jsp"/>
            </gb:if-entry-permission>
        </liferay-ui:search-container-row>

        <liferay-ui:search-iterator/>
    </liferay-ui:search-container>
</gb:if-guestbook-permission>

<gb:if-guestbook-permission
        permissionChecker="${permissionChecker}"
        guestbookId="${guestbookId}"
        actionId="ADD_ENTRY">
    <portlet:renderURL var="addEntryURL">
        <portlet:param name="mvcPath" value="/web/edit.jsp"/>
        <portlet:param name="guestbookId" value="${guestbookId}"/>
    </portlet:renderURL>

    <aui:button-row>
        <aui:button onClick="${addEntryURL}" value="Add Entry"/>
    </aui:button-row>
</gb:if-guestbook-permission>