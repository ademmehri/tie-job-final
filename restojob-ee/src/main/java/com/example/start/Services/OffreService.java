package com.example.start.Services;

import com.example.start.Entities.Employee;

import com.example.start.Entities.Offre;
import com.example.start.Entities.cordoffre;
import com.example.start.Entities.listoffre;
import com.example.start.Repositories.EmployeeRepo;

import com.example.start.Repositories.OffreRepo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OffreService {
    @Autowired
    OffreRepo offreRepo;
    @Autowired
    FileService file;
     @Autowired
    EmployeeRepo employeeRepo;
     @Autowired
 	com.example.start.mail.EmailSenderService emailService;
     public Offre addoffre(cordoffre off) {
    	
    	 Employee emp=employeeRepo.findById(off.getIdemp()).get();
    	 Employee empr=employeeRepo.findById(off.getIdempr()).get();
    	 Offre offre=new Offre();
    	 offre.setDescriptionoffre(off.getDescriptionoffre());
    	 emp.getOffreemp().add(offre);
    	 empr.getOffreempr().add(offre);
    	 offre.setEmployee(emp);
    	 offre.setEmployeur(empr);
    	 emailService.sendemailoffre(emp.getEmail(),empr.getNom(),off.getDescriptionoffre());
    	 return offreRepo.save(offre);
     }
public List<listoffre> getoffre(Long idemp){
List<Offre> offres=	offreRepo.getoffre(idemp);
ArrayList<listoffre> listoffres=new ArrayList();
for(Offre of : offres) {
	Employee empr =employeeRepo.findById(of.getEmployeur().getId()).get();
	listoffre lof=new listoffre();
	lof.setDescoffre(of.getDescriptionoffre());
	lof.setNomcafe(empr.getNom());
	lof.setId(empr.getId());
	lof.setFile(file.getfileemp(empr.getId()));
	listoffres.add(lof);
}
	return listoffres;
	
}
public List<Offre>  getoffrecrrerparemployeur(Long idempr){
	return offreRepo.getoffrecrrerparemployeur(idempr);
}
public void annuleroffre(Long id) {
	offreRepo.annuleroffre(id);
}


}
