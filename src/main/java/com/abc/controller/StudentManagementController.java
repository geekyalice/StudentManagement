package com.abc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.abc.StudentManagement;
import com.abc.model.Student;
import com.abc.service.StudentService;

@RestController
public class StudentManagementController {
	
	public final Logger logger = LoggerFactory.getLogger(StudentManagement.class);

	@Autowired
	StudentService studentService;

	// -------------------Retrieve All Students---------------------------------------------

	@GetMapping("/students")
	public  @ResponseBody ResponseEntity<?> listAllStudents(@RequestParam Map<String, String> customQuery) {
		logger.info("in api");
		logger.info("in api");
		boolean admissionYearAfterFlag = true;
		boolean admissionYearBeforeFlag = true;
		boolean studentClassFlag = true;
		boolean activeFlag = true;
		int admissionYearAfter = 0;
		int admissionYearBefore = 0;
		int limit = 20;
		int offset = 0;
		List<Integer> studentClass = new ArrayList<Integer>();
		studentClass.add(1);
		boolean active = true;
		if(customQuery.containsKey("admissionYearAfter")) {
			admissionYearAfterFlag = false;
			admissionYearAfter = Integer.parseInt(customQuery.get("admissionYearAfter"));
			logger.info("found admissionYearAfter");
		}
			
		if(customQuery.containsKey("admissionYearBefore")) {
			admissionYearBeforeFlag = false;
			admissionYearBefore = Integer.parseInt(customQuery.get("admissionYearBefore"));
			logger.info("found admissionYearBefore");
		}
		if(customQuery.containsKey("classes")) {
			studentClassFlag = false;
			studentClass.clear();
			String studentClassParam = customQuery.get("classes");
			String[] studentClassArray = studentClassParam.split(",");
			for(String s: studentClassArray) studentClass.add(Integer.valueOf(s));
			logger.info("found studentClass");
		}
		if(customQuery.containsKey("active")) {
			activeFlag = false;
			active = customQuery.get("active").equals("true")?true:false;
		}
		if(customQuery.containsKey("pageSize")) {
			limit = Integer.parseInt(customQuery.get("pageSize"));
		}
		if(customQuery.containsKey("pageNumber")) {
			offset = (Integer.parseInt(customQuery.get("pageNumber")) -1 ) * limit;
		}
		List<Student> Students = studentService.findStudentFilters(admissionYearAfter, admissionYearAfterFlag,
				admissionYearBefore, admissionYearBeforeFlag, studentClass, studentClassFlag, active, activeFlag, limit,
				offset);
		//List<Student> Students = studentService.findAll();
		if (Students.isEmpty()) {
			return new ResponseEntity<String>("No data", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Student>>(Students, HttpStatus.OK);
	}
	
	// -------------------Retrieve Single Student------------------------------------------

	@RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getStudent(@PathVariable("id") long id) {
		logger.info("Fetching Student with id {}", id);
		Student Student = studentService.findById(id);
		if (Student == null) {
			logger.error("Student with id {} not found.", id);
			return new ResponseEntity<String>("Student not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Student>(Student, HttpStatus.OK);
	}

	// -------------------Create a Student-------------------------------------------

	@RequestMapping(value = "/student/", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createStudent(@Valid @RequestBody Student Student) {
		logger.info("Creating Student : {}", Student);
		Student existingStudent = studentService.findByName(Student.getName());
		if (existingStudent != null) {
			logger.error("Unable to create. A Student with name {} already exist", Student.getName());
			return new ResponseEntity<String>("Unable to create. A Student with same name already exist",
					HttpStatus.CONFLICT);
		}
		logger.info(Student.toString());
		studentService.save(Student);
		return new ResponseEntity<String>("Student Created", HttpStatus.CREATED);
	}

	// ------------------- Update a Student ------------------------------------------------

	@RequestMapping(value = "/student/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateStudent(@PathVariable("id") long id, @RequestBody Student Student) {
		logger.info("Updating Student with id {}", id);

		Student currentStudent = studentService.findById(id);

		if (currentStudent == null) {
			logger.error("Unable to update. Student with id {} not found.", id);
			return new ResponseEntity<String>("Student not found", HttpStatus.NOT_FOUND);
		}

		//currentStudent.setName(Student.getName());
		//currentStudent.setActive(Student.getActive());
		//currentStudent.setAdmissionYear(Student.getAdmissionYear());
		currentStudent.setStudentClass(Student.getStudentClass());
		studentService.save(currentStudent);
		return new ResponseEntity<Student>(currentStudent, HttpStatus.OK);
	}

	// ------------------- Delete a Student-----------------------------------------

	@RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteStudent(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Student with id {}", id);

		Student Student = studentService.findById(id);
		if (Student == null) {
			logger.error("Unable to delete. Student with id {} not found.", id);
			return new ResponseEntity<String>("Student not found",HttpStatus.NOT_FOUND);
		}
		studentService.deleteById(id);
		return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Students-----------------------------

	@RequestMapping(value = "/student/", method = RequestMethod.DELETE)
	public ResponseEntity<Student> deleteAllStudents() {
		logger.info("Deleting All Students");

		studentService.deleteAll();
		return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
	}
}
