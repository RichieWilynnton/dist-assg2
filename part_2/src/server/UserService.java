package server;

import dto.GetProfileRequest;
import dto.GetProfileResponse;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {
    GetProfileResponse getProfile(GetProfileRequest request) throws RemoteException;
}
