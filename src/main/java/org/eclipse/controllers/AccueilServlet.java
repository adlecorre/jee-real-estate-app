package org.eclipse.controllers;

import java.io.IOException;
import java.util.List;

import org.eclipse.models.Annonce;
import org.eclipse.models.Utilisateur;
import org.eclipse.repositories.AnnonceDAO;
import org.eclipse.repositories.FavoriDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AnnonceDAO annonceDao;
    private FavoriDAO favoriDao;

    @Override
    public void init() throws ServletException {
        annonceDao = new AnnonceDAO();
        favoriDao = new FavoriDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utilisateur utilisateur = null;

        if (session != null) {
            utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        }

        List<Annonce> annonces = annonceDao.findNotByUtilisateur(utilisateur.getId());
        
        //Récupère les favoris de l'utilisateur
        for (Annonce a : annonces) {
            boolean estFavori = favoriDao.exists(utilisateur.getId(), a.getId());
            a.setFavori(estFavori);
        }

        request.setAttribute("annonces", annonces);
        request.getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
    }
}
