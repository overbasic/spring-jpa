package basic.query;

public record Count(
    long value
) {

    public Count countOne() {
        return new Count(value + 1);
    }
}