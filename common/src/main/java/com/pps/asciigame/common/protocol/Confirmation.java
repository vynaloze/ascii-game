package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.User;

public class Confirmation extends Message {
	
	private final String contents;

	public Confirmation(User user, String contents) {
		super(user);
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}

}
