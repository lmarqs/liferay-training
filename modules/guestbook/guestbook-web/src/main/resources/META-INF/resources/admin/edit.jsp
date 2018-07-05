<%@include file="./init.jsp" %>

<div id="<portlet:namespace />" class="container">
    <div class="col-sm"></div>
    <div class="col-sm">
        <div class="card">
            <div class="card-body">

                <portlet:actionURL name='${guestbook eq null ? "addGuestbook" : "updateGuestbook"}' var="actionURL"/>
                <aui:form action="${actionURL}" name="fm">

                    <aui:model-context bean="${guestbook}"
                                       model="<%= com.liferay.blade.samples.guestbook.model.Guestbook.class %>"/>

                    <aui:input type="hidden" name="guestbookId" value='${guestbook.guestbookId}'/>

                    <aui:fieldset>
                        <aui:input name="name" value='${guestbook.name}'>
                            <aui:validator name="required"/>
                        </aui:input>

                        <aui:input name="eventDate" value='${eventDate}'>
                            <aui:validator name="date"/>
                        </aui:input>

                        <aui:input name="priority" value='${guestbook.priority}' type="number" min="1000" max="99999">
                            <aui:validator name="number"/>
                            <aui:validator name="min">1000</aui:validator>
                            <aui:validator name="max">99999</aui:validator>
                        </aui:input>

                        <aui:input name="note" value='${guestbook.note}' type="textarea"/>
                    </aui:fieldset>

                    <liferay-ui:asset-categories-error/>
                    <liferay-ui:asset-tags-error/>
                    <liferay-ui:panel
                            defaultState="closed"
                            extended="${false}"
                            id="guestbook-categorization-panel"
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
                            id="guestbook-asset-links-panel"
                            persistState="${true}"
                            title="related-assets">
                        <aui:fieldset>
                            <liferay-ui:input-asset-links
                                    className="com.liferay.blade.samples.guestbook.model.Guestbook"
                                    classPK="${guestbookId}"/>
                        </aui:fieldset>
                    </liferay-ui:panel>

                    <aui:button-row>

                        <aui:button type="submit"/>

                        <portlet:renderURL var="viewURL">
                            <portlet:param name="mvcPath" value="/admin/view.jsp"/>
                        </portlet:renderURL>
                        <aui:button onClick="${viewURL}" type="cancel"/>

                    </aui:button-row>
                </aui:form>
            </div>
        </div>
    </div>
    <div class="col-sm"></div>
</div>
