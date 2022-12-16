INSERT INTO PROJECT
(ID, USERNAME, PROJECT_NAME, PROJECT_TYPE, DATE_OF_COMPLETION,
 IS_DEPLOYED, SOURCE_CODE_URL, PROJECT_URL, PROJECT_IMAGE_FILENAME)
VALUES
    (1001, 'tester', 'test project one', 'Coding', CURRENT_DATE(),
     true, 'http://www.github.com/testproj1', 'http://railway.app/testproj1',
     'default.png');

INSERT INTO PROJECT
(ID, USERNAME, PROJECT_NAME, PROJECT_TYPE, DATE_OF_COMPLETION,
 IS_DEPLOYED)
VALUES
    (1002, 'tester', 'test project two', 'UX', CURRENT_DATE(),
     false);

