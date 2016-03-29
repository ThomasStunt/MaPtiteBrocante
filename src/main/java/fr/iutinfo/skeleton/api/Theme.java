package fr.iutinfo.skeleton.api;

public class Theme {
	
	private int id;
	private String libelle;
	
	public Theme(){
		
	}
	
	public Theme(int id, String libelle){
		this.setId(id);
		this.setLibelle(libelle);
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

}
