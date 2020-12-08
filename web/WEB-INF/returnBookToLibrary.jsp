<%-- 
    Document   : returnBookToLibrary
    Created on : 08.12.2020, 12:55:30
    Author     : A
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Вернуть книгу</title>
    </head>
    <body>
        <h1>Вернуть книгу в библиотеку</h1>
        <p>${info}</p>
        <form action="returnBook" method="POST">
        <select name="historyId" multiple="true">
                <option value="">выберите книгу</option>
                <c:forEach var="history" items="${listHistoriesWithReadBook}">
                    <option value="${history.id}">"${history.book.name}" читает ${history.reader.name} ${history.reader.lastname} </option>
                </c:forEach>
            </select>
            <br>
            <input type="submit" value="вернуть книгу">
        </form>
        
    </body>
</html>
