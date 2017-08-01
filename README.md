# twitter

A simple twitter messaging service in SpringMVC.

## Architecture
Frontend: AngularJS, Bootstrap

Backend: SpringbootMVC

DevOps: gradle

Database: H2 in-memory database

## Implementations

The functionality includes:

1. An endpoint to read the message list for the current user (as identified by their HTTP Basic authentication credentia
ls). Include messages they have sent and messages sent by users they follow. Support a “search=” parameter that can be 
used to further filter messages based on keyword.
2. Endpoints to get the list of people the user is following as well as the followers of the user.
3. An endpoint to start following another user.
4. An endpoint to unfollow another user.
5. An endpoint that returns the current user's "shortest distance" to some other user. The shortest distance is defined
 as the number of hops needed to reach a user through the users you are following (not through your followers; direction
  matters). For example, if you follow user B, your shortest distance to B is 1. If you do not follow user B, but you 
  do follow user C who follows user B, your shortest distance to B is 2.
6. An endpoint that returns a list of all users, paired with their most "popular" follower. The more followers someone
 has, the more "popular" they are. Hint: this is possible to do with a single SQL query!
 
## Databases

The project provides an H2 in-memory SQL database as a storage backend. Your application must use this database to 
store all of its user and message data. Use Spring JDBC's NamedParameterJdbcTemplate to interact with the database.


All data is lost when the application restarts. So, instead of bootstrapping the database manually, the project uses 
the schema.sql and data.sql files in the src/main/resources directory. These files already contain a schema and some 
data for you, are executed by Spring when your applications starts. If you want to interact with the database directly, 
you can do so through the web-based console provided by H2. This is available as part of your application at 
http://localhost:8080/h2-console. To connect to your application's database, just change the JDBC URL to "
jdbc:h2:mem:challenge" (the defaults are correct otherwise).
