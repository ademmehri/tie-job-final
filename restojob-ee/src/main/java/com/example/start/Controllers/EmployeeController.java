package com.example.start.Controllers;

import com.example.start.Entities.AuthentifiactionModel;
import com.example.start.Entities.Employee;
import com.example.start.Entities.File;
import com.example.start.Entities.Role;
import com.example.start.Entities.listemps;
import com.example.start.Entities.patronemp;
import com.example.start.Entities.rechercheemp;
import com.example.start.Repositories.EmployeeRepo;
import com.example.start.Services.Emailservice;
import com.example.start.Services.EmployeeService;
import com.example.start.mail.EmailSenderService;


import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(value = "*")
public class EmployeeController {
    @Autowired
    private EmployeeRepo personneRepo;
    @Autowired
    EmailSenderService emailserv;
    @Autowired
    private EmployeeService personneService;
    @Autowired
	EmailSenderService emailService;
 



	@PostMapping("/addUser")
	public ResponseEntity<String> addUser(@RequestBody Employee user) {
		List<com.example.start.Entities.Role> roles = new ArrayList<>();
	    com.example.start.Entities.Role r = new com.example.start.Entities.Role();
	    
	    r.setRole("USER");
	    roles.add(r);
	    user.setEnabled(true);
	    user.setRoles(roles);
		personneService.saveUser(user);
		emailserv.sendemailinscrit(user.getEmail(),"Nous sommes ravis de vous accueillir sur TIE JOB ! Votre compte a été créé avec succès");
        return ResponseEntity.ok("User added successfully!");
    }
	@PostMapping("/addentreprise")
	public ResponseEntity<String> addentreprise(@RequestBody Employee user) {
		List<com.example.start.Entities.Role> roles = new ArrayList<>();
	    com.example.start.Entities.Role r = new com.example.start.Entities.Role();
	    r.setRole("ENTR");
	    roles.add(r);
	    user.setEnabled(true);
	    user.setRoles(roles);
		personneService.saveUser(user);
		emailserv.sendemailinscritemployeur(user.getEmail());
        return ResponseEntity.ok("User added successfully!");
    }
	@GetMapping("/exists/{email}")
    public boolean checkUserExists(@PathVariable String email) {
        return personneService.hasUserWithEmail(email);
    }
	@PostMapping("/verifieremail")
	public ResponseEntity<Map<String, String>> registerUser(@RequestParam("email") String email) {
	    
	        // Send verification email and get the generated token
	        String verificationToken = emailService.sendVerificationEmail(email);
	        System.out.println("Email sent successfully. Verification token: " + verificationToken);
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Verification token sent to email");
	        response.put("token", verificationToken);

	        return ResponseEntity.ok(response);
	    }
    @PostMapping("/savefichier/{mail}")
    public String savefichier(@RequestParam("file") MultipartFile file,@PathVariable String mail) throws IOException {
        return personneService.addEmployeefile(mail, file);
    }
    @GetMapping("/sendemail/{mail}")
    public String sendemail(@PathVariable String mail) throws IOException, MessagingException {
        return personneService.sendemail(mail);
    }
    @GetMapping("/sendemailoffre/{mail}/{qui}")
    public String sendemail(@PathVariable String mail,@PathVariable String qui) throws IOException, MessagingException {
        return personneService.sendemailoffre(mail, qui);
    }
    @PostMapping("/addcv/{mail}")
    public String addcv(@RequestParam("file") MultipartFile file,@PathVariable String mail) throws IOException {
       
    	
        return personneService.addcv(mail, file);
    }

