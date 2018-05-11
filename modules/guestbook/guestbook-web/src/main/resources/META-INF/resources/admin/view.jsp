<%@ include file="./init.jsp" %>

<liferay-ui:search-container total="${total}">
    <liferay-ui:search-container-results results="${results}"/>

    <liferay-ui:search-container-row className="br.com.objective.training.model.Guestbook" modelVar="guestbook">
        <liferay-ui:search-container-column-text property="name"/>
        <liferay-ui:search-container-column-jsp align="right" path="./actions.jsp"/>
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