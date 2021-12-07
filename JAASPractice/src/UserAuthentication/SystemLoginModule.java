package UserAuthentication;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class SystemLoginModule implements LoginModule {
	
	public static String[][] users = { { "user1", "password1" }, { "user2", "password2" }, { "user3", "password3" } };
	private Subject subject = null;
	private CallbackHandler callbackHandler = null;
	private SystemPrincipal sysPrincipal = null;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		System.out.println("SystemLoginModule.initialize...");
	}

	@Override
	public boolean login() throws LoginException {
		boolean flag = false;
		System.out.println("SystemLoginModule.login...");
		Callback[] callbackArray = new Callback[2];
		callbackArray[0] = new NameCallback("User name : ");
		callbackArray[1] = new PasswordCallback("Password : ", false);
		try {
			callbackHandler.handle(callbackArray);
			String name = ((NameCallback) callbackArray[0]).getName();
			String password = new String(((PasswordCallback) callbackArray[1]).getPassword());
			int i = 0;
			while (i < users.length) {
				if (users[i][0].equals(name) && users[i][1].equals(password)) {
					sysPrincipal = new SystemPrincipal(name);
					System.out.println("Authentication Success..");
					flag = true;
					break;
				}
				i++;
			}
			if (flag == false)
				throw new FailedLoginException("Authentication failure...");
		} catch (IOException | UnsupportedCallbackException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean createUser()
	{
		boolean flag = false;
		
		
		String[][] newArray = new String[users.length + 1][2];
		
		for(int i=0; i<users.length; i++)
		 {
			  for(int j=0; j<users[i].length; j++)
			  {
			    newArray[i][j]=users[i][j];
			  }
		 }
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter username:");
		String newUsername = sc.next();
		System.out.println("Please enter password:");
		String newPassword = sc.next();
		
		newArray[newArray.length-1][0] = newUsername;
		newArray[newArray.length-1][1] = newPassword;
		
		users = newArray;
		
		System.out.println("User Creation Success..");
				
		
		return flag;
	}

	@Override
	public boolean commit() throws LoginException {
		boolean flag = false;
		System.out.println("SystemLoginModule.commit...");
		if (subject != null && !subject.getPrincipals().contains(sysPrincipal)) {
			subject.getPrincipals().add(sysPrincipal);
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean abort() throws LoginException {
		if (subject != null && sysPrincipal != null && subject.getPrincipals().contains(sysPrincipal))
			subject.getPrincipals().remove(sysPrincipal);
		subject = null;
		sysPrincipal = null;
		System.out.println("SystemLoginModule.abort...");
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		subject.getPrincipals().remove(sysPrincipal);
		subject = null;
		sysPrincipal = null;
		System.out.println("SystemLoginModule.logout...");
		return true;
	}

}