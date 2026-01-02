package exo.exo4save;

public class data {
    private int counter;
    private String text;
    private String text2;
    public data() {
        this.counter = 0;
        this.text = "";
        this.text2 = "";
    }

    public int getCounter() { return counter; }
    public void setCounter(int counter) { this.counter = counter; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getText2() { return text2; }
    public void setText2(String text2) { this.text2 = text2; }
}
