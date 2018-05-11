<%@ include file="./init.jsp" %>


<p>
    <b><liferay-ui:message key="guestbook-web.caption"/></b>
</p>

<liferay-ui:search-container total="${total}">
    <liferay-ui:search-container-results results="${results}"/>

    <liferay-ui:search-container-row className="br.com.objective.training.model.Entry" modelVar="entry">

        <liferay-ui:search-container-column-text property="message"/>
        <liferay-ui:search-container-column-text property="name"/>
        <liferay-ui:search-container-column-text property="email"/>
        <liferay-ui:search-container-column-jsp align="right" path="/web/actions.jsp"/>

    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator/>
</liferay-ui:search-container>

<portlet:renderURL var="addEntryURL">
    <portlet:param name="mvcPath" value="/web/edit.jsp"/>
    <portlet:param name="guestbookId" value="${guestbookId}" />
</portlet:renderURL>

<aui:button-row>
    <aui:button onClick="${addEntryURL}" value="Add Entry"/>
</aui:button-row>

