package fr.iutinfo.skeleton.api;

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
	private boolean valide;
	

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
