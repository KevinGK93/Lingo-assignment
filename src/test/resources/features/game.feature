Scenario: The word length stays inside of context bounds
  Given A game has started
  And i won the last round
  And the last round had "<previous length>" letters
  When i start a new round
  Then the word to attempt has "<next length>" letters

Examples:
| previous length | next length |
| 5               | 6           |
| 6               | 7           |
| 7               | 5           |

Scenario: Start a new game

  When I start a new game
  Then the word to guess has "x" letters
  And I should see the first letter
  And my score is "0"

Scenario Outline: Guess a word
  Given I am playing a game
  And the word to guess is "<word to guess>"
  When I guess "<attempt>"
  Then I get feedback "<feedback>"

Examples:
| word to guess | attempt | feedback                                             |
| BAARD         | BERGEN  | INVALID, INVALID, INVALID, INVALID, INVALID, INVALID |
| BAARD         | BONJE   | CORRECT, ABSENT, ABSENT, ABSENT, ABSENT              |
| BAARD         | BARST   | CORRECT, CORRECT, PRESENT, ABSENT, ABSENT            |
| BAARD         | DRAAD   | ABSENT, PRESENT, CORRECT, PRESENT, CORRECT           |
| BAARD         | BAARD   | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT          |

Scenario: Cannot start a round if still guessing
  Given I am playing a game
  And I am still guessing a word
  Then I cannot start a new round

Scenario: Cannot start a round if eliminated
  Given I am playing a game
  And I have been eliminated
  Then I cannot start a new round

Scenario: Cannot guess word if round not started
  Given I am playing a game
  And the round was won
  Then I cannot guess the word

Scenario: Player is eliminated after 5 incorrect guesses
  Given I am playing a game
  And the word to guess is "hold the door"
  When I guess "hodor"
  And I guess "hodor"
  And I guess "hodor"
  And I guess "hodor"
  And I guess "hodor"
  Then I have lost

Scenario Outline: Score increases based on number of attempts
  Given I am playing a game
  And the score is "<current score>"
  And the word to guess is "school"
  When I guess "school" in "<attempts>" attempts
  Then the score is "<new score>"
Examples:
| current score | attempts | new score |
| 0             | 1        | 45        |
| 5             | 1        | 50        |
| 0             | 2        | 40        |
| 5             | 2        | 45        |
| 0             | 3        | 30        |
| 5             | 3        | 35        |
| 0             | 4        | 25        |
| 5             | 4        | 30        |
| 0             | 5        | 20        |
| 5             | 5        | 25        |

