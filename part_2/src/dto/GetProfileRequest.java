package dto;

public class GetProfileRequest extends AuthRequest {
    public GetProfileRequest(String username) {
        super(username);
    }
}
