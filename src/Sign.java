public enum Sign {
    MULTIPLIEDBY('*'), DIVIDEDBY('/'), PLUS('+'), MINUS('-');

    private final char sign;

    Sign(char sign) {
        this.sign = sign;
    }

    public char sign() {
        return sign;
    }
}
