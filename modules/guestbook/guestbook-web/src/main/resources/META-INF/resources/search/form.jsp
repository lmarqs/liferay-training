<aui:form action="${searchURL}" name="fm">

	<div class="row">
		<div class="col-md-2">
			<aui:select name="fieldName" value="${fieldName}">
				<aui:option label="Modified" value="modified" />
				<aui:option label="Event Date" value="guestbookEventDate" />
				<aui:option label="Created" value="created" />
			</aui:select>
		</div>

		<div class="col-md-5">
			<c:set value="MM/dd/yyyy or Date Math Expression" var="ph" />

			<aui:input autocomplete="off" cssClass="aui-datepicker" name="from" placeholder="${ph}" value="${from}" />
		</div>

		<div class="col-md-5">
			<aui:input autocomplete="off" cssClass="aui-datepicker" name="to" placeholder="${ph}" value="${to}" />
		</div>
	</div>

	<div class="row">
		<div class="col-md-8">
			<aui:input inlineLabel="left" label="" name="keywords" placeholder="search-entries" size="256" />
		</div>

		<div class="col-md-4">
			<aui:button type="submit" value="search" />
		</div>
	</div>
</aui:form>