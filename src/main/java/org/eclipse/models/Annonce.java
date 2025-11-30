package org.eclipse.models;

public class Annonce {
	Integer id;
	String titre;
	String description;
	Double prix;
	String ville;
	Integer utilisateur;

	private boolean favori;

	public Annonce() {
		super();
	}

	public Annonce(Integer id, String titre, String description, Double prix, String ville, Integer utilisateur) {
		super();
		this.id = id;
		this.titre = titre;
		this.description = description;
		this.prix = prix;
		this.ville = ville;
		this.utilisateur = utilisateur;
		this.favori = false;
	}

	public Annonce(String titre, String description, Double prix, String ville, Integer utilisateur) {
		super();
		this.titre = titre;
		this.description = description;
		this.prix = prix;
		this.ville = ville;
		this.utilisateur = utilisateur;
		this.favori = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Integer getIdUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(int utilisateur) {
		this.utilisateur = utilisateur;
	}

	public boolean isFavori() {
		return favori;
	}

	public void setFavori(boolean favori) {
		this.favori = favori;
	}

}
