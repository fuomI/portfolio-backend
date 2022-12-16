<%@ include file="common/header.jspf" %>
<title>Add Project</title>
</head>

<body>
<%@ include file="common/navigation.jspf" %>

    <div class="container">
        <h1>Enter Project Details</h1>

        <%--@elvariable id="project" type="fi.jonij.portfoliobackend.project.Project"--%>
        <form:form method="post" modelAttribute="project">

            <div class="mb-3">
                <form:label for="nameInput" path="projectName" cssClass="form-label">Name</form:label>
                <form:input id="nameInput" type="text" path="projectName" cssClass="form-control" required="required"/>
                <form:errors path="projectName" cssClass="text-warning"/>
            </div>

            <fieldset class="mb-3">
                <form:label for="typeInput" path="projectType" cssClass="form-label">Type</form:label>
                <form:input id="typeInput" type="text" path="projectType" cssClass="form-control" required="required"/>
                <form:errors path="projectType" cssClass="text-warning"/>
            </fieldset>

            <fieldset class="mb-3">
                <form:label for="dateInput" path="dateOfCompletion" cssClass="form-label">Completed</form:label>
                <form:input id="dateInput" type="date" path="dateOfCompletion" cssClass="form-control" required="required"/>
                <form:errors path="dateOfCompletion" cssClass="text-warning"/>
            </fieldset>

            <fieldset class="mb-3">
                <form:label for="deployedInput" path="deployed" cssClass="formlabel">Deployed</form:label>
                <form:input id="deployedInput" type="text" path="deployed" cssClass="form-control" required="required"/>
                <form:errors path="deployed" cssClass="text-warning"/>
            </fieldset>

            <fieldset class="mb-3">
                <form:label for="sourceCodeInput" path="sourceCodeUrl" cssClass="form-label">Source Code URL</form:label>
                <form:input id="sourceCodeInput" type="text" path="sourceCodeUrl" cssClass="form-control"/>
                <form:errors path="sourceCodeUrl" cssClass="text-warning"/>
            </fieldset>

            <fieldset class="mb-3">
                <form:label for="projectUrlInput" path="projectUrl" cssClass="form-label">Project URL</form:label>
                <form:input id="projectUrlInput" type="text" path="projectUrl" cssClass="form-control"/>
                <form:errors path="projectUrl" cssClass="text-warning"/>
            </fieldset>

<%--            <fieldset class="mb-3">--%>
<%--                <form:label path="projectImageFilename">Target Date</form:label>--%>
<%--                <form:input type="file" path="projectImageFilename"/>--%>
<%--                <form:errors path="projectImageFilename" cssClass="text-warning"/>--%>
<%--            </fieldset>--%>

            <form:input type="hidden" path="username"/>
            <form:input type="hidden" path="id"/>

            <input type="submit" class="btn btn-success" value="submit"/>

        </form:form>

    </div>

<%@ include file="common/footer.jspf" %>