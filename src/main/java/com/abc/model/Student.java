package com.abc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Value;

//import org.hibernate.envers.Audited;

@Entity
@Table(name = "student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@NotBlank
	@Column(nullable=false)
	private String name;
	
	@NotNull
	@Positive
	@Column(nullable = false)
	private long studentClass;
	
	@Column(columnDefinition = "BOOLEAN default TRUE", nullable = false)
	private boolean active=true;
	
	@NotNull
	@Positive
	@Column(nullable = false)
	private int admissionYear;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(long studentClass) {
		this.studentClass = studentClass;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Integer getAdmissionYear() {
		return admissionYear;
	}
	public void setAdmissionYear(Integer admissionYear) {
		this.admissionYear = admissionYear;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Student(long id, String name, long studentClass, boolean active, Integer admissionYear) {
		super();
		this.id = id;
		this.name = name;
		this.studentClass = studentClass;
		this.active = active;
		this.admissionYear = admissionYear;
	}
	public Student() {
		super();
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", studentClass=" + studentClass + ", active=" + active
				+ ", admissionYear=" + admissionYear + "]";
	}
	
	
	
}
