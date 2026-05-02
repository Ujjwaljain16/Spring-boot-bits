<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add Student</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
<div class="container">
    <h1>Add Student</h1>
    <div class="card-note">Use this form to add a student and assign courses.</div>

    <form method="post" action="${pageContext.request.contextPath}/students" onsubmit="return validateForm()">
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
                    <label>
                        <input type="checkbox" name="courseIds" value="${course.id}" />
                        ${course.title}
                    </label>
                </c:forEach>
            </div>
        </div>

        <button class="btn" type="submit">Save Student</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/students">Back</a>
    </form>
</div>

<script>
function validateForm() {
  const name = document.getElementById('name').value.trim();
  const email = document.getElementById('email').value.trim();
  if (!name || !email) {
    alert('Name and email are required');
    return false;
  }
  return true;
}
</script>
</body>
</html>