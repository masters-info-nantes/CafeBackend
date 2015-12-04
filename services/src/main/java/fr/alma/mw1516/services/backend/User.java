package fr.alma.mw1516.services.backend;

import fr.alma.mw1516.api.backend.IUser;

public class User implements IUser {
	
	private String id, firstName, lastName;

	public User(String id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public String getLasName() {
		return this.lastName;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + " id: "+id;
	}
}
