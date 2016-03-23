package fr.iutinfo.skeleton.api;

public class Brocante {
	
	private int id;
	private String libelle;
	private String ville;
	private String date;
	
	
	public Brocante(){
		
	}
	
	public Brocante(String libelle, String ville, String date){
		setLibelle(libelle);
		setVille(ville);
		setDate(date);
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public String getVille() {
		return ville;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	@Override
    public String toString() {
        return id + ": " + libelle + ", " + ville + " <" + date + ">";
    }
	
	
}
