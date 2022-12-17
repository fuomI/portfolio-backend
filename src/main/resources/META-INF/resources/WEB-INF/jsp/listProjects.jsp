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
                    <td><img src="${project.projectImageFilename}" class="img-fluid" alt="Default project image" width="132" height="100"></td>
                    <td><b>Name:</b> ${project.projectName}<br>
                        <b>Type:</b> ${project.projectType}<br>
                        <b>Description: </b>${project.projectDescription}<br>
                        <b>Completed:</b> ${project.dateOfCompletion}<br>
                        <b>Source code:</b> <a>${project.sourceCodeUrl}</a><br>
                        <b>Hosted at:</b> <a>${project.projectUrl}</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

<%@ include file="common/footer.jspf" %>