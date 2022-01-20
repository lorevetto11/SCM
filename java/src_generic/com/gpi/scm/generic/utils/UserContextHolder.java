package com.gpi.scm.generic.utils;

import com.gpi.scm.generic.dtos.UserDto;

public class UserContextHolder {

	private static final ThreadLocal<UserDto> contextHolder = new ThreadLocal<>();

	public static void setUser(UserDto user) {
		contextHolder.set(user);
	}

	public static UserDto getUser() {
		return contextHolder.get();
	}

	public static void clearUser() {
		contextHolder.remove();
	}
}
