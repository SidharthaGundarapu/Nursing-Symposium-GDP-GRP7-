# Nursing Conference Application

### GDP 1 - Group 7

---

### Project Description

Our group's project is to develop a mobile application to aid with the scheduling of nursing conferences between Northwest Faculty, the local hopsital in Maryville, and prospective atendees to these conferences.  The purpose of the application is to allow the staff scheduling the conference to display and manipulate both conference and event data so that atendees/guests can view it in a clear and concise manner.
We have added the project title as the "Nursing symposium"

---

### Project Navigation

 - [Meeting Notes & Minutes](/Meetings/)
    - [Most Recent Minutes](/Meetings/minutes_8_25.md)
    - [Most Recent Weekly Standup Notes](/Meetings/SU%20Meeting%201.xlsx) - Excel
 - [App Design Mockups (Visuals)](/Mockups/)
 - [Project Code]
 - Design Documentation
    - [Current Project Requirements]
    - [Initial Requirements & Functionalities]
    - [Initial Proposal Reaction](application_propopsal.md)
 - [Team Biography](#our-team)

---

### Project Lifecycle

- 8/29/22 - Meetings Folder
    - Created a [meetings folder](/Meetings/) to hold meeting minutes/descriptions.
- 8/31/22 - Coordination & Mockups
    - Coordinated meeting with client
    - Set up meeting with other design group for client prep
    - Created a [mockups folder](/Mockups/) to hold app design mockups.

---

### Our Team

Anakapalli Kanaka Venkata Phaneendra Babu

 - I have an experience in automotive domain, have familiarized with the java concepts in the first semester and persuring the SWIFT(IOS in the current semester.
 - Doing the intiall reserach on the complete application development.
 - Conducted the meetings in the library as the inital discussion and seggregated the topics.


Sidhartha Gundarapu

- Hi, I'm pursuing my graduation in Applied Computer Science at NWMSU. I am excited for the above stated mobile application development. My role in the project to schedule the team meetings, checking the updates regarding the project, as well as following the project development processes(SDLC or Agile) and dividing the project into sprints for ease development. And doing research about new technologies useful for the project.
- I have interest towards the computer science by following the latest technologies like machine learning e.t.c.. Actually i have done my bachelors in mechanical but i have shifted to computer science and have done a project of bank statement analyser using python and it''s libraries.
- Finally we are a good team and expecting a great project output of the mobile application "Nursing Symposium".

Phani Padhmanabha Naidu Ratnala

 - Hello,I have hands on experience on front end development.My role in this project will be in front end user interface designing also will share my helping hand for my team mates in all the other areas for developing the mobile application.
 - Currently,I'm pursuing my masters in applied Computer Science at Northwest Missouri State University.
 - GDP is an oppurtunity to learn new things like how a team works and to conduct my roles and responsibilites as per the requirements of the project.

Alexander Dieringer

- Hi, I'm a CS Graduate Student at NWMSU with both front & back end experience.  I enjoy the development process in general, as well as the planning and organizational aspects of project development.  My role in our project is to help withmore of the back end for our application, though I will potentially be helping in other areas.
-----------------------------------------------------------------------------
09/02/2022

@ Anakapalli Kanaka Venkata Phaneendra babu
1. Have attended the Client meeting and listed the functionalites
2. Working on the existing api and studying to understand
3. Working on the preparation of layouts for the prototype presentation.
4. Started the documentation task for the application devlopment
5. started the tutorials for the application development

@ Phani Padmanabha Naidu Ratnala
1) Mainly this week we attended the client meeting and we had good interaction also known the requirements about the project.
2) Started working on the user interface and preparation of layouts.
3) Studying on the existing API's for the discussed features.

-----------------------------------------
09/09/2022
@ Siddhartha
1. Have created the events activity in the android studio and started working with it 
2. Created few of the views and iterating on the layout
3. Working on the diffrent methods for the connection part
4. Simultaneously working on the database prototype development.

@ Phani padmanabaha Naidu
1. Started the login activity in the android studio
2. Working of the adjusting views and prepare a proper layout
3. Working on the java backend code for the connnections
4. Developing the database logics and tables.'
5. [link for Reference](https://webflow.com/design/nursing-symposium)
6. Developed reference login page and written code for it.
7. <!DOCTYPE html><html><head><meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><meta name="app-version"><meta name="_csrf" href="/manifest.json"><meta name="viewport" content="width=device-width, initial-scale=1"><meta name="theme-color" content="#3490eb"><link rel="stylesheet"> <title>Webflow - Nursing Symposium</title><link rel="icon" 
var source = 'designer';
var hostUrl = 'https://webflow.com';
var authenticated = Boolean();
var forceFalse = false;
// authenticated users
switch (source) {
  case 'dashboard':
    if (!authenticated) {
      initCallbacks();
      loadIpStack();
    }
    break;
  case 'designer-preview':
    initCallbacks();
    isAuthenticated(function(authenticated) {
      if (!authenticated) {
        loadIpStack();
      } else {
        // Needed to resolve wf_isEU
        forceFalse = true;
      }
    });
    break;
}

function initCallbacks() {
  window.wf_onDetectContinent = function(data) { 
    window.wf_continentCode = data.continent_code; 
    window.wf_userLocation = {
      country_code: data.country_code,
      city: data.city
    }
  };
