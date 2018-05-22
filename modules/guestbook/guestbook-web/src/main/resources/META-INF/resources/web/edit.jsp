<%@ include file="./init.jsp" %>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/web/view.jsp"/>
</portlet:renderURL>

<portlet:actionURL name='${entry eq null ? "addEntry" : "updateEntry"}' var="actionURL"/>

<aui:form action="${actionURL}" name="fm">
    <aui:model-context bean="${entry}"/>

    <aui:fieldset>
        <aui:input name="name" value="${entry.name}"/>
        <aui:input name="email" value="${entry.email}"/>
        <aui:input name="message" value="${entry.message}"/>
        <aui:input name="entryId" type="hidden" value="${entry.entryId}"/>
        <aui:input name="guestbookId" type="hidden" value="${entry eq null ? guestbookId : entry.guestbookId}"/>
    </aui:fieldset>

    <liferay-ui:asset-categories-error/>
    <liferay-ui:asset-tags-error/>
    <liferay-ui:panel
            defaultState="closed"
            extended="${false}"
            id="entryCategorizationPanel"
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
            id="entryAssetLinksPanel"
            persistState="${true}"
            title="related-assets"
    >
        <aui:fieldset>
            <liferay-ui:input-asset-links
                    className="br.com.objective.training.model.Entry"
                    classPK="${entryId}"
            />
        </aui:fieldset>
    </liferay-ui:panel>


    <aui:button-row>
        <c:choose>
            <c:when test="${entry eq null}">
                <gb:if-guestbook-permission
                        permissionChecker="${permissionChecker}" guestbookId="${guestbookId}" actionId="ADD_ENTRY">
                    <aui:button type="submit"/>
                </gb:if-guestbook-permission>
            </c:when>
            <c:otherwise>
                <gb:if-entry-permission
                        permissionChecker="${permissionChecker}" entryId="${entry.entryId}" actionId="UPDATE">
                    <aui:button type="submit"/>
                </gb:if-entry-permission>
            </c:otherwise>
        </c:choose>
        <aui:button type="cancel" onClick="${viewURL}"/>
    </aui:button-row>
</aui:form>