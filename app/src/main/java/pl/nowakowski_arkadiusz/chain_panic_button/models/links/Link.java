package pl.nowakowski_arkadiusz.chain_panic_button.models.links;

public abstract class Link {

    protected LinkType type;
    private String name;

    public LinkType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
