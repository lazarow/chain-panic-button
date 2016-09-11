package pl.nowakowski_arkadiusz.chain_panic_button.models;

public enum ChainLinkType {
    CALL(2), EMAIL(1), SMS(0);

    private int value;

    private ChainLinkType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
