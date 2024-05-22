package live.easytrain.application.exceptions;

public class EasyTrainException {

    private int status;

    private String message;

    private Long timeStamp;

    private String stackTrace;

    public EasyTrainException() {}

    public EasyTrainException(int status, String message, Long timeStamp, String stackTrace) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
        this.stackTrace = stackTrace;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
