<%@ include file="common/header.jspf" %>
<title>Projects</title>
</head>

<body>

<%@ include file="common/navigation.jspf" %>
    <div class="container">
        <table class="table">
            <thead>
            <tr>
                <th>Image</th>
                <th>Project Name</th>
                <th>Project Type</th>
                <th>Completed</th>
                <th>Deployed</th>
                <th>Source code</th>
                <th>Hosted at</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${projects}" var="project">
                <tr>
                    <td><img src="default.png" class="img-fluid" alt="Default project image" width="132"
                             height="100"></td>
                    <td>${project.projectName}</td>
                    <td>${project.projectType}</td>
                    <td>${project.dateOfCompletion}</td>
                    <td>${project.deployed}</td>
                    <td>${project.sourceCodeUrl}</td>
                    <td>${project.projectUrl}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

<%@ include file="common/footer.jspf" %>