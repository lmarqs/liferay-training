<aui:form action="${searchURL}" method="post" name="fm">

    <div class="row">
        <div class="col-md-2">
            <aui:select name="fieldName" value='${fieldName}'>
                <aui:option label="Modified" value="modified"/>
                <aui:option label="Event Date" value="guestbookEventDate"/>
                <aui:option label="Created" value="created"/>
            </aui:select>
        </div>
        <div class="col-md-5">
            <c:set var="ph" value="MM/dd/yyyy or Date Math Expression"/>
            <aui:input name="from" value="${from}" cssClass="aui-datepicker" placeholder="${ph}" autocomplete="off"/>
        </div>
        <div class="col-md-5">
            <aui:input name="to" value="${to}" cssClass="aui-datepicker" placeholder="${ph}" autocomplete="off"/>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8">
            <aui:input name="keywords" label="" inlineLabel="left" placeholder="search-entries" size="256"/>
        </div>
        <div class="col-md-4">
            <aui:button type="submit" value="search"/>
        </div>
    </div>

</aui:form>