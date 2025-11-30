package org.eclipse.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import org.eclipse.models.Annonce;
import org.eclipse.models.Utilisateur;
import org.eclipse.repositories.FavoriDAO;

@WebServlet("/favori")
public class FavoriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FavoriDAO favoriDao;

    @Override
    public void init() {
        favoriDao = new FavoriDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        List<Annonce> favoris = favoriDao.findByUtilisateur(utilisateur.getId());

        request.setAttribute("favoris", favoris);
        request.getRequestDispatcher("/WEB-INF/favoris.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        int idAnnonce = Integer.parseInt(request.getParameter("idAnnonce"));
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        FavoriDAO favoriDao = new FavoriDAO();
        boolean estFavori = favoriDao.exists(utilisateur.getId(), idAnnonce);

        if (estFavori) {
            favoriDao.remove(utilisateur.getId(), idAnnonce);
        } else {
            favoriDao.save(utilisateur.getId(), idAnnonce);
        }
        
        //retour page d'où a été appelé POST/favori
        response.sendRedirect(request.getHeader("referer"));
    }

}

