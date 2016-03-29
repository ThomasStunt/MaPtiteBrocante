package fr.iutinfo.skeleton.api;

public class Article {
	private int id;
	private String titre;
	private int theme;
	private String texte;
	
	public Article(){
		
	}
	
	public Article(int id, String titre, int theme, String texte){
		this.setId(id);
		this.setTitre(titre);
		this.setTheme(theme);
		this.setTexte(texte);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTheme() {
		return theme;
	}
	public void setTheme(int theme) {
		this.theme = theme;
	}
	public String getTexte() {
		return texte;
	}
	public void setTexte(String texte) {
		this.texte = texte;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	
	
}
