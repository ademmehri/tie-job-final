package com.example.start.Repositories;

import com.example.start.Entities.Employee;
import com.example.start.Entities.File;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {



	    boolean existsByCin(String cin);
	    Employee findByCin(String cin);
	    Optional<Employee> findBynom(String nom);
	    @Query(value = "select * from employee e where e.gouvernerat = :gouvernerat  and e.specialite = :spe", nativeQuery = true)
	    public List<Employee> getemployees(@Param("gouvernerat") String gouvernerat, @Param("spe") String spe);
	    @Query(value = "select * from employee e where e.gouvernerat = :gouvernerat and e.specialite = :spe and e.sexe = :sexe", nativeQuery = true)
	    public List<Employee> getemployeessexe(@Param("gouvernerat") String gouvernerat, @Param("spe") String spe,@Param("sexe") String sexe);
	    @Query(value = "select * from employee e where e.gouvernerat = :gouvernerat and e.role = 'user' ", nativeQuery = true)
	    public List<Employee> getemployeegouv(@Param("gouvernerat") String gouvernerat);
	    @Query(value = "select * from employee e where e.mail = :mail and e.password = :password ", nativeQuery = true)
	    public Employee loginemployee(@Param("mail") String mail,@Param("password") String password);
	    boolean existsBycin(String cin);
		Employee findByemail(String mail);

		Employee findBycin(String cin);
		List<Employee> findByEtat(String etat);
		List <Employee> findByGouvernerat(String gov);
		boolean existsByemail(String mail);
		List<Employee> findByEtatAndGouvernerat(String etat ,String gouvernerat);
		List<Employee> findBySpecialite(String specialite);
		List<Employee> findBySexe(String sexe);
		List<Employee> findByEtatAndGouverneratAndSpecialiteAndSexe(String etat, String gouvernerat, String specialite,
				String sexe);
		List<Employee> findBySexeAndSpecialiteAndGouvernerat(String sexe, String specialite, String gouvernerat);
		@Query(value = "SELECT COUNT(*) FROM employee e WHERE e.specialite LIKE :ch%", nativeQuery = true)
	    public int getempspecialiste(@Param("ch") String ch);

}
