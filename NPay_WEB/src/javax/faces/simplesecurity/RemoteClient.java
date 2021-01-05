package javax.faces.simplesecurity;

import java.util.HashSet;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class representing remote client in web applications.
 *   
 * @author Przemysław Kudłacik
 *
 * @param <T> Any class allowing to store additional information to session (i.e. User entity)
 */
public class RemoteClient<T> {

	private String email;
	private String password;
	private String name;
	private String remoteAddr;
	private String remoteHost;
	private int remotePort;
	private T details;

	private HashSet<String> roles = new HashSet<String>();

	public RemoteClient() {
	}

	public RemoteClient(ServletRequest request) {
		this.remoteAddr = request.getRemoteAddr();
		this.remoteHost = request.getRemoteHost();
		this.remotePort = request.getRemotePort();
	}

	public RemoteClient(String email, String pass, String name, ServletRequest request) {
		this.email = email;
		this.password = pass;
		this.name = name;
		if (request != null) {
			this.remoteAddr = request.getRemoteAddr();
			this.remoteHost = request.getRemoteHost();
			this.remotePort = request.getRemotePort();			
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String login) {
		this.email = login;
	}

	public String getPass() {
		return password;
	}

	public void setPass(String pass) {
		this.password = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public T getDetails() {
		return details;
	}

	public void setDetails(T details) {
		this.details = details;
	}

	public HashSet<String> getRoles() {
		return roles;
	}

	public void setRoles(HashSet<String> roles) {
		this.roles = roles;
	}

	public boolean isInRole(String role) {
		return roles.contains(role);
	}

	public boolean isInOneRole(HashSet<String> roles) {
		boolean found = false;
		for (String role : roles) {
			if (this.roles.contains(role)) {
				found = true;
				break;
			}
		}
		return found;
	}

	public void store(HttpServletRequest request) {
		this.remoteAddr = request.getRemoteAddr();
		this.remoteHost = request.getRemoteHost();
		this.remotePort = request.getRemotePort();
		HttpSession session = request.getSession();
		session.setAttribute("remoteClient", this);
	}

	public static RemoteClient load(HttpSession session) {
		return (RemoteClient) session.getAttribute("remoteClient");
	}

}
