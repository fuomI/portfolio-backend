<%@ include file="common/header.jspf" %>
<title>Projects</title>
</head>

<body>

<%@ include file="common/navigation.jspf" %>
    <div class="container">
        <table class="table">
            <tbody>
            <c:forEach items="${projects}" var="project">
                <tr>
                    <td><img src="/upload/${project.projectImageFilename}" class="img-fluid" alt="Image of a project: ${project.projectName}" width="132" height="100"></td>
                    <td><b>Name:</b> ${project.projectName}<br>
                        <b>Type:</b> ${project.projectType}<br>
                        <b>Description: </b>${project.projectDescription}<br>
                        <b>Completed:</b> ${project.dateOfCompletion}<br>
                        <b>Source code:</b> <a>${project.sourceCodeUrl}</a><br>
                        <b>Hosted at:</b> <a>${project.projectUrl}</a></td>
                </tr>
                <tr>
                    <td><a href="delete-project?id=${project.id}" class="btn btn-warning" style="background-color: #f87c7c">Delete</a></td>
                    <td><a href="update-project?id=${project.id}" class="btn btn-success">Update</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

<%@ include file="common/footer.jspf" %>