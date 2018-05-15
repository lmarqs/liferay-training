<%@ include file="./init.jsp" %>

<p>
    <b><liferay-ui:message key="guestbook-web.caption"/></b>
</p>

<aui:nav>
    <c:forEach items="${guestbooks}" var="guestbook">
        <portlet:renderURL var="viewPageURL">
            <portlet:param name="mvcPath" value="/web/view.jsp"/>
            <portlet:param name="guestbookId" value="${guestbook.guestbookId}"/>
        </portlet:renderURL>

        <c:choose>
            <c:when test="${guestbook.guestbookId eq guestbookId}">
                <c:set var="cssClass" value="active"/>
            </c:when>
            <c:otherwise>
                <c:set var="cssClass" value=""/>
            </c:otherwise>
        </c:choose>

        <aui:nav-item cssClass="${cssClass}" href="${viewPageURL}" label="${guestbook.name}"/>
    </c:forEach>
</aui:nav>

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

<gb:if-guestbook-permission permissionChecker="${permissionChecker}" guestbookId="${guestbookId}" actionId="ADD_ENTRY">
    <portlet:renderURL var="addEntryURL">
        <portlet:param name="mvcPath" value="/web/edit.jsp"/>
        <portlet:param name="guestbookId" value="${guestbookId}"/>
    </portlet:renderURL>

    <aui:button-row>
        <aui:button onClick="${addEntryURL}" value="Add Entry"/>
    </aui:button-row>
</gb:if-guestbook-permission>