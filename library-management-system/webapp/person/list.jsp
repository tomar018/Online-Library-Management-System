<%-- Document : person/list Created on : 08/01/2022, 23:38:26 Author : edsonpaulo --%>

<%@page import="java.util.List" %>
<%@page import="com.thesquad.dao.PersonDAO" %>
<%@page import="com.thesquad.utils.Helpers" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.time.LocalDate" %>
<%@page import="java.util.Date" %>
<%@page import="com.thesquad.dao.GenderDAO" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="com.thesquad.connection.DBConnection" %>
<%@page import="com.thesquad.utils.HtmlObj" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
    <%@ include file="../partials/html-head.jsp" %>  
   <%
    DBConnection connection = null;
    try {
        String query = "SELECT person.person_id AS id, " +
                       "person.first_name AS first_name, " +
                       "person.last_name AS last_name, " +
                       "gender.name AS gender, " +
                       "person.id_number AS id_number, " +
                       "person.birth_date AS birth_date " +
                       "FROM person " +
                       "INNER JOIN gender ON person.gender_id = gender.gender_id;";

        connection = new DBConnection();
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery(query);
        PersonDAO personDao = new PersonDAO();
%>
    <body>
        <%@ include file="../partials/navbar.jsp" %>  

        <a href="<%=request.getContextPath()%>" class="btn btn-primary btn-sm m-4"><< Back</a>
        <a href="<%=request.getContextPath()%>/person/new.jsp" class="btn btn-primary m-4 float-right">+ Add New Person</a>

        <div class="h-100 container-fluid d-flex justify-content-center align-items-start">
            <div class="card px-5 py-3 table-responsive-lg" style="width: 100%;">

                <h5 class="text-center mb-3">List of People</h5>

                <table class="table table-striped table-sm">
                    <thead>
                        <tr>
                            <th scope="col">Type</th>
                            <th scope="col">ID</th>
                            <th scope="col">Name</th>                            
                            <th scope="col">Surname</th>
                            <th scope="col">Gender</th>
                            <th scope="col">Identity Card</th>
                            <th scope="col">Date of Birth</th>
                            <th scope="col">Actions</th>                            
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            StringBuilder htmlBuilder = new StringBuilder();
                            if (!resultSet.isBeforeFirst()) {
                                htmlBuilder.append("<tr><td colspan=\"8\"> <h6 class=\"my-5 text-muted text-center\">No data available!</h6></tr></td>");
                            }

                            while (resultSet.next()) {
                                htmlBuilder.append("<tr>");
                                htmlBuilder.append("<th scope=\"row\">" + (personDao.isAuthor(resultSet.getInt("id"), connection) ? "Author" : "Reader") + "</th>");
                                htmlBuilder.append("<th scope=\"row\">0" + resultSet.getInt("id") + "</th>");
                                htmlBuilder.append("<td>" + resultSet.getString("name") + "</td>");
                                htmlBuilder.append("<td>" + resultSet.getString("surname") + "</td>");
                                htmlBuilder.append("<td>" + resultSet.getString("gender") + "</td>");
                                htmlBuilder.append("<td>" + resultSet.getString("bi") + "</td>");
                                htmlBuilder.append("<td>" + resultSet.getTimestamp("birth_date").toLocalDateTime().toLocalDate() + "</td>");

                                htmlBuilder.append("<td><a class=\"btn btn-secondary btn-sm text-white\" href=\"" + request.getContextPath() + "/person/view.jsp?id="
                                        + resultSet.getInt("id") + "\">View</a>");

                                htmlBuilder.append(" <a class=\"btn btn-warning btn-sm text-white mx-2\" href=\"" + request.getContextPath() + "/person/edit.jsp?id="
                                        + resultSet.getInt("id") + "\">Edit</a>");

                                htmlBuilder.append("<a class=\"btn btn-danger btn-sm text-white\" href=\"" + request.getContextPath() + "/person-servlet?id="
                                        + resultSet.getInt("id") + "&action=delete\">Remove</a></td>");

                                htmlBuilder.append("</tr>");
                            }
                        %>
                        <%= htmlBuilder%>
                    </tbody>
                </table>


            </div>
        </div>
    </body>
    <%
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.closeConnection();
            }
        }
    %>
    <script>

    </script>

</html>
