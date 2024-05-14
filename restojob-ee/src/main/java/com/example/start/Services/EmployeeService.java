package com.example.start.Services;

import com.example.start.Entities.Employee;

import com.example.start.Entities.File;
import com.example.start.Entities.listemps;
import com.example.start.Entities.patronemp;
import com.example.start.Entities.rechercheemp;
import com.example.start.Repositories.EmployeeRepo;
import com.example.start.Repositories.FileRepo;
/*import com.rhplateforme.entities.Role;
import com.rhplateforme.entities.User;*/

import io.jsonwebtoken.lang.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.mail.MessagingException;
@Transactional
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
     private FileService fileService;
    @Autowired
    FileRepo fileemprep;
    @Autowired
    Emailservice emailserv;
    @Autowired    
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public List<com.example.start.Entities.Role> getroleuser(String email) {
		 Employee user = employeeRepo.findByemail(email);
		return user.getRoles();
	}
    public Employee saveUser(Employee user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return employeeRepo.save(user);
	}
    public List<Employee> getemployees(){
    	return employeeRepo.findAll();
    }
   
	public boolean hasUserWithEmail(String email) {
		return employeeRepo.existsByemail(email);
	}
    
  public Employee addemployee(Employee emp) throws MessagingException{
	 if(employeeRepo.existsByemail(emp.getCin())) {
		 return null; 
	 }
        return employeeRepo.save(emp);
   }
  public String addEmployeefile(String mail, MultipartFile file) throws IOException {
		
		if(employeeRepo.existsByemail(mail)) {
			File f=fileService.addfile(file);
			Employee e=employeeRepo.findByemail(mail);
			
			e.getListemp().add(f);
			f.setEmp(e);
			employeeRepo.save(e);
			return "Fichier inserer";
		}
		
		return "personne non existe";
	}
  public String addcv(String mail, MultipartFile file) throws IOException {
		if(employeeRepo.existsByemail(mail)) {
			File f=fileService.addcv(file);
			Employee e=employeeRepo.findByemail(mail);
			
			e.getListemp().add(f);
			f.setEmp(e);
			employeeRepo.save(e);
			return "CV inserer";
		}
		
		return "personne non existe";
	}

    public Employee updateemployee(Employee employee) {
        Employee e;
        e=  employeeRepo.findById(employee.getId()).get();
       
        e.setEmail(employee.getEmail());
        e.setDate_nais(employee.getDate_nais());
        e.setNum(employee.getNum());
        e.setDescription(employee.getDescription());
        e.setNom(employee.getNom());
      
     e.setCity(employee.getCity());
        e.setSexe(employee.getSexe());
        e.setSpecialite(employee.getSpecialite());
        e.setExp(employee.getExp());
        e.setGouvernerat(employee.getGouvernerat());
        e.setDescription(employee.getDescription());
        e.setCp(employee.getCp());
        e.setPack(employee.getPack());
        e.setDuree(employee.getDuree());
        return employeeRepo.save(e) ;
    }

    public Employee getemployeebyid(Long id) {
    	return employeeRepo.findById(id).get();
    }
    public Employee getemployeebyemail(String email) {
    	return employeeRepo.findByemail(email);
    }
    public Employee getemployeebycin(String cin) {
    	return employeeRepo.findBycin(cin);
    }
    public List<patronemp> getpatronemp(rechercheemp rech){
    	System.out.println(rech.getSexe());
    	if(rech.getSexe().equals("tous")) {
    	
    	List<Employee> listspemp=employeeRepo.getemployees(rech.getGouvernerat(), rech.getSpe());
    	
    	List<patronemp> patemp=new ArrayList<>();
    	for(Employee emp: listspemp) {
    		patronemp pe=new patronemp();
    		System.out.println(emp.getGouvernerat());
    		File file= fileService.getfileemp(emp.getId());
    		pe.setNom(emp.getNom());
    	pe.setCity(emp.getCity());
    		pe.setRegion(emp.getGouvernerat());
    		pe.setDpe(rech.getSpe());
    		pe.setFilee(file);
    		pe.setNum(emp.getNum());
    		pe.setId(emp.getId());
    		patemp.add(pe);
    		
    	}
    	return patemp;
    	}
    	List<Employee> listspemp=employeeRepo.getemployeessexe(rech.getGouvernerat(),rech.getSpe(), rech.getSexe());
    	
    	List<patronemp> patemp=new ArrayList<>();
    	for(Employee emp: listspemp) {
    		patronemp pe=new patronemp();
    		System.out.println(emp.getGouvernerat());
    		File file= fileService.getfileemp(emp.getId());
    		pe.setNom(emp.getNom());
    		pe.setCity(emp.getCity());
    		pe.setRegion(emp.getGouvernerat());
    		pe.setDpe(rech.getSpe());
    		pe.setFilee(file);
    		pe.setNum(emp.getNum());
    		pe.setId(emp.getId());
    		patemp.add(pe);}
    	
    	return patemp;
    }
    public List<patronemp> getlistemployeegouv(String gouver){
    	List<Employee> listspemp=employeeRepo.getemployeegouv(gouver);
    	
    	List<patronemp> patemp=new ArrayList<>();
    	for(Employee emp: listspemp) {
    		patronemp pe=new patronemp();
    		System.out.println(emp.getGouvernerat());
    		File file= fileService.getfileemp(emp.getId());
    		pe.setNom(emp.getNom());
    		pe.setRegion(emp.getGouvernerat());
    		pe.setCity(emp.getCity());
    		pe.setDpe(emp.getSpecialite());
    		pe.setFilee(file);
    		pe.setNum(emp.getNum());
    		pe.setId(emp.getId());
    		patemp.add(pe);
    		}
    	
    	return patemp;
    	
    }
    public String motpasseoublie(String mail) throws MessagingException {
    	Employee e=employeeRepo.findByemail(mail);
    	if(e==null) {
    		return "personne non existe";
    	}
    
        String link="votre mot passe est "+e.getCp();
     emailserv.send(e.getEmail(), link);
     System.out.println("fffffffff");
    	return "Message envoyee";
    }
    public String sendemail(String email) throws MessagingException {
    	 String link="Bienvenue chez RestoJob";
         emailserv.send(email, link);
    	return "ok";
    }
    public String sendemailoffre(String email,String qui) throws MessagingException {
   	 String link="Vous avez une offre par "+qui;
        emailserv.send(email, link);
   	return "ok";
   }
    public List<listemps> getallemployee(){
	List<Employee> listspemp=employeeRepo.findAll();
    	
    	List<listemps> patemp=new ArrayList<>();
    	for(Employee emp: listspemp) {
    		listemps pe=new listemps();
    		File file= fileService.getfileemp(emp.getId());
    		pe.setNom(emp.getNom());
    		
    	
    		pe.setSpe(emp.getSpecialite());
    		pe.setFilee(file);
    		//pe.setRole(emp.getRole());
    		patemp.add(pe);
    		}
    	
    	return patemp;
    }
    public void updatePassword(String email, String newPassword)  {
        Employee user = employeeRepo.findByemail(email);
        if (user != null) {
            // Update the user's password
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            employeeRepo.save(user);
        
    }
        
 }
    public Employee getuserbyemail(String email) {
    	return employeeRepo.findByemail(email);
    }
    
    
    public List<Employee> rechercheparEtatetGouvernoratetSpecialiteetSexe(String etat,String gouvernerat,String specialite,String sexe){
  	  
  	   if(etat!=null && gouvernerat==null && specialite==null && sexe==null) {
  		  return employeeRepo.findByEtat(etat);
  	  }
  	  else if(etat==null &&  specialite==null && sexe==null && gouvernerat!=null) {
  		  return employeeRepo.findByGouvernerat(gouvernerat);
  	  }
  	else if(etat==null &&  specialite!=null && sexe==null && gouvernerat!=null) {
		  return employeeRepo.findBySpecialite(specialite);
	  }
  	else if(etat==null &&  specialite==null && sexe!=null && gouvernerat!=null) {
		  return employeeRepo.findBySexe(sexe);
	  }
  	else if(etat==null &&  specialite!=null && sexe!=null && gouvernerat!=null) {
		  return employeeRepo.findBySexeAndSpecialiteAndGouvernerat(sexe,specialite,gouvernerat);
	  }
  	else if(etat==null &&  specialite!=null && sexe!=null && gouvernerat!=null) {
		  return employeeRepo.findBySexeAndSpecialiteAndGouvernerat(sexe,specialite,gouvernerat);
	  }
  	  else {
  		  return employeeRepo.findByEtatAndGouverneratAndSpecialiteAndSexe(etat, gouvernerat,specialite,sexe);
  	  }
    }
    
    public List<Employee> rechercherEmployeursGold() {
        // Créer manuellement une liste de noms de métiers
        List<String> nomsMetiersRecherches = java.util.Arrays.asList("Directeur d’hôtel", "Directeur d'hebergement", 
        		"Adjoint de directeur en hotellerie","Directeur de la restauration",
        		"Directuer financier d'un hotel","Guest relation manager",
        		"Manager dans la restauration","Spa manager","Yield manager",
        		"Chef de cuisine","Chef de partie",
        		"Commis de cuisine","Chef-gérant en restauration collective", 
        		"Cuisinier","Pizzaiolo",
        		"Barman","Patissier",
        		"Boucher","Boulanger","Poissonnier","Chocalatier-confisseur","Charcutier-traiteur","Econome","Gérant",
        		"Serveur de restaurant ","Chef de rang", 
        		"Maitre d'hotel","Garçon de café",
        		"Plongeur","Serveuse",
        		"Majordome(Butler)","Bagagiste","Chasseur","liftier","Groom","Portier");

        // Récupérer tous les employeurs de la base de données
        List<Employee> employeurs = employeeRepo.findAll();
        List<Employee> resultats = new ArrayList<>();

        // Parcourir tous les employeurs
        for (Employee employeur : employeurs) {
            String metierEmployeur = employeur.getSpecialite();
            // Vérifier si l'un des métiers de l'employeur correspond à un métier recherché
            System.out.println("okkkkkkjhh");
                if (nomsMetiersRecherches.contains(metierEmployeur)) {
                	System.out.println("okkkkkk");
                    resultats.add(employeur);
                     
                }
            }
        return resultats;
        

        
    }
    public List<Employee> rechercherEmployeursSuperieur() {
        // Créer manuellement une liste de noms de métiers
        List<String> nomsMetiersRecherches = java.util.Arrays.asList("Directeur d’hôtel", "Directeur d'hebergement", 
        		"Adjoint de directeur en hotellerie","Directeur de la restauration",
        		"Directuer financier d'un hotel","Guest relation manager",
        		"Manager dans la restauration","Spa manager","Yield manager");

        // Récupérer tous les employeurs de la base de données
        List<Employee> employeurs = employeeRepo.findAll();
        List<Employee> resultats = new ArrayList<>();

        // Parcourir tous les employeurs
        for (Employee employeur : employeurs) {
            String metierEmployeur = employeur.getSpecialite();
            // Vérifier si l'un des métiers de l'employeur correspond à un métier recherché
            System.out.println("okkkkkkjhh");
                if (nomsMetiersRecherches.contains(metierEmployeur)) {
                	System.out.println("okkkkkk");
                    resultats.add(employeur);
                     
                }
            }
        return resultats;
        

        
    }
    
    public List<Employee> rechercherEmployeursRestaurer() {
        // Créer manuellement une liste de noms de métiers
        List<String> nomsMetiersRecherches = java.util.Arrays.asList("Chef de cuisine","Chef de partie",
        		"Commis de cuisine","Chef-gérant en restauration collective", 
        		"Cuisinier","Pizzaiolo",
        		"Barman","Patissier",
        		"Boucher","Boulanger","Poissonnier","Chocalatier-confisseur","Charcutier-traiteur","Econome","Gérant");

        // Récupérer tous les employeurs de la base de données
        List<Employee> employeurs = employeeRepo.findAll();
        List<Employee> resultats = new ArrayList<>();

        // Parcourir tous les employeurs
        for (Employee employeur : employeurs) {
            String metierEmployeur = employeur.getSpecialite();
            // Vérifier si l'un des métiers de l'employeur correspond à un métier recherché
            
                if (nomsMetiersRecherches.contains(metierEmployeur)) {
                    resultats.add(employeur);
                     
                }
            }
        return resultats;
        

        
    }
    
    public List<Employee> rechercherEmployeursServir() {
    	System.out.println("pkk");
        // Créer manuellement une liste de noms de métiers
        List<String> nomsMetiersRecherches = java.util.Arrays.asList("Serveur de restaurant ","Chef de rang", 
        		"Maitre d'hotel","Garçon de café",
        		"Plongeur","Serveuse",
        		"Majordome(Butler)","Bagagiste","Chef de cuisine","Chasseur","liftier","Groom","Portier");

        // Récupérer tous les employeurs de la base de données
        List<Employee> employeurs = employeeRepo.findAll();
        List<Employee> resultats = new ArrayList<>();

        // Parcourir tous les employeurs
        for (Employee employeur : employeurs) {
            String metierEmployeur = employeur.getSpecialite();
            // Vérifier si l'un des métiers de l'employeur correspond à un métier recherché
            
                if (nomsMetiersRecherches.contains(metierEmployeur)) {
                    resultats.add(employeur);
                     
                }
            }
        return resultats;
        

        
    }

  //{**************************pack Hotel--------------------------------------}
    
    
    
    
  //{-----------------------pack restaurant--------------------------------------}
    //Gold
    public List<Employee> rechercherEmployeursGold_restaurant() {
        // Créer manuellement une liste de noms de métiers
        List<String> nomsMetiersRecherches = java.util.Arrays.asList("Directeur de la restauration",
        		"Directeur financier d'un restaurant", 
        		"Gérant","Econome",
        		"Manager de restauration","Chef de cuisine","Chef de partie",
        		"Commis de cuisine", 
        		"Cuisinier","Pizzaiolo",
        		"Barman","Patissier",
        		"Boucher","Boulanger","Poissonnier","Chocalatier-confisseur","Charcutier-traiteur","Econome","Gérant",
        		"Serveur","Chef de rang",
        		"Plongeur","Serveuse",
        		"Portier");

        // Récupérer tous les employeurs de la base de données
        List<Employee> employeurs = employeeRepo.findAll();
        List<Employee> resultats = new ArrayList<>();

        // Parcourir tous les employeurs
        for (Employee employeur : employeurs) {
            String metierEmployeur = employeur.getSpecialite();
            // Vérifier si l'un des métiers de l'employeur correspond à un métier recherché
            
                if (nomsMetiersRecherches.contains(metierEmployeur)) {
                    resultats.add(employeur);
                     
                }
            }
        return resultats;
        

        
    }
    
    
    
  //rechercheMetiersPremium
    

    public List<Employee> rechercherEmployeursSuperieur_restaurant() {
        // Créer manuellement une liste de noms de métiers
        List<String> nomsMetiersRecherches = java.util.Arrays.asList("Directeur de la restauration",
        		"Directeur financier d'un restaurant", 
        		"Gérant","Econome",
        		"Manager de restauration");

        // Récupérer tous les employeurs de la base de données
        List<Employee> employeurs = employeeRepo.findAll();
        List<Employee> resultats = new ArrayList<>();

        // Parcourir tous les employeurs
        for (Employee employeur : employeurs) {
            String metierEmployeur = employeur.getSpecialite();
            // Vérifier si l'un des métiers de l'employeur correspond à un métier recherché
            
                if (nomsMetiersRecherches.contains(metierEmployeur)) {
                    resultats.add(employeur);
                     
                }
            }
        return resultats;
        

        
    }
    
    public List<Employee> rechercherEmployeursRestaurer_restaurant() {
        // Créer manuellement une liste de noms de métiers
        List<String> nomsMetiersRecherches = java.util.Arrays.asList("Chef de cuisine","Chef de partie",
        		"Commis de cuisine", 
        		"Cuisinier","Pizzaiolo",
        		"Barman","Patissier",
        		"Boucher","Boulanger","Poissonnier","Chocalatier-confisseur","Charcutier-traiteur","Econome","Gérant");

        // Récupérer tous les employeurs de la base de données
        List<Employee> employeurs = employeeRepo.findAll();
        List<Employee> resultats = new ArrayList<>();

        // Parcourir tous les employeurs
        for (Employee employeur : employeurs) {
            String metierEmployeur = employeur.getSpecialite();
            // Vérifier si l'un des métiers de l'employeur correspond à un métier recherché
            
                if (nomsMetiersRecherches.contains(metierEmployeur)) {
                    resultats.add(employeur);
                     
                }
            }
        return resultats;
        

        
    }
    
    public List<Employee> rechercherEmployeursServir_restaurant() {
        // Créer manuellement une liste de noms de métiers
        List<String> nomsMetiersRecherches = java.util.Arrays.asList("Serveur","Chef de rang",
        		"Plongeur","Serveuse",
        		"Portier");

        // Récupérer tous les employeurs de la base de données
        List<Employee> employeurs = employeeRepo.findAll();
        List<Employee> resultats = new ArrayList<>();

        // Parcourir tous les employeurs
        for (Employee employeur : employeurs) {
            String metierEmployeur = employeur.getSpecialite();
            // Vérifier si l'un des métiers de l'employeur correspond à un métier recherché
            
                if (nomsMetiersRecherches.contains(metierEmployeur)) {
                    resultats.add(employeur);
                     
                }
            }
        return resultats;
        

        
    }
    public ResponseEntity<Object> getnbspecaliter(){
    	 Map<String, Object> responseData = new HashMap<>();
    	int nbs=0;
    	int nbp=0;
    	int nbB=0;
    	int nbb=0;
    	int nbi=0;
    	int nbpor=0;
    	int nbchef;
    	int nbdir;
    	int nbm;
    	  List<String> nomsMetiersRecherches = java.util.Arrays.asList("Serveur",
          		"Plongeur","Barman","Baggagiste","Pizzaiolo",
          		"Portier");
    	  nbchef= employeeRepo.getempspecialiste("Chef");
    	  nbdir= employeeRepo.getempspecialiste("Directeur");
    	  nbm= employeeRepo.getempspecialiste("Manager");
    	   List<Employee> employees = employeeRepo.findAll();
    	   for (Employee employee : employees) {
               String metierEmployee = employee.getSpecialite();
                   if (metierEmployee.equals("Serveur")) {
                     nbs++;
                   }
                   if (metierEmployee.equals("Plongeur")) {
                       nbp++;
                     }
                   if (metierEmployee.equals("Barman")) {
                       nbB++;
                     }
                   if (metierEmployee.equals("Baggagiste")) {
                       nbb++;
                     }
                   if (metierEmployee.equals("Pizzaiolo")) {
                       nbi++;
                     }
                   if (metierEmployee.equals("Portier")) {
                       nbpor++;
                     }
                   
               }
    	     responseData.put("Serveur", nbs);
    	     responseData.put("Plongeur", nbp);
    	     responseData.put("Barman", nbB); 
    	     responseData.put("Baggagiste", nbb); 
    	     responseData.put("Pizzaiolo", nbi);  
    	     responseData.put("Portier", nbpor);
    	     responseData.put("Chef", nbchef);
    	     responseData.put("Directeur", nbdir);
    	     responseData.put("Manager", nbm);
    	     return ResponseEntity.ok(responseData);
    }


}
