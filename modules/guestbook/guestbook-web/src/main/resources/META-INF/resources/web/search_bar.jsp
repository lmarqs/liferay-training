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
            <aui:input autocomplete="off" name="from" placeholder="MM/dd/yyyy" value="${from}"/>
        </div>
        <div class="col-md-5">
            <aui:input autocomplete="off" name="to" placeholder="MM/dd/yyyy" value="${to}"/>
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

<script type="text/javascript">
    AUI().use("aui-datepicker", function (A) {
        new A.DatePicker({
            trigger: "[placeholder='MM/dd/yyyy']",
            mask: "%m/%d/%Y",
            popover: {
                zIndex: 1E3
            }
        })
    });
</script>