<%@ include file="./init.jsp" %>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/web/view.jsp"/>
</portlet:renderURL>

<portlet:actionURL name="addEntry" var="addEntryURL"/>

<aui:form action="${addEntryURL}" name="fm">
    <aui:model-context bean="${entry}" model="${br.com.objective.training.model.Entry.class}"/>

    <aui:fieldset>
        <aui:input name="name" value="${entry.name}"/>
        <aui:input name="email" value="${entry.email}"/>
        <aui:input name="message" value="${entry.message}"/>
        <aui:input name="entryId" type="hidden" value="${entry.entryId}"/>
        <aui:input name="guestbookId" type="hidden" value="${entry eq null ? guestbookId : entry.guestbookId}"/>
    </aui:fieldset>

    <aui:button-row>
        <gb:if-entry-permission permissionChecker="${permissionChecker}" entryId="${entry.entryId}" actionId="UPDATE">
            <aui:button type="submit"/>
        </gb:if-entry-permission>
        <aui:button type="cancel" onClick="${viewURL}"/>
    </aui:button-row>
</aui:form>