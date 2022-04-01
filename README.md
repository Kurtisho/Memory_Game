# Classic Memory Game

## Synopsis of the game


For this term, I will be designing a memory game in which users will be
displayed cards which have pairs. It is the users task to try and identify 
the respective pairs. In order to **challenge** the users, there will be a
**timer** that tracks the time elapsed for each play through. It is the users
task to try and beat their fastest time!

For instance :

- Play through 1 : 53 Seconds
- Play through 2 : 45 Seconds
- Play through 3 : 42 Seconds
- etc...


## Interests
___

The reason why I chose to design a memory game was because this was the game
I grew up playing with. My cousins and I loved playing this game because 
we would always challenge each other to see who could have the most 
pairs in a full deck. With this game, we won't be competing to see who has the most pairs but rather
who can achieve the fastest time.


## User Stories
___

- As a user, I want to be able to play the game as long as I'd like
- As a user, I want to be able to see the time spent per game after I quit
- As a user, I want to choose the size of my board and the letters that are used in the game
- As a user, I want to rate the difficulty of the game
- As a user, I want to clearly see my previous attempts (keep only for console?)

- As a user, I want to be able to save the current board I'm working on
- As a user, when I start the application, I want to be able to load my board from the last time I played.


## Phase 4 : Task 2
___

Thu Mar 31 17:20:59 PDT 2022 \
Added panel: A || board pos: 1

Thu Mar 31 17:20:59 PDT 2022 \
Added panel: A || board pos: 2

Thu Mar 31 17:20:59 PDT 2022 \
Added panel: B || board pos: 3

Thu Mar 31 17:20:59 PDT 2022 \
Added panel: B || board pos: 4

Thu Mar 31 17:21:02 PDT 2022 \
The Board has been shuffled!

Thu Mar 31 17:21:02 PDT 2022  
The Board has been shuffled!

Thu Mar 31 17:21:03 PDT 2022 \
The Board has been revealed, Game Over!

## Phase 4 : Task 3
___

Overall, I am satisfied with how my project has turned out. I believe my UML diagram clearly conveys how the board and panels are associated with one another and
how they are incorporated in the final GUI class. However, there is room for improvement.
If I had more time, I would :

- reduce code duplication within the ui class by refactoring common methods.
- create a superclass for the JPanels and JButtons used in the main menu.
- create a superclass for the JPanels used in new CardLayouts to reduce the redundancy when a new panel is created
- create an exceptions folder and incorporate exceptions instead of checking user inputs manually


NOTE: the MemoryGame class in the ui folder has the same associations as the GUI class except for the association to CardPanel.
