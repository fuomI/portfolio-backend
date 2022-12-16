INSERT INTO PROJECT
(ID, USERNAME, PROJECT_NAME, PROJECT_TYPE, DATE_OF_COMPLETION,
 DEPLOYED, SOURCE_CODE_URL, PROJECT_URL, PROJECT_IMAGE_FILENAME)
VALUES
    (1001, 'tester', 'test project one', 'Coding', CURRENT_DATE(),
     true, 'https://www.github.com/testproj1', 'https://railway.app/testproj1',
     'default.png');
