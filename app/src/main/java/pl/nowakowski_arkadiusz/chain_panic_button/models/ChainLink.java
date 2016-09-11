package pl.nowakowski_arkadiusz.chain_panic_button.models;

import android.content.ContentValues;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ChainLink implements Serializable {

    private final Integer id;
    private final ChainLinkType type;
    private String name;
    private String message;
    private boolean addLocation;
    private boolean addPhoto;
    private String phone;
    private String email;
    private String subject;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAddLocation(boolean addLocation) {
        this.addLocation = addLocation;
    }

    public void setAddPhoto(boolean addPhoto) {
        this.addPhoto = addPhoto;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", getType().getValue());
        contentValues.put("name", getName());
        contentValues.put("message", getMessage());
        contentValues.put("addLocation", getAddLocation() ? 1 : 0);
        contentValues.put("addPhoto", getAddPhoto() ? 1 : 0);
        contentValues.put("phone", getPhone());
        contentValues.put("email", getEmail());
        contentValues.put("subject", getSubject());
        return contentValues;
    }

    public static ChainLink createSMSChainLink(
        Integer id,
        String name, String message,
        boolean addLocation,
        boolean addPhoto,
        String phone
    ) {
        return new ChainLink(id, ChainLinkType.SMS, name, message, addLocation, addPhoto, phone, "", "");
    }

    public static ChainLink createEmailChainLink(
            Integer id,
            String name, String message,
            boolean addLocation,
            boolean addPhoto,
            String email,
            String subject
    ) {
        return new ChainLink(id, ChainLinkType.EMAIL, name, message, addLocation, addPhoto, "", email, subject);
    }
}
