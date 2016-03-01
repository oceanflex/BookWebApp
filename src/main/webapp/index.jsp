<%-- 
    Document   : index
    Created on : Feb 8, 2016, 2:30:29 PM
    Author     : Zachary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 
              rel="stylesheet" 
              integrity="sha256-7s5uDGW3AHqw6xtJmNNtr+OBRJUlgkNJEo78P4b0yRw= sha512-nNo+yCHEyn0smMxSswnf/OnX6/KwJuZTlNZBjauKhTK0c+zT+q5JOCx0UFhXQ6rJR9jg6Es8gPuD2uZcYDLqSw==" 
              crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <br/>
            <div class="alert alert-info" role="alert">All Authors</div>
            <table class="table">
                <thead class="head">
                <th>Name</th>
                <th>ID</th>
                <th>Date Added</th>
                <th>
                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#insertModal">
                        Add
                    </button>

                    <div class="modal fade" id="insertModal" role="dialog" aria-labelledby="myModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="myModalLabel">Add New Author</h4>
                                </div>
                                <div class="modal-body">
                                    <form role='form' method="post" action="AuthorController" id="-1">
                                        <label for="authorName">Author's Name</label>
                                        <div class="input-group">
                                            <input required type="text" placeholder="first last" class="form-control" name="author_name">
                                            <span class="input-group-btn">
                                                <button name='submit' class="btn btn-success" value="insert">Add</button>
                                            </span>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </th>
                </thead>
                <c:forEach var="i" items="${author}" >
                    <tr>
                        <td>${ i.authorName }</td>
                        <td>${ i.authorID }</td>
                        <td>${ i.dateAdded }</td>
                        <td>
                            <form role='form' method="post" name='${ i.authorID }' action="AuthorController" id='${ i.authorID }'>
                                <div class="btn-group" role="group">
                                    <button type="button" name="submit" class="btn btn-warning" value='update' data-toggle="modal" data-target="#updateModal">Edit</button>
                                    <input type="hidden" value='${ i.authorID }' name="aId"/>
                                    <button name="submit" class="btn btn-danger" value='delete'>Delete</button>
                                </div>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

            </table>

            <div class="modal fade" id="updateModal" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">Updating</h4>
                        </div>
                        <div class="modal-body">
                            <form role='form' method="post" action="AuthorController" id="-1">
                                <label for="authorName">Author's Name</label>
                                <div class="input-group">
                                    <input required type="text" placeholder="first last" class="form-control" name="author_name">
                                    <!--span class="input-group-btn">
                                        <button name='submit' class="btn btn-success" value="update">Add</button>
                                        <input type="hidden" value=""/>
                                    </span-->
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" 
                integrity="sha256-KXn5puMvxCw+dAYznun+drMdG1IFl3agK0p/pqT9KAo= sha512-2e8qq0ETcfWRI4HJBzQiA3UoyFk6tbNyG+qSaIBZLyW9Xf3sWZHN/lxe9fTh1U45DpPf07yj94KsUHHWe4Yk1A==" 
        crossorigin="anonymous"></script>
        <script src="custom.js"></script>
    </body>
</html>