    @PostMapping("/save")
    public Employee savepersonne(@RequestBody Employee personne) throws IOException, MessagingException {
    	
        return  personneService.addemployee(personne) ;
    }
    @PostMapping("/update")
    public Employee updateemployee(@RequestBody Employee e){
      
        return   personneService.updateemployee(e);
    }
    @PostMapping("/recherche")
    public List<patronemp> rechercheemployee(@RequestBody rechercheemp rech){
      return   personneService.getpatronemp(rech);
    }
    @GetMapping("/recherchegouver/{gouv}")
    public List<patronemp> rechercheemployeegouv(@PathVariable String gouv){
      return   personneService.getlistemployeegouv(gouv);
    }
    @GetMapping("/getemployeebyemail/{mail}")
    public Employee getemployee(@PathVariable String mail){

      return   personneService.getemployeebyemail(mail);
    }
    @GetMapping("/getemployeebyid/{id}")
    public Employee getemployee(@PathVariable Long id){
    
      return   personneService.getemployeebyid(id);
    }
    @GetMapping("/getemployeebycin/{cin}")
    public Employee getemployeebycin(@PathVariable String cin){
    
      return   personneService.getemployeebycin(cin);
    }
    @GetMapping("/motpasseoubliee/{mail}")
    public String motpasseoublie(@PathVariable String mail) throws MessagingException{
    
      return   personneService.motpasseoublie(mail);
    }
    @GetMapping("/getallemployee")
    public List<listemps> getallemployee() throws MessagingException{
    
      return   personneService.getallemployee();
    }
	@PostMapping("/forgetPassword/{email}")
	public ResponseEntity<Map<String, String>> forgetPassword(@PathVariable String email) {
	    
	        // Send verification email and get the generated token
	        String resetToken = emailService.sendVerificationEmail(email);
	        System.out.println("Email sent successfully. Verification token: " + resetToken);
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Reset token sent to email");
	        response.put("token", resetToken);

	        return ResponseEntity.ok(response);
	    }
	@PostMapping("/updatePassword")
	public ResponseEntity<String> updatePassword(@RequestBody Employee user)  {
		// Validate the email and newPassword
        if (StringUtils.isBlank(user.getEmail()) || StringUtils.isBlank(user.getPassword())) {
            return ResponseEntity.badRequest().body("Email and newPassword are required.");
        }
        // Update the password
        personneService.updatePassword(user.getEmail(), user.getPassword());
        return ResponseEntity.ok("Password updated successfully.");
    }
	@GetMapping("/getuserbyemail/{email}")
    public Employee getuserbyemail(@PathVariable String email) throws MessagingException{
    
      return   personneService.getuserbyemail(email);
    }
	@GetMapping("/getemployees")
    public List<Employee> getemployees() throws MessagingException{
    
      return   personneService.getemployees();
    }
	@GetMapping("/rechercheParEtatAndGouvernoratAndSpecialiteAndSexe/{etat}/{gouvernerat}/{specialite}/{sexe}")
    public List<Employee> rechercheparEtatAndGouvernoratAndSpecialiteAndSexe(@PathVariable String etat ,@PathVariable  String gouvernerat
    		,@PathVariable  String specialite ,@PathVariable  String sexe) {
		return personneService.rechercheparEtatetGouvernoratetSpecialiteetSexe(etat, gouvernerat,specialite,sexe);  
   }

    @GetMapping("/rechercheemployeeGold")
 public List<Employee> rechercheremployeeGold(){
	 return personneService.rechercherEmployeursGold();
	 }
    @GetMapping("/rechercheemployeesuperieur")
    public List<Employee> rechercheremployeePremium(){
   	 return personneService.rechercherEmployeursSuperieur();
   	 }
    //rechercheEmployeeRestaurer
    @GetMapping("/rechercheemployeerestaurer")
 public List<Employee> rechercheremployeerestaurer(){
	 return personneService.rechercherEmployeursRestaurer();
	 }
    //rechercheEmployeeServir
    @GetMapping("/rechercheemployeeServir")
 public List<Employee> rechercheremployeeServir(){
	 return personneService.rechercherEmployeursServir();
	 }
    
    //{**********Restaurant******}
    //rechercheEmployeeGold
    @GetMapping("/rechercheemployeeGold_r")
 public List<Employee> rechercheremployeeGold_r(){
	 return personneService.rechercherEmployeursGold_restaurant();
	 }
    
    
    //rechercheEmployeeSuperieur
    @GetMapping("/rechercheemployeeSuperieur_r")
 public List<Employee> rechercheremployeePremium_r(){
	 return personneService.rechercherEmployeursSuperieur_restaurant();
	 }
    
    
    //rechercheEmployeeRestaurer
    @GetMapping("/rechercheemployeeRestaurer_r")
 public List<Employee> rechercheremployeeRestaurer_r(){
	 return personneService.rechercherEmployeursRestaurer_restaurant();
	 }
    
    //rechercheEmployeePremium
    @GetMapping("/rechercheemployeeServir_r")
 public List<Employee> rechercheremployeeServir_r(){
	 return personneService.rechercherEmployeursServir_restaurant();
	 }

    @GetMapping("/getrole/{email}")
 public List<Role> getrole(@PathVariable String email){
	 return personneService.getroleuser(email);
	 }
    @GetMapping("/getsp")
 public ResponseEntity<Object> getspecialite(){
	 return personneService.getnbspecaliter();
	 }



}