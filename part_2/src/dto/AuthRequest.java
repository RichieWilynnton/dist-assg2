package dto;

import java.io.Serializable;

public abstract class AuthRequest implements Serializable {
    public String username;
    
    public AuthRequest(String username) {
        this.username = username;
    }
}
