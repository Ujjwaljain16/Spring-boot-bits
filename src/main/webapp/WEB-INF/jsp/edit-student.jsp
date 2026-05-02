<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Edit Student</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
<div class="container">
    <h1>Edit Student</h1>
    <div class="card-note">The form is pre-filled with the selected student’s current values.</div>

    <form method="post" action="${pageContext.request.contextPath}/students/update/${student.id}">
        <div class="form-group">
            <label for="name">Name</label>
            <input id="name" type="text" name="name" value="${student.name}" />
            <c:if test="${not empty org.springframework.validation.BindingResult.student && org.springframework.validation.BindingResult.student.hasFieldErrors('name')}">
                <div class="error"><c:out value="${org.springframework.validation.BindingResult.student.getFieldError('name').defaultMessage}" /></div>
            </c:if>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input id="email" type="email" name="email" value="${student.email}" />
            <c:if test="${not empty org.springframework.validation.BindingResult.student && org.springframework.validation.BindingResult.student.hasFieldErrors('email')}">
                <div class="error"><c:out value="${org.springframework.validation.BindingResult.student.getFieldError('email').defaultMessage}" /></div>
            </c:if>
        </div>

        <div class="form-group">
            <label>Courses</label>
            <div class="courses">
                <c:forEach items="${courses}" var="course">
                    <c:set var="checked" value="false" />
                    <c:forEach items="${student.courses}" var="assigned">
                        <c:if test="${assigned.id == course.id}">
                            <c:set var="checked" value="true" />
                        </c:if>
                    </c:forEach>
                    <label>
                        <input type="checkbox" name="courseIds" value="${course.id}" <c:if test="${checked}">checked</c:if> />
                        ${course.title}
                    </label>
                </c:forEach>
            </div>
        </div>

        <button class="btn" type="submit">Update Student</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/students">Back</a>
    </form>
</div>
</body>
</html>