package hr.foi.air.t18.chatup;

/**
 * Interface for saving attachments (images and text files)
 * Created by Laptop on 29.12.2015..
 */
public interface SaveAttachments {

    void savePicture(String base64String);

    void saveTextDocument(String base64String);
}
