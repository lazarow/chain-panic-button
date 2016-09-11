package pl.nowakowski_arkadiusz.chain_panic_button.models;

public class ChainLink {

    private final Integer id;
    private final ChainLinkType type;
    private final String name;
    private final String message;
    private final Boolean addLocation;
    private final Boolean addPhoto;
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

    public Boolean getAddLocation() {
        return addLocation;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getAddPhoto() {
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
        Boolean addLocation,
        Boolean addPhoto,
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

    public static ChainLink createCallChainLink(Integer id, String name, String phone) {
        return new ChainLink(id, ChainLinkType.CALL, name, null, null, null, phone, null, null);
    }

    public static ChainLink createSMSChainLink(
        Integer id,
        String name, String message,
        Boolean addLocation,
        Boolean addPhoto,
        String phone
    ) {
        return new ChainLink(id, ChainLinkType.CALL, name, message, addLocation, addPhoto, phone, null, null);
    }

    public static ChainLink createEmailChainLink(
            Integer id,
            String name, String message,
            Boolean addLocation,
            Boolean addPhoto,
            String email,
            String subject
    ) {
        return new ChainLink(id, ChainLinkType.CALL, name, message, addLocation, addPhoto, null, email, subject);
    }
}
