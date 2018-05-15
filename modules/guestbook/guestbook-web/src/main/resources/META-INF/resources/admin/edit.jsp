<%@include file = "./init.jsp" %>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/admin/view.jsp" />
</portlet:renderURL>

<portlet:actionURL name='${guestbook eq null ? "addGuestbook" : "updateGuestbook"}' var="actionURL" />

<aui:form action="${actionURL}" name="fm">

    <aui:model-context bean="${guestbook}" model="${br.com.objective.training.model.Guestbook.class}" />

    <aui:input type="hidden" name="guestbookId" value='${guestbook.guestbookId}' />

    <aui:fieldset>
        <aui:input name="name"  value='${guestbook.name}' />
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" />
        <aui:button onClick="${viewURL}" type="cancel"  />
    </aui:button-row>
</aui:form>