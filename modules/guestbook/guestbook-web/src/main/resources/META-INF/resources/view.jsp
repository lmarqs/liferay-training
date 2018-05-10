<%@ include file="/init.jsp" %>


<p>
    <b><liferay-ui:message key="guestbook-web.caption"/></b>
</p>

<jsp:useBean id="entries" class="java.util.ArrayList" scope="request"/>

<liferay-ui:search-container>
    <liferay-ui:search-container-results results="${entries}"/>


    <liferay-ui:search-container-row className="br.com.objective.training.web.model.Entry" modelVar="entry">

        <liferay-ui:search-container-column-text property="message"/>
        <liferay-ui:search-container-column-text property="name"/>


        <portlet:actionURL name="removeEntry" var="removeEntryURL">
            <portlet:param name="name" value="${entry.name}"/>
            <portlet:param name="message" value="${entry.message}"/>
        </portlet:actionURL>

        <c:set var="removeEntryURL" value="location = '${removeEntryURL}'"/>
        <liferay-ui:search-container-column-button href="${removeEntryURL}"/>

    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator/>
</liferay-ui:search-container>


<portlet:renderURL var="addEntryURL">
    <portlet:param name="mvcPath" value="/edit_entry.jsp"/>
</portlet:renderURL>

<aui:button-row>
    <aui:button onClick="${addEntryURL}" value="Add Entry"/>
</aui:button-row>

