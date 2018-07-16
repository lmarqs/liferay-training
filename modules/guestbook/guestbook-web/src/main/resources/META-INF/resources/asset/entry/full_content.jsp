<%@ include file="../init.jsp" %>

<dl>
	<dt>Guestbook</dt>
	<dd><c:out value="${guestbook.name}" /></dd>
	<dt>Name</dt>
	<dd><c:out value="${entry.name}" /></dd>
	</dd>
	<dt>Message</dt>
	<dd><c:out value="${entry.message}" /></dd>
	</dd>
</dl>