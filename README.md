# Portfolio backend (jonij.fi)
## Project description in a nutshell

This is the backend of a fullstack project, which is a portfolio for showcasing my past projects.

Frontend shall be done after the backend is ready.

## Git workflow

The project has five different kinds of branches:
- **main (production)**
  - This is only touched when release is ready to be merged to the main.
- **release**
  - Unit tests are  not included in the release branch.
  - H2 database is switched to MySQL database.
  - Last check that everything is as it should for production phase.
- **development**
  - When development is ready, it is then merged to the release branch.
- **project_task#3, #4, #5...**
  - When project_task is complete, it is then merged to the development branch.
- **coding_task#0, #1, #2...**
  - When coding_task passes the exit criteria, it is then merged to the development branch.
  - Exit criteria is explained later in the documentation.
- **hot_fix#1, #2, #3...**
  - Changes that are not planned but must be done are handled in hot_fix branches.
  - Merging to development when hot_fix is done.

