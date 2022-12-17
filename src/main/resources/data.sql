INSERT INTO PROJECT
(ID, USERNAME, PROJECT_NAME, PROJECT_TYPE, PROJECT_DESCRIPTION,
 DATE_OF_COMPLETION, SOURCE_CODE_URL, PROJECT_URL, PROJECT_IMAGE_FILENAME)
VALUES
    (1001, 'tester', 'test project one', 'Coding',
     'Project for testing the backend of my portfolio',
     CURRENT_DATE(), 'https://www.github.com/testproj1',
     'https://railway.app/testproj1', 'default.png');

INSERT INTO PROJECT
(ID, USERNAME, PROJECT_NAME, PROJECT_TYPE, PROJECT_DESCRIPTION,
 DATE_OF_COMPLETION, SOURCE_CODE_URL, PROJECT_URL, PROJECT_IMAGE_FILENAME)
VALUES
    (1001, 'tester', 'test project two', 'UX',
     'Second project for testing the backend of my portfolio',
     CURRENT_DATE(), 'https://www.github.com/testproj2',
     'https://railway.app/testproj2', 'default.png');
