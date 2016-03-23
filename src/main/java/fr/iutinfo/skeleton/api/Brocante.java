package fr.iutinfo.skeleton.api;

public class Brocante {
	
	private int id;
	private String libelle;
	private String ville;
	private String addresse;
	private String date;
	private String heure_debut;
	private String heure_fin;
	private String desc;
	private int user_id;
	
	public Brocante(){
		
	}
	
	public Brocante (String libelle, String ville, String addresse, String date, String heure_debut, String heure_fin, String desc, int user_in){
		setLibelle(libelle);
		setVille(ville);
		setDate(date);
		setAddresse(addresse);
		setHeure_debut(heure_debut);
		setHeure_fin(heure_fin);
		setDesc(desc);
		setUser_id(user_id);
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

	public String getAddresse() {
		return addresse;
	}

	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}

	public String getHeure_debut() {
		return heure_debut;
	}

	public void setHeure_debut(String heure_debut) {
		this.heure_debut = heure_debut;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getHeure_fin() {
		return heure_fin;
	}

	public void setHeure_fin(String heure_fin) {
		this.heure_fin = heure_fin;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	
}
