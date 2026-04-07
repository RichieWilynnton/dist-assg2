package dto;
import java.io.Serializable;

public abstract class AuthResponse implements Serializable {
    public final boolean success;
    public final String message;

    AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
