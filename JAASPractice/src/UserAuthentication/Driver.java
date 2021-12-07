package UserAuthentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Scanner;

import javax.security.auth.Subject;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class Driver {
	
	public enum Action {
		//action1("View Categories"), action2("Display User Info"), Logout("Logout");
		action1("View Categories"), Logout("Logout");
		
		private String text;
		private static boolean flag = false;
		
		Action(String string) {
			// TODO Auto-generated constructor stub
			this.text = string;
		}
		
		private static final HashMap<String, Action> KEYS = new HashMap<>();
	    
	    static {
	        for (Action e: values()) {
	            KEYS.put(e.text, e);
	        }
	    }


	    public static Action valueOfLabel2(String label) {
	        return KEYS.get(valueOfLabel(label));
	    }
	    
		public static Action valueOfLabel(String label) {
		    for (Action e : values()) {
		        if (e.text.equals(label)) {
		           flag = true;
		        	return e;
		        }
		    }
		    flag = false;
		    return null;
		}
		
		public String getText()
		{
			return text;
		}
	};

	public static void main(String[] args) throws LoginException, URISyntaxException {
		Driver driver = new Driver();
		System.setProperty("java.security.auth.login.config", "jaasprac.config");
		LoginContext loginContext = null;
		SystemLoginModule loginObj = new SystemLoginModule();
		while (true) {
//			
			loginContext = new LoginContext("Test", new SystemCallbackHandler());

			System.out.println("Would you like to:");
			System.out.println("1. Login");
			System.out.println("2. Create new account");
			System.out.println("3. Exit");
			System.out.println("Specify by entering the appropriate number.");
			
			Scanner sc = new Scanner(System.in);
			int x = sc.nextInt();
			
			try
			{
				switch(x)
				{
				case 1: try {
					
					loginContext.login();
					
					boolean flag = true;
					while (flag)
						flag = driver.performAction(loginContext);
				} catch (LoginException | IOException e) {
					System.out.println(e.getMessage());
				}
				
				
				break;
				
				case 2: 
					loginObj.createUser();
					
				break;
				
				case 3:
					System.exit(0);
					break;
				}
			}
			catch (IllegalArgumentException e) {
				System.out.println("Invalid Entry");
			}
		}

	}
	

	boolean performAction(LoginContext loginContext) throws IOException, LoginException, URISyntaxException {
		boolean flag = true;
		//System.out.println("Please specify action to take (usage : View Categories, Display User Info, Logout)");
		System.out.println("Please specify action to take (usage : View Categories, Logout)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String value = br.readLine();
			if(Action.KEYS.containsKey(value))
			{
				
			
			switch (Action.valueOfLabel(value)) {
			case Logout:
				PrivilegedAction<Object> privilegedAction3 = () -> {
					System.out.println("Logout was performed..");
					return null;
				};
				
				Subject.doAs(loginContext.getSubject(), privilegedAction3);
				System.out.println("by " + loginContext.getSubject().getPrincipals().iterator().next().getName());
				
				loginContext.logout();
				flag = false;
				break;
				
			case action1:
				
				PrivilegedAction<Object> privilegedAction2 = () -> {
					System.out.println("View Categories was performed..");
					return null;
				};
				Subject.doAs(loginContext.getSubject(), privilegedAction2);
				System.out.println("by " + loginContext.getSubject().getPrincipals().iterator().next().getName());
				
				WebReader obj = new WebReader();
				obj.readPage();
				
				break;
			/*	
			case action2:
				
				PrivilegedAction<Object> privilegedAction1 = () -> {
					System.out.println("Display User Info was performed..");
					return null;
				};
				Subject.doAs(loginContext.getSubject(), privilegedAction1);
				System.out.println("by " + loginContext.getSubject().getPrincipals().iterator().next().getName());
				
				System.out.printf("Username: %s%nPassword: %s%n", loginContext.getSubject().getPrincipals().iterator().next().getName(), loginContext.getSubject().getPrincipals().iterator().next().getName());
				
				break;
				*/
				default: 
				break;

			}
			}
			else
			{
				System.out.println("Invalid Entry");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid Entry");
		}
		return flag;
	}
}