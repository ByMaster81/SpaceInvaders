package game;

class Scores {
    private String name;
    private int score;

    public Scores(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}