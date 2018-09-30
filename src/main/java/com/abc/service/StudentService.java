package com.abc.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.abc.model.Student;

@Transactional
public interface StudentService extends JpaRepository<Student, Long> {
	public Student findById(long id);
	
	public Student findByName(String name);

	public List<Student> findAll();

	@Query(value = " select * from student where ( ?2 or admission_year >= ?1 ) and ( ?4 or admission_year <= ?3 ) and ( ?6 or student_class in ?5 ) and ( ?8 or active = ?7 ) order by id limit ?9 offset ?10 ;", nativeQuery = true)
	public List<Student> findStudentFilters(Integer admissionYearAfter, boolean admissionYearAfterFlag, Integer admissionYearBefore, boolean admissionYearBeforeFlag,
			List<Integer> studentClass, boolean studentClassFlag, boolean active, boolean activeFlag, int limit, int offset);

//	@Query(value = " select * from fec_user where ( ?2 or created > ?1 ) and ( ?4 or created < ?3) and ( ?6 or user_type in ?5 ) and ( ?8 or lower( name ) like ?7) and ( ?10 or mobile like ?9 ) and ( ?12 or lower( email_id ) like ?11 ) and ( ?14 or id like ?13 ) and ( is_deleted = 0 ) and ( ?16 or is_available = ?15 ) order by created DESC limit ?17 offset ?18 ;", nativeQuery = true)
//	public List<StudentService> findStudentServiceByFilters(String from, int fromFlag, String to, int toFlag,
//			List<String> userType, int userTypeFlag, String name, int nameFlag, String mobile, int mobileFlag,
//			String emailId, int emailIdFlag, String userId, int userIdFlag, String isAvailable, int isAvailableFlag,
//			int limit, int offset);
//
//	@Query(value = " select count(*) from fec_user where ( ?2 or created > ?1 ) and ( ?4 or created < ?3) and ( ?6 or user_type in ?5 ) and ( ?8 or lower( name ) like ?7) and ( ?10 or mobile like ?9 ) and ( ?12 or lower ( email_id ) like ?11 ) and ( ?14 or id like ?13 ) and ( ?16 or is_available = ?15 ) and ( is_deleted = 0 ) ;", nativeQuery = true)
//	public int findStudentServiceCountByFilters(String from, int fromFlag, String to, int toFlag, List<String> userType,
//			int userTypeFlag, String name, int nameFlag, String mobile, int mobileFlag, String emailId, int emailIdFlag,
//			String userId, int userIdFlag, String isAvailable, int isAvailableFlag);
//
//	@Query(value = "select * from (select *,(select count(*) from service_request_no where agent_id = e.id and is_deleted = 0 and status != 'Closed') as count from fec_user as e where e.user_type = ?1 and e.is_available = 1 and e.is_deleted = 0 order by count) as f where f.count < ?2 limit 1;", nativeQuery = true)
//	public StudentService findNextAgentToAssignServiceTask(String agentLevel, long limit);
//
//	@Query(value = "select * from fec_user where unavailable_date_from is not null and unavailable_date_to is not null and (STR_TO_DATE(?1, '%Y-%m-%d') between STR_TO_DATE(unavailable_date_from, '%Y-%m-%d') and STR_TO_DATE(unavailable_date_to, '%Y-%m-%d'));", nativeQuery = true)
//	public List<StudentService> getUnavailable(String date);
//
//	@Query(value = "select * from (select *,(select count(*) from fec_collections where agent_id = e.id and is_deleted = 0) as count from fec_user as e where e.user_type = ?1 and e.is_available = 1 and e.is_deleted = 0 order by count) as f limit 1;", nativeQuery = true)
//	public StudentService findNextAgentToAssignCollection(String agentLevel);

}
