package raytracer;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RemoteRaytracer extends UnicastRemoteObject 
							 implements RemoteRaytracerInterface {

	public RemoteRaytracer() throws RemoteException {
		super();
	}

	@Override
	public Color renderRay(Scene scene, int x, int y) throws RemoteException {
		return scene.renderPixel(x, y);
	}

	public static void main(String args[]) {
		try {
			RemoteRaytracer rr = new RemoteRaytracer();
			Naming.rebind("//machine/serveur", rr);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
