# ScoreBoards

ScoreBoards is a Java application concept designed to store, retrieve and update gaming events and their scores.

## Usage

You can access an automatically updated home page at <br>
http://localhost:8080/ which reloads using jQuery <br> http://localhost:8080/data/flux which reloads using Spring Boot

#### Adding an event
POST localhost:8080/event

```json
{
    "teamA": "Team A",
    "teamB": "Team B",
    "scoreTeamA": 1,
    "scoreTeamB": 2
}
```

#### Retrieving an event
GET localhost:8080/event/score/1
<br><br>
#### Updating an event
PUT localhost:8080/event/score/
```json
{
    "id": 1,
    "teamA": "Team A",
    "teamB": "Team B",
    "scoreTeamA": 1,
    "scoreTeamB": 3,
    "version": 0
}
```

## Tests
Tests can be run in IntelliJ/Eclipse and have >90% coverage
