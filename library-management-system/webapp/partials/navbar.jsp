<%-- 
    Document   : navbar
    Created on : 20/01/2022, 04:10:32
    Author     : aditya bansal
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="navbar px-5 justify-content-around bg-primary">

    <div class="btn-group">
        <button type="button" class="btn btn-primary dropdown-toggle text-left" data-toggle="dropdown">
            People
        </button>
        <div class="dropdown-menu">
            <h6 class="dropdown-header">Person</h6>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/person/list.jsp">List People</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/person/new.jsp">Add Person</a>
            <div class="dropdown-divider"></div>

            <h6 class="dropdown-header">Reader</h6>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">List Readers</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">Add Reader</a>
            <div class="dropdown-divider"></div>

            <h6 class="dropdown-header">Author</h6>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">List Authors</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">Add Author</a>
        </div>
    </div>

    <div class="btn-group">
        <button type="button" class="btn btn-primary dropdown-toggle text-left" data-toggle="dropdown">
            Publishers
        </button>
        <div class="dropdown-menu">
            <a class="dropdown-item" href="<%=request.getContextPath()%>/publisher/list.jsp">List Publishers</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/publisher/new.jsp">Add Publisher</a>
        </div>
    </div>

    <div class="btn-group">
        <button type="button" class="btn btn-primary dropdown-toggle text-left" data-toggle="dropdown">
            Books
        </button>
        <div class="dropdown-menu">
            <a class="dropdown-item" href="<%=request.getContextPath()%>/book/list.jsp">List Books</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/book/new.jsp">Add Book</a>
        </div>
    </div>

    <div class="btn-group">
        <button type="button" class="btn btn-primary dropdown-toggle text-left" data-toggle="dropdown">
            Borrowing
        </button>
        <div class="dropdown-menu">
            <a class="dropdown-item" href="<%=request.getContextPath()%>/book-request/list.jsp">List Requests</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/book-request/new.jsp">Add Request</a>
        </div>
    </div>

    <div class="btn-group">
        <button type="button" class="btn btn-primary dropdown-toggle text-left" data-toggle="dropdown">
            Genre
        </button>
        <div class="dropdown-menu">
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">List Genres</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">Add Genre</a>
        </div>
    </div>

    <div class="btn-group">
        <button type="button" class="btn btn-primary dropdown-toggle text-left" data-toggle="dropdown">
            Address
        </button>
        <div class="dropdown-menu">
            <h6 class="dropdown-header">Country</h6>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">List Countries</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">Add Country</a>

            <h6 class="dropdown-header">Province</h6>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">List Provinces</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">Add Province</a>
            <div class="dropdown-divider"></div>

            <h6 class="dropdown-header">Municipality</h6>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">List Municipalities</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">Add Municipality</a>
            <div class="dropdown-divider"></div>

            <h6 class="dropdown-header">Commune</h6>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">List Communes</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">Add Commune</a>
            <div class="dropdown-divider"></div>
        </div>
    </div>

    <div class="btn-group">
        <button type="button" class="btn btn-primary dropdown-toggle text-left" data-toggle="dropdown">
            Book (Attributes)
        </button>
        <div class="dropdown-menu">
            <h6 class="dropdown-header">Category</h6>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">List Categories</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">Add Categories</a>

            <h6 class="dropdown-header">Status</h6>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">List Statuses</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">Add Status</a>
            <div class="dropdown-divider"></div>

            <h6 class="dropdown-header">Classification</h6>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">List Classifications</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">Add Classification</a>
            <div class="dropdown-divider"></div>

            <h6 class="dropdown-header">Descriptors</h6>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">List Descriptors</a>
            <a class="dropdown-item" href="<%=request.getContextPath()%>/#">Add Descriptors</a>
        </div>
    </div>
</div>
