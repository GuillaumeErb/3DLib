package raytracer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteRaytracerInterface extends Remote {
	public Color renderRay(Scene scene, int x, int y) throws RemoteException;
}
