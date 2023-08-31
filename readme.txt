
Congratulations on your completion of Unit 6! You started with a general overview of relational databases and Structured Query Language (SQL), integrated a relational database into an app with Room, and learned about Preferences DataStore for persisting settings and UI state. It's time to put everything you've learned into practice.

In this project, you'll build the Flight Search app in which users enter an airport and can view a list of destinations using that airport as a departure. This project gives you the opportunity to practice your skills with SQL, Room, and DataStore by offering you a set of app requirements that you must fulfill. In particular, you need the Flight Search app to meet the following requirements:

Provide a text field for the user to enter an airport name or International Air Transport Association (IATA) airport identifier.
Query the database to provide autocomplete suggestions as the user types.
When the user chooses a suggestion, generate a list of available flights from that airport, including the IATA identifier and airport name to other airports in the database.
Let the user save favorite individual routes.
When no search query is entered, display all the user-selected favorite routes in a list.
Save the search text with Preferences DataStore. When the user reopens the app, the search text, if any, needs to prepopulate the text field with appropriate results from the database.
We've provided a prepopulated database for this project. However, the expectation is for you to build the app from scratch per the requirements—practice for the actual work you do as an Android developer. This project is also a good chance to revisit or further refine your UI building skills with Compose, as you haven't needed to do much UI work since Unit 4.
