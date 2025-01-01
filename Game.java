import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final List<Player> players;
    private int currentPlayerIndex;
    private int remainingPairs;

    public Game(int rows, int cols) {
        this.board = new Board(rows, cols);
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.remainingPairs = (rows * cols) / 2;
    }

    private void initializePlayers(Scanner scanner) {
        while (true) {
            System.out.print("Enter number of players (1-4): ");
            try {
                int numPlayers = Integer.parseInt(scanner.nextLine().trim());
                if (numPlayers < 1 || numPlayers > 4) {
                    System.out.println("Number of players must be between 1 and 4. Try again.");
                    continue;
                }

                // Get player names
                for (int i = 0; i < numPlayers; i++) {
                    System.out.print("Enter name for Player " + (i + 1) + ": ");
                    String name = scanner.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println("Name cannot be empty. Try again.");
                        i--; // Retry this player
                        continue;
                    }
                    players.add(new Player(name));
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        // Initialize players at the start of the game
        initializePlayers(scanner);
        System.out.println("\nGame starting with " + players.size() + " players!");
        System.out.println("Type 'end' at any time to finish the game early.");

        while (remainingPairs > 0) {
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("\n" + currentPlayer.getName() + "'s turn");
            board.displayBoard();

            try {
                // Get first card
                Card firstCard = getCardFromInput(scanner);
                firstCard.flip();
                board.displayBoard();

                // Get second card
                Card secondCard = getCardFromInput(scanner);
                secondCard.flip();
                board.displayBoard();

                if (firstCard.getId() == secondCard.getId()) {
                    System.out.println("Match found! " + currentPlayer.getName() + " gets another turn!");
                    currentPlayer.incrementScore();
                    remainingPairs--;
                } else {
                    System.out.println("No match. Next player's turn.");
                    firstCard.flip();
                    secondCard.flip();
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                }
            } catch (RuntimeException e) {
                if (e.getMessage() != null && e.getMessage().equals("Game ended")) {
                    return;
                }
                throw e;
            }
        }

        announceWinner();
    }

    private Card getCardFromInput(Scanner scanner) {
        while (true) {
            System.out.print("Enter row and column (examples: '1 2', '12', or '1,2') or 'end' to finish: ");
            String input = scanner.nextLine().trim().replaceAll("\\s+", ""); // Remove extra spaces

            // Check for end game command
            if (input.toLowerCase().equals("end")) {
                System.out.println("\n=== Game ended early by player! ===");
                revealAllCards();
                announceWinner();
                System.exit(0);
            }

            try {
                int row, col;

                if (input.toLowerCase().contains("row") || input.toLowerCase().contains("col")) {
                    System.out.println("Please enter numbers only, without words.");
                    continue;
                }

                // Handle different input formats
                if (input.contains(",")) {
                    // Handle "1,2" format
                    String[] parts = input.split(",");
                    if (parts.length != 2) {
                        System.out.println("Invalid format. Try again.");
                        continue;
                    }
                    row = Integer.parseInt(parts[0]) - 1;
                    col = Integer.parseInt(parts[1]) - 1;
                } else if (input.length() == 2) {
                    // Handle "12" format
                    row = Character.getNumericValue(input.charAt(0)) - 1;
                    col = Character.getNumericValue(input.charAt(1)) - 1;
                } else {
                    System.out.println("Invalid format. Please enter two numbers (examples: '1 2', '12', or '1,2')");
                    continue;
                }

                // Validate coordinates
                if (row >= 0 && row < board.getRows() && col >= 0 && col < board.getCols()) {
                    Card card = board.getCard(row, col);
                    if (!card.isFlipped()) {
                        return card;
                    }
                    System.out.println("Card is already flipped. Try another one.");
                } else {
                    System.out.println("Invalid coordinates. Please enter numbers between 1 and " +
                            board.getRows() + " for row and 1 to " + board.getCols() + " for column.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only.");
            } catch (Exception e) {
                System.out.println("Unexpected error. Please try again.");
            }
        }
    }

    private void announceWinner() {
        System.out.println("\n=== Game Over! All cards have been matched! ===");
        System.out.println("=======================================");

        Player winner = players.get(0);
        boolean tie = false;

        for (int i = 1; i < players.size(); i++) {
            Player current = players.get(i);
            if (current.getScore() > winner.getScore()) {
                winner = current;
                tie = false;
            } else if (current.getScore() == winner.getScore()) {
                tie = true;
            }
        }

        if (tie) {
            System.out.println("*** It's a tie game! Well played everyone! ***");
        } else {
            System.out.println("*** Congratulations " + winner.getName() + "! You've won the game! ***");
            System.out.println("Amazing performance with " + winner.getScore() + " pairs found!");
        }

        System.out.println("\nFinal Scores:");
        System.out.println("---------------");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore() + " pairs");
        }

        System.out.println("\nThanks for playing! Good game everyone!");
        System.out.println("=======================================");
    }

    // New method to reveal all cards
    private void revealAllCards() {
        System.out.println("\n=== Revealing all cards ===");
        System.out.println("------------------------");
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Card card = board.getCard(i, j);
                if (!card.isFlipped()) {
                    card.flip();
                }
            }
        }
        board.displayBoard();
    }
}