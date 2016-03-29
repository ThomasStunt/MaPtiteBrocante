package fr.iutinfo.skeleton.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Brocante {
	
	private int id;
	
	private String libelle;
	
	private String nomOrganisateur;
	private String emailOrganisateur;
	private String telOrganisateur;
	
	private String pays;
	private int departement;
	private String ville;
	private String codePostal;
	private String rue;
	
	private String date;
	private String heure_debut;
	private String heure_fin;
	
	private String salle;
	private boolean handicape;
	private float prixEmplacement;
	private boolean valide = false;
	
	Logger logger = LoggerFactory.getLogger(Brocante.class);
	
	public Brocante(){
		
	}

	public Brocante(int id, String libelle, String nomOrganisateur, String emailOrganisateur,
	String telOrganisateur, String pays, int departement, String ville, String codePostal,
	 String rue, String date, String heure_debut, String heure_fin, String salle, boolean handicape,
	 float prixEmplacement){
		
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public void sanitize(){
		 Class c = this.getClass();
		  Method[] m = c.getMethods();

		  ArrayList<String> getters = new ArrayList<String>();
		  ArrayList<String> setters = new ArrayList<String>();
		  //On parcourt le tableau de méthodes
		  for(int i = 0; i < m.length; i++)
		  {
			  String method =  m[i].getName();//m[i].toString().substring(m[i].toString().lastIndexOf('.') +1);
			  //logger.debug("---------------------------------------------------------");
			  //logger.debug("Method : " + method);
			   Class[] p = m[i].getParameterTypes();
			  if(method.startsWith("set") && p[0].getName().equals("java.lang.String")){
				  //logger.debug("Setter : " + method);
				  setters.add(method);
			  } else if(method.startsWith("get") && !method.equals("getClass")){
				  //logger.debug("Getter : " + method);
				  getters.add(method);
			  }

			  //logger.debug("---------------------------------------------------------");
		  }
		  try {
			  	Class cl = getClass();
				 for(int i = 0; i < setters.size(); i++){
					 int gId = getters.indexOf("get" + setters.get(i).substring(3));
					 Method get = cl.getMethod(getters.get(gId), null);
					 Method set = cl.getMethod(setters.get(i), String.class);
					 String value = (String) get.invoke(this, null);
					 logger.debug("value : " + value);
					 set.invoke(this, ESAPI.encoder().encodeForHTML(value));
				 }
		  } catch(Exception e){
			  logger.debug(e.toString());
		  }
	}

	public String getNomOrganisateur() {
		return nomOrganisateur;
	}


	public void setNomOrganisateur(String nomOrganisateur) {
		this.nomOrganisateur = nomOrganisateur;
	}


	public String getEmailOrganisateur() {
		return emailOrganisateur;
	}


	public void setEmailOrganisateur(String emailOrganisateur) {
		this.emailOrganisateur = emailOrganisateur;
	}


	public String getTelOrganisateur() {
		return telOrganisateur;
	}


	public void setTelOrganisateur(String telOrganisateur) {
		this.telOrganisateur = telOrganisateur;
	}


	public String getPays() {
		return pays;
	}


	public void setPays(String pays) {
		this.pays = pays;
	}


	public int getDepartement() {
		return departement;
	}


	public void setDepartement(int departement) {
		this.departement = departement;
	}


	public String getVille() {
		return ville;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public String getCodePostal() {
		return codePostal;
	}


	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}


	public String getRue() {
		return rue;
	}


	public void setRue(String rue) {
		this.rue = rue;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getHeure_debut() {
		return heure_debut;
	}


	public void setHeure_debut(String heure_debut) {
		this.heure_debut = heure_debut;
	}


	public String getHeure_fin() {
		return heure_fin;
	}


	public void setHeure_fin(String heure_fin) {
		this.heure_fin = heure_fin;
	}


	public String getSalle() {
		return salle;
	}


	public void setSalle(String salle) {
		this.salle = salle;
	}


	public boolean isHandicape() {
		return handicape;
	}


	public void setHandicape(boolean handicape) {
		this.handicape = handicape;
	}


	public float getPrixEmplacement() {
		return prixEmplacement;
	}


	public void setPrixEmplacement(float prixEmplacement) {
		this.prixEmplacement = prixEmplacement;
	}

	public boolean isValide() {
		return valide;
	}

	public void setValide(boolean valide) {
		this.valide = valide;
	}

	
}
