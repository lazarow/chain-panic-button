package pl.nowakowski_arkadiusz.chain_panic_button.models;

public class ChainLink {

    private final Integer id;
    private final ChainLinkType type;
    private final String name;
    private final String message;
    private final boolean addLocation;
    private final boolean addPhoto;
    private final String phone;
    private final String email;
    private final String subject;

    public Integer getId() {
        return id;
    }

    public ChainLinkType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public boolean getAddLocation() {
        return addLocation;
    }

    public boolean getAddPhoto() {
        return addPhoto;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public ChainLink(
        Integer id,
        ChainLinkType type,
        String name,
        String message,
        boolean addLocation,
        boolean addPhoto,
        String phone,
        String email,
        String subject
    ) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.message = message;
        this.addLocation = addLocation;
        this.addPhoto = addPhoto;
        this.phone = phone;
        this.email = email;
        this.subject = subject;
    }

    public boolean isNewRecord() {
        return id == null;
    }

    public static ChainLink createCallChainLink(Integer id, String name, String phone) {
        return new ChainLink(id, ChainLinkType.CALL, name, "", false, false, phone, "", "");
    }

    public static ChainLink createSMSChainLink(
        Integer id,
        String name, String message,
        boolean addLocation,
        boolean addPhoto,
        String phone
    ) {
        return new ChainLink(id, ChainLinkType.CALL, name, message, addLocation, addPhoto, phone, "", "");
    }

    public static ChainLink createEmailChainLink(
            Integer id,
            String name, String message,
            boolean addLocation,
            boolean addPhoto,
            String email,
            String subject
    ) {
        return new ChainLink(id, ChainLinkType.CALL, name, message, addLocation, addPhoto, "", email, subject);
    }
}
