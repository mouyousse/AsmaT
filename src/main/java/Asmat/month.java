package Asmat;

public enum month {
    JANVIER(31),
    FÉVRIER(29),
    MARS(31),
    AVRIL(30),
    MAI(31),
    JUIN(30),
    JUILLET(31),
    AOÛT(31),
    SEPTEMBRE(30),
    OCTOBRE(31),
    NOVEMBRE(30),
    DÉCEMBRE(31);

    private final int days;

    month(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }
}
