<%-- 
    Document   : index
    Created on : Feb 8, 2016, 2:30:29 PM
    Author     : Zachary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Table of Authors</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 
              rel="stylesheet" 
              integrity="sha256-7s5uDGW3AHqw6xtJmNNtr+OBRJUlgkNJEo78P4b0yRw= sha512-nNo+yCHEyn0smMxSswnf/OnX6/KwJuZTlNZBjauKhTK0c+zT+q5JOCx0UFhXQ6rJR9jg6Es8gPuD2uZcYDLqSw==" 
              crossorigin="anonymous">
    </head>
    <body>
        <sec:authorize access="hasAnyRole('ROLE_MGR')">
        <div class="modal fade" id="insertModal" role="dialog" aria-labelledby="iModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="iModalLabel">Add New Author</h4>
                    </div>
                    <div class="modal-body">
                        <form role='form' method="post" action='<%= response.encodeURL("index") %>' id="-1">
                            <div class="input-group">
                                <label class="input-group-addon" for="author_name">Author's Name</label>
                                <input required type="text" placeholder="first last" id="author_name" class="form-control" name="author_name">
                                <span class="input-group-btn">
                                    <button name='submit' class="btn btn-success" value="insert">Add</button>
                                </span>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
        </sec:authorize>
        <div class="container">
            <br/>
            <div class="alert alert-info" role="alert">All Authors</div>
            <table class="table">
                <thead class="head">
                <th>Name</th>
                <th>ID</th>
                <th>Date Added</th>
                <sec:authorize access="hasAnyRole('ROLE_MGR')"><th>
                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#insertModal">
                        Add
                    </button>

                    </th></sec:authorize>
                </thead>
                <c:forEach var="i" items="${author}" >
                    <tr>
                        <td>${ i.authorName }</td>
                        <td>${ i.authorId }</td>
                        <td>${ i.dateAdded }</td>
                        <sec:authorize access="hasAnyRole('ROLE_MGR')"><td>
                            <form role='form' method="post" name='${ i.authorId }' action='<%= response.encodeURL("index") %>' id='${ i.authorId }'>
                                <div class="btn-group" role="group">
                                    <button type="button" name="submit" class="btn btn-warning" value='update' 
                                            data-aid="${ i.authorId }" data-aname="${ i.authorName }" data-adate="${ i.dateAdded }" data-toggle="modal" data-target="#updateModal">Edit</button>
                                    <input type="hidden" value='${ i.authorId }' name="aId"/>
                                    <button name="submit" class="btn btn-danger" value='delete'>Delete</button>
                                </div>
                            </form>
                            </td></sec:authorize>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <sec:authorize access="hasAnyRole('ROLE_MGR')">
        <div class="modal fade" id="updateModal" role="dialog" aria-labelledby="uModelLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="uModalLabel">Update Author</h4>
                    </div>
                    <div class="modal-body">
                        <form role='form' method="post" action='<%= response.encodeURL("index") %>' id="-2">
                            <div class="input-group">
                                <label class="input-group-addon" for="uDate">Date Added</label>
                                <input class="form-control" type="date" value=" " name="date_added" id="uDate" required/>
                            </div><br/>
                            <div class="input-group">
                                <label class="input-group-addon" for="uName">Author's Name</label>
                                <input required type="text" placeholder="first last" class="form-control" id="uName" name="author_name"/>
                                <span class="input-group-btn">
                                    <button name='submit' class="btn btn-warning" value="update">Change</button>
                                </span>
                            </div>
                            <input type="hidden" value="-3" name="aId" id="uId"/>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
        </sec:authorize>
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" 
                integrity="sha256-KXn5puMvxCw+dAYznun+drMdG1IFl3agK0p/pqT9KAo= sha512-2e8qq0ETcfWRI4HJBzQiA3UoyFk6tbNyG+qSaIBZLyW9Xf3sWZHN/lxe9fTh1U45DpPf07yj94KsUHHWe4Yk1A==" 
        crossorigin="anonymous"></script>
        <script src="custom.js"></script>
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>  
    </body>
</html>