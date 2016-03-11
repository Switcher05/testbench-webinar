crm
==============

Template for a simple Vaadin application that only requires a Servlet 3.0 container to run.


Workflow
========
Project uses https://wscdn.vaadin.com/ for widgetset compilation
To compile the entire project, run "mvn install".
To run integration tests, run "mvn verify".
To run the application, run "mvn widfly:run" and open http://localhost:8080/ .

To develop the theme, simply update the relevant theme files and reload the application.
Pre-compiling a theme eliminates automatic theme updates at runtime - see below for more information.
