/**
 * Created by chad on 2/4/17.
 */

public class ResponseData {
    private String status;
    private String message;

    public ResponseData() {
        super();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
