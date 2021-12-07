package UserAuthentication;

import java.io.Serializable;
import java.security.Principal;

public class SystemPrincipal implements Principal, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String name;
	//private final String password;

	public SystemPrincipal(String name) {
		this.name = name;
		//this.password = password;
	}

	@Override
	public String getName() {
		System.out.println("SystemPrincipal.getName...");
		return name;
	}
	
	/*
	public String getPassword() {
		System.out.println("SystemPrincipal.getPassword...");
		return password;
	}*/

	public boolean equals(Object object) {
		boolean flag = false;
		if (object instanceof SystemPrincipal)
			flag = name.equals(((SystemPrincipal) object).getName());
		return flag;
	}
}