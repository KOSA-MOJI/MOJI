<%@ tag body-content="empty" pageEncoding="utf-8" %>
<%@ tag import="java.time.format.DateTimeFormatter" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="date" required="false" type="java.time.LocalDate" %>
<%@ attribute name="pattern" type="java.lang.String" %>
<%
    if (pattern == null) {
        pattern = "yyyy-MM-dd";
    }
%>
<%= date != null ? DateTimeFormatter.ofPattern(pattern).format(date) : "N/A" %>
