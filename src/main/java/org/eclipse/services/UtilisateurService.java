package org.eclipse.services;

import org.eclipse.repositories.AnnonceDAO;
import org.eclipse.repositories.FavoriDAO;
import org.eclipse.repositories.UtilisateurDAO;

public class UtilisateurService {
    private UtilisateurDAO utilisateurDao = new UtilisateurDAO();
    private AnnonceDAO annonceDao = new AnnonceDAO();
    private FavoriDAO favoriDao = new FavoriDAO();

    public boolean supprimerUtilisateurComplet(int idUtilisateur) {
        favoriDao.removeByUtilisateur(idUtilisateur);
        annonceDao.removeByUtilisateur(idUtilisateur);
        return utilisateurDao.remove(idUtilisateur);
    }
}

