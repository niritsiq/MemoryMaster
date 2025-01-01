# Memory Master Game

A Java-based console Memory Card Game that supports 1-4 players. Test your memory by finding matching pairs of cards!

## Game Description

Memory Master is a classic card matching game where players take turns flipping cards to find matching pairs. The player with the most pairs at the end of the game wins.

### Features

- Supports 1-4 players
- Customizable board size (default 4x4)
- Multiple input formats supported (e.g., "1 2", "12", or "1,2")
- Early game termination option
- Clear console interface
- Score tracking

### Game Rules

1. Cards are placed face down on the board
2. On each turn, a player:
   - Flips two cards
   - If they match, the player:
     - Keeps the pair
     - Gets another turn
   - If they don't match:
     - Cards are flipped face down
     - Next player's turn
3. Game ends when all pairs are found
4. Player with the most pairs wins

## How to Play

1. Run the game
2. Enter the number of players (1-4)
3. Enter names for each player
4. On your turn:
   - Enter coordinates for first card (e.g., "1 2")
   - Enter coordinates for second card
   - Type 'end' at any time to finish early

### Input Formats
- "1 2" (space-separated)
- "12" (consecutive numbers)
- "1,2" (comma-separated)

## Technical Requirements

- Java 8 or higher
- Console/Terminal to run the game

## Author

Created by Nir Itzik 
