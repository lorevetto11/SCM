package com.gpi.scm.generic.dtos;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserDto extends GenericDto {

	private static final long serialVersionUID = 1L;

	private String firstname;
	private String lastname;
	private String username;
	

	private String password;
	private String email;
	private String phone;
	private String status;
	private String language;
	private OrganizationDto organization;
	private UserRoleDto role;

	@NotNull
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@NotNull
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    @Size(min=4,max=32)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password=password;
		//MD5(password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}

	@NotNull
	public UserRoleDto getRole() {
		return role;
	}

	public void setRole(UserRoleDto role) {
		this.role = role;
	}
	
/*	public String MD5(String md5) {
		   try {
		        MessageDigest md = MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes(Charset.forName("UTF-8")));
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		   		}
		        catch(NullPointerException e) {
		        	
		        }
		    catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
	}*/
}
