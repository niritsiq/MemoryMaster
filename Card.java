public class Card {
    private final int id;
    private boolean isFlipped;

    public Card(int id) {
        this.id = id;
        this.isFlipped = false;
    }

    public int getId() {
        return id;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void flip() {
        isFlipped = !isFlipped;
    }

    @Override
    public String toString() {
        return isFlipped ? String.valueOf(id) : "*";
    }
}