package hr.foi.air.t18.chatup.Mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by JurmanLap on 29.12.2015..
 */
public class ByteArrayDataSource implements javax.activation.DataSource {
    private byte[] data;
    private String type;

    /**
     * Constructor
     */
    public ByteArrayDataSource(byte[] data, String type) {
        super();
        this.data = data;
        this.type = type;
    }
    /**
     * Constructor
     */
    public ByteArrayDataSource(byte[] data) {
        super();
        this.data = data;
    }

    /**
     * Setter for setting type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for get type of Content
     * returns type
     */
    public String getContentType() {
        if (type == null)
            return "application/octet-stream";
        else
            return type;
    }
    /**
     * Getter for get Input Stream
     * returns  input stream with data
     */
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(data);
    }
    /**
     * Getter for get Name
     */
    public String getName() {
        return "ByteArrayDataSource";
    }
    /**
     * Getter for get Output Stream
     */
    public OutputStream getOutputStream() throws IOException {
        throw new IOException("Not Supported");
    }
}