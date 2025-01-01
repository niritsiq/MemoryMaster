import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private final Card[][] cards;
    private final int rows;
    private final int cols;

    public Board(int rows, int cols) {
        if (rows * cols % 2 != 0) {
            throw new IllegalArgumentException("Board must have even number of cards");
        }

        this.rows = rows;
        this.cols = cols;
        this.cards = new Card[rows][cols];
        initializeBoard();
    }

    private void initializeBoard() {
        List<Card> cardList = new ArrayList<>();
        for (int i = 0; i < (rows * cols) / 2; i++) {
            cardList.add(new Card(i));
            cardList.add(new Card(i));
        }
        Collections.shuffle(cardList);

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cards[i][j] = cardList.get(index++);
            }
        }
    }

    public Card getCard(int row, int col) {
        return cards[row][col];
    }

    public void displayBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(cards[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}