public enum Roman {
    I('I'), V('V'), X('X'), L('L'), C('C'), D('D'), M('M');
    // Enum by default returns String, for returning char were added variable asChar, constructor and getter
    private final char asChar;

    Roman(char asChar) {
        this.asChar = asChar;
    }

    public char asChar() {
        return asChar;
    }
}
