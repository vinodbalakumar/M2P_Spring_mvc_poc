package com.devcases.usermanager.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table
public class User implements Serializable {

	private static final long serialVersionUID = 6541162783907686900L;

	@Id
	@GeneratedValue
	private int sno;
	@Column
	private String name;

	@Version
	private long version;

	private LocalDate dob;

	private String mobile;

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getMobile() {
		return new String(this.mobile != null ? Base64.getDecoder().decode(this.mobile.getBytes()) : "".getBytes());
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
