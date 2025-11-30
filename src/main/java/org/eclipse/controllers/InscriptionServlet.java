package org.eclipse.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.eclipse.models.Utilisateur;
import org.eclipse.repositories.UtilisateurDAO;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtilisateurDAO utilisateurDao;

    @Override
    public void init() {
        utilisateurDao = new UtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        String confirmation = request.getParameter("confirmation");

        //Check sur les mots de passe
        if (!motDePasse.equals(confirmation)) {
            request.setAttribute("erreur", "Les mots de passe ne correspondent pas");
            request.setAttribute("utilisateurSaisie", new Utilisateur(nom, prenom, email, ""));
            request.getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);
            return;
        }
        
        //Check sur l'unicité de l'email
        if (utilisateurDao.findByEmail(email) != null) {
            request.setAttribute("erreur", "Cet email est déjà utilisé");
            request.setAttribute("utilisateurSaisie", new Utilisateur(nom, prenom, email, ""));
            request.getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);
            return;
        }

        Utilisateur u = new Utilisateur(nom, prenom, email, motDePasse);
        utilisateurDao.save(u);

        request.setAttribute("message", "Inscription réussie ! Vous pouvez maintenant vous connecter");
        request.getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);
    }
}
