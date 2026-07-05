[README.md](https://github.com/user-attachments/files/29680379/README.md)
# Othello (Reversi) — Java Implementation with Minimax AI

A console-based implementation of the board game Othello (Reversi), featuring
a human vs. AI game loop where the AI opponent uses the **minimax algorithm
with alpha-beta pruning** to choose its moves.

## Project structure

```
ce326.hw2
├── HW2.java            # Entry point: game loop, I/O, move history, win check
├── Board.java          # Board state, move generation, move execution, evaluation
├── AIPlayer.java       # Minimax + alpha-beta pruning search
├── AvailableMove.java  # Simple (row, col) move representation with algebraic notation
├── Pawn.java           # Abstract base class for a board piece
├── PawnBlack.java      # Pawn subclass representing 'O'
└── PawnWhite.java      # Pawn subclass representing 'X'
```

## How to compile and run

From the directory containing the `ce326` package folder:

```bash
javac ce326/hw2/*.java
java ce326.hw2.HW2
```

## How to play

1. **Choose your color** when prompted — you can type `black`, `white`, `b`,
   `w`, `o`, or `x` (case-insensitive). Black (`O`) always moves first.
2. **Choose the AI search depth** (1–9). This controls how many moves ahead
   the AI looks — higher values give a stronger but slower AI.
3. On your turn, enter a move in algebraic notation: a column letter (`a`–`h`)
   followed by a row number (`1`–`8`), e.g. `c4`.
4. Legal moves for the side to move are shown on the board as `*`.
5. If a player has no legal moves, their turn is skipped automatically.
6. The game ends when neither player has a legal move. The pawn counts are
   totalled and the winner is announced.

### Board legend

| Symbol | Meaning        |
|--------|----------------|
| `X`    | White pawn     |
| `O`    | Black pawn     |
| `*`    | Legal move     |
| `.`    | Empty square   |

## Game logic

- **`Board`** stores an 8×8 grid of `Pawn` references (`null` = empty).
  - `getValidMoves(symbol)` scans every empty square and checks all 8
    directions for a valid Othello flanking move (an unbroken line of
    opponent pawns terminated by one of the player's own pawns).
  - `makeMove(row, col, symbol)` places the new pawn and flips every
    outflanked line in all 8 directions via `flipDirection`.
  - `evaluate(aiSymbol)` scores a board position using a static positional
    weight table (corners and edges weighted heavily, squares adjacent to
    corners penalized, since they set up the opponent to take the corner).

- **`AIPlayer`** implements the decision-making:
  - `findBestMove` generates every legal move for the AI, simulates each on
    a board copy, and picks the move whose resulting position scores highest
    after a minimax search.
  - `minimax` recursively explores the game tree to the requested depth,
    alternating between maximizing (AI) and minimizing (opponent) turns, and
    prunes branches with alpha-beta cutoffs once a branch can no longer
    affect the final decision.
  - At depth 0 (or when a side has no legal moves), the position is scored
    directly via `Board.evaluate`.

- **`HW2`** (main class) drives the game loop: it alternates turns between
  the human and the AI, validates and applies human input, calls
  `AIPlayer.findBestMove` for the AI's turn, prints the board and move
  history after every move, and tallies the final score to declare a winner.

- **`AvailableMove`** is a lightweight (row, col) pair with a `toString()`
  that renders it in algebraic notation (e.g. `(2, 3)` → `"d3"`).

- **`Pawn` / `PawnBlack` / `PawnWhite`** form a small class hierarchy: `Pawn`
  is an abstract type exposing `getSymbol()`, implemented by `PawnBlack`
  (`'O'`) and `PawnWhite` (`'X'`).

## Notes

- `HW2.boardCopy(Board)` performs a deep copy of the board (new `Pawn`
  instances) so the minimax search can simulate moves without mutating the
  real game state.
- The AI's strength is controlled entirely by the search depth chosen at
  startup and the static weight table in `Board.evaluate` — there is no
  dynamic (e.g. mobility- or stability-based) evaluation.
