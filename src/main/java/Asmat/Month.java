package Asmat;

public enum Month {
    JANVIER(31),
    FEVRIER(29),
    MARS(31),
    AVRIL(30),
    MAI(31),
    JUIN(30),
    JUILLET(31),
    AOUT(31),
    SEPTEMBRE(30),
    OCTOBRE(31),
    NOVEMBRE(30),
    DECEMBRE(31);

    private final int days;

    Month(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }
}
