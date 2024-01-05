# SpeerAssesment

```
Sandeep Roy
Cell - +91-9178386506
Email - sandeep.roy2014@gmail.com
```
## Requirement

DUE DATE: 48 hours from when you start the assessment, however, if you require more time feel free to ask! Good luck!


Project Overview

You have been asked to build a secure and scalable RESTful API that allows users to create, read, update, and delete notes. The application should also allow users to share their notes with other users and search for notes based on keywords.


Technical Requirements

    Implement a RESTful API using a framework of your choice (e.g. Express, DRF, Spring).
    Use a database of your choice to store the data (preferably MongoDB or PostgreSQL).
    Use any authentication protocol and implement a simple rate limiting and request throttling to handle high traffic.
    Implement search functionality to enable users to search for notes based on keywords. ( preferably text indexing for high performance )
    Write unit tests and integration tests your API endpoints using a testing framework of your choice.


API Endpoints

Your API should implement the following endpoints:

Authentication Endpoints

    POST /api/auth/signup: create a new user account.
    POST /api/auth/login: log in to an existing user account and receive an access token.

Note Endpoints

    GET /api/notes: get a list of all notes for the authenticated user.
    GET /api/notes/:id: get a note by ID for the authenticated user.
    POST /api/notes: create a new note for the authenticated user.
    PUT /api/notes/:id: update an existing note by ID for the authenticated user.
    DELETE /api/notes/:id: delete a note by ID for the authenticated user.
    POST /api/notes/:id/share: share a note with another user for the authenticated user.
    GET /api/search?q=:query: search for notes based on keywords for the authenticated user.


Deliverables

    A GitHub repository with your code.
    A README file with
    Details explaining the choice of framework/db/ any 3rd party tools.
    instructions on how to run your code and run the tests.
    Any necessary setup files or scripts to run your code locally or in a test environment.


Evaluation Criteria

Your code will be evaluated on the following criteria:

    Correctness: does the code meet the requirements and work as expected?
    Performance: does the code use rate limiting and request throttling to handle high traffic?
    Security: does the code implement secure authentication and authorization mechanisms?
    Quality: is the code well-organized, maintainable, and easy to understand?
    Completeness: does the code include unit, integration, and end-to-end tests for all endpoints?
    Search Functionality: does the code implement text indexing and search functionality to enable users to search for notes based on keywords?


## Run Locally

Clone the project using - ```https://github.com/sandeepRoy/SpeerAssesment.git```

Create database schema with given sql file(speer.sql).
Please make sure to create a blank schema named 'speer' before importing the sql file.

To create the jar file, run inside the cloned folder
 
```
mvn clean install

```
Inside target folder, Run the jar file using

```
java -jar SpeerBackEnd-0.0.1-SNAPSHOT.jar

```

The swagger endpoint to test the APIs

```http://localhost:8080/swagger-ui/index.html```

Note

```
1. Search using keyword method in UserService, only checks for single keyword. 
2. In UserServiceTest class we can search using combination of keywords(space separated).
3. Please run the tests from top to bottom
```



## Usage

1. Register a User

   Request
   ```
   {
       "user_name": "username",
       "password": "password"
   }
   ```
   Response
   ```
   {
      "user_id": 8,
      "user_name": "string",
      "password": "string",
      "token": null,
      "noteList": null
    }
   ```
2. Login a User
   Request
   ```
   {
       "user_name": "username",
       "password": "password"
   }
   ```
   Response
   ```
   {
      "user_id": 8,
      "user_name": "username",
      "password": "password",
      "token": Abcdefghijklmnopqrstusername ,
      "noteList": null
    }
   ```

3. Create Note for User
      
      Request Parameter - ```token = "Abcdefghijklmnopqrstusername"``` 

      Request Body -
      ```
      {
          "note_body": "string"
      }
      ```

      Response
      ```
      {
          "user_id": 9,
          "user_name": "",
          "password": "string",
          "token": "string",
          "noteList": [
            {
              "note_id": 1,
              "note_body": "string"
            }
          ]
      }
      ```
4. Create another Note for User
      
      Request Parameter - ```token = "Abcdefghijklmnopqrstusername"```

      Request Body -
      ```
      {
          "note_body": "string1"
      }
      ```

      Response
      ```
      {
          "user_id": 9,
          "user_name": "",
          "password": "string",
          "token": "string",
          "noteList": [
           {
              "note_id": 1,
              "note_body": "string"
           },  
           {
              "note_id": 2,
              "note_body": "string1"
            }
          ]
      }
      ```

5. See all notes of a user

      Request Parameter - ```token = "Abcdefghijklmnopqrstusername"```

      Response
      ```
      {
          "user_id": 9,
          "user_name": "",
          "password": "string",
          "token": "string",
          "noteList": [
            {
              "note_id": 1,
              "note_body": "string"
            },
            {
              "note_id": 2,
              "note_body": "string1"
            }
          ]
      }
      ```

6. See a note with given note id

      Request Parameter - ```token = "Abcdefghijklmnopqrstusername", note_id = 1```

      Response
      ```
      {
          "user_id": 9,
          "user_name": "",
          "password": "string",
          "token": "string",
          "noteList": [
            {
              "note_id": 1,
              "note_body": "string"
            }
          ]
      }
      ```

7. Update a note with given note id

      Request Parameter - ```token = "Abcdefghijklmnopqrstusername", note_id = 1```
      
      Request Body -
      ```
      {
          "note_body": "Updated String"
      }
      ```

      Response
      ```
      {
          "user_id": 9,
          "user_name": "",
          "password": "string",
          "token": "string",
          "noteList": [
            {
              "note_id": 1,
              "note_body": "Updated String"
            }
          ]
      }
      ```

8. Share a note with given note_id to another user with given user_id

      Request Parameter - ```token = "Abcdefghijklmnopqrstusername", note_id = 1, user_id = 1```  

      Response -
      ```
      {
          "note_id": 1,
          "note_body": "Updated String"
      }

      Requesting to check this in database, ** There should be another entry in notes table with notes body copied from given note_id and assigned to the new user.
      Can also be verified by logging in again as with a another user. **
      ```

9. Search Notes as per keywords

      Request Parameter - ```token = "Abcdefghijklmnopqrstusername", keyword = "string"```  

      
      ```
      {
          "note_id": 1,
          "note_body": "Updated String"
      }

      Search using a combination of keywords can be verified in test.
      
      ```
11. Delete notes by given note_id
    
        Request Parameter - ```token = "Abcdefghijklmnopqrstusername", note_id = "1"```

        Response
          ```
          {
              "user_id": 9,
              "user_name": "",
              "password": "string",
              "token": "string",
              "noteList": []
          }
          ```
    

        
