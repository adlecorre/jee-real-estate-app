package org.eclipse.models;

public class Favori {
	Integer id;
	Integer idUtilisateur;
	Integer IdAnnonce;

	public Favori(Integer id, Integer utilisateur, Integer annonce) {
		super();
		this.id = id;
		this.idUtilisateur = utilisateur;
		this.IdAnnonce = annonce;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setUtilisateur(Integer utilisateur) {
		this.idUtilisateur = utilisateur;
	}

	public Integer getIdAnnonce() {
		return IdAnnonce;
	}

	public void setAnnonce(Integer annonce) {
		this.IdAnnonce = annonce;
	}

}
