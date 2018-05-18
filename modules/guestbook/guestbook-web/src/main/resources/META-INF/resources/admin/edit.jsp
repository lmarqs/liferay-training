<%@include file="./init.jsp" %>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/admin/view.jsp"/>
</portlet:renderURL>

<portlet:actionURL name='${guestbook eq null ? "addGuestbook" : "updateGuestbook"}' var="actionURL"/>

<aui:form action="${actionURL}" name="fm">

    <aui:model-context bean="${guestbook}" model="${br.com.objective.training.model.Guestbook.class}"/>

    <aui:input type="hidden" name="guestbookId" value='${guestbook.guestbookId}'/>

    <aui:fieldset>
        <aui:input name="name" value='${guestbook.name}'/>
    </aui:fieldset>

    <liferay-ui:asset-categories-error/>
    <liferay-ui:asset-tags-error/>
    <liferay-ui:panel
            defaultState="closed"
            extended="${false}"
            id="guestbookCategorizationPanel"
            persistState="${true}"
            title="categorization"
    >
        <aui:fieldset>
            <aui:input name="categories" type="assetCategories"/>
            <aui:input name="tags" type="assetTags"/>
        </aui:fieldset>
    </liferay-ui:panel>

    <liferay-ui:panel
            defaultState="closed"
            extended="${false}"
            id="guestbookAssetLinksPanel"
            persistState="${true}"
            title="related-assets">
        <aui:fieldset>
            <liferay-ui:input-asset-links
                    className="br.com.objective.training.model.Guestbook"
                    classPK="${guestbookId}"/>
        </aui:fieldset>
    </liferay-ui:panel>

    <aui:button-row>
        <aui:button type="submit"/>
        <aui:button onClick="${viewURL}" type="cancel"/>
    </aui:button-row>
</aui:form>