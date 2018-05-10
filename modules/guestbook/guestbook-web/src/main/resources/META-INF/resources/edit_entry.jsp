<%@ include file="/init.jsp" %>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/view.jsp"/>
</portlet:renderURL>

<portlet:actionURL name="addEntry" var="addEntryURL"/>

<aui:form action="${addEntryURL}" name="fm">
    <aui:model-context bean="${entry}" model="${br.com.objective.training.model.Entry.class}" />

    <aui:fieldset>
        <aui:input name="name" />
        <aui:input name="email" />
        <aui:input name="message" />
        <aui:input name="entryId" type="hidden" />
        <aui:input name="guestbookId" type="hidden" value="${entry eq null ? guestbookId : entry.guestbookId}"/>
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"/>
        <aui:button type="cancel" onClick="${viewURL}"/>
    </aui:button-row>
</aui:form>