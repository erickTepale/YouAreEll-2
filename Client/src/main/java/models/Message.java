package models;

/* 
 * POJO for an Message object
 */
public class Message {
    private String sequence;
    private String timestamp;
    private String message;
    private String fromid;
    private String toid;

    public  Message(){

    }

    public Message (String sequence, String timestamp, String fromId, String toId, String message) {
        this.message = message;
        this.fromid = fromId;
        this.toid = toId;
        this.sequence = sequence;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromId) {
        this.fromid = fromId;
    }

    public String getToid() {
        return toid;
    }

    public void setToid(String toid) {
        this.toid = toid;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sequence='" + sequence + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", message='" + message + '\'' +
                ", fromid='" + fromid + '\'' +
                ", toid='" + toid + '\'' +
                '}';
    }
}