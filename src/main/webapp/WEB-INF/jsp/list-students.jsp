<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Students</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
<div class="container">
    <div class="toolbar">
        <h1>Student List</h1>
        <a class="btn" href="${pageContext.request.contextPath}/students/add">Add Student</a>
    </div>

    <div class="card-note">Students and their enrolled courses are shown below using a JSTL loop.</div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Courses</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${students}" var="student">
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.email}</td>
                <td>
                    <c:forEach items="${student.courses}" var="course" varStatus="status">
                        <c:out value="${course.title}" />
                        <c:if test="${!status.last}">, </c:if>
                    </c:forEach>
                </td>
                <td>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/students/edit/${student.id}">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>