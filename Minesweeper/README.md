# Minesweeper

### Goal
Implement a class that functions as a Minesweeper backend, and another class that 
solves a given Minesweeper board.

### Criteria (in order of importance)
**Minesweeper** [20 marks]
* The Minesweeper class should store an *n • m* board, and a set of bomb locations stored
as valid coordinates [1 mark]
* The Minesweeper class should have a constructor, as well as a random generation function
  (seeded) that generates the board after the player's first move to ensure losing immediately is impossible [2 marks]
* The Minesweeper class should store a set of flag locations, and a function to add/remove a 
flag from the board [2 marks]
* The Minesweeper class should include a function to select a valid space, and expose its value. [1 mark]
* If the selected tile is a 0 tile, all the surrounding tiles should be selected until
no empty tiles border any selected 0 tile. [3 marks]
* If the selected tile is a bomb, it should signal that the game is lost, and expose all
the remaining tiles. [2 marks]
* The Minesweeper class should include a function that prints the current board state to 
the terminal. The numbers can be coloured using ANSI escape sequences [1 mark]
* The written code should be clean, flexible, and the purpose of each component should be clear
through its design and any included Javadocs. [4 marks]
* Important/complex functions should be tested, and a final integration test should be written. [3 marks]

**Solver** [20 marks]
- The Solver class should store a Minesweeper instance, a log of moves, and any other variables you think
will be required. Apart from the constructor, it should have a single public function, `solve`. [1 mark]
- The Solver class should terminate and write the logs to a file when the game ends. The logs
should indicate the result of the game. [1 marks]
- The Solver class should flag all spaces it can immediately recognise as a bomb, e.g. if a 1 tile borders
exactly 1 unselected tile, that tile is a bomb. The logs should reflect if a bomb is flagged in this way. [4 marks]
- If no bombs can be flagged like this, the Solver class should implement a search function
to determine guaranteed bomb/tile locations. The logs should reflect moves made in this way. [7 marks]
- If the above returns no guaranteed moves, the Solver class should calculate the probabilities of each
tile being a bomb, and play the least likely tile. The logs should show each probability, 
and the resulting move. [4 marks]
- (hard) The Solver class should aim to optimise for time by minimising the distance
between the tiles flagged/selected on two consecutive moves. The effects of this optimisation
should be demonstrated by picking a random Minesweeper instance, starting move, having two 
Solver instances play the game with/without distance optimisation, and the difference in the
total distance travelled should be calculated. [3 marks]

### Time: 150m
**Goals**
- 20/40 minimum
- 25/40 - good stuff
- 30/40 - popping off
- 35/40 - buy yourself <$50 of Lego's
- 40/40 - ur lying

**Results**
- Minesweeper: 
- Solver:
- TOTAL: