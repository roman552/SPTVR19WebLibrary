<%-- 
    Document   : giveBookToReader
    Created on : 01.12.2020, 14:42:09
    Author     : user
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Выдать книгу читателю</title>
    </head>
    <body>
        <h1>Выдать книгу читателю</h1>
        <form action="giveBook" method="POST">
        <select name="bookId">
                <option value="">выберите книгу</option>
                <c:forEach var="book" items="${listBooks}">
                    <option value="${book.id}">"${book.name}". ${book.author}. ${book.publishedYear} </option>
                </c:forEach>
            </select>   
        <select name="readerId">
                <option value="">выберите книгу</option>
                <c:forEach var="reader" items="${listReaders}">
                     <option value="${reader.id}">"${reader.name}". ${reader.lastname}. ${reader.phone} </option>
                </c:forEach>
        </select>   
        <br>
        <input type="submit" value="Выдать книгу">
        </form>
    </body>
</html>
