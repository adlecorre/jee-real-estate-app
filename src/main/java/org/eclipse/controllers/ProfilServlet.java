package org.eclipse.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.eclipse.models.Utilisateur;
import org.eclipse.repositories.UtilisateurDAO;
import org.eclipse.services.UtilisateurService;

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDAO utilisateurDao;

	@Override
	public void init() {
		utilisateurDao = new UtilisateurDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/profil.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		String action = request.getParameter("action");

		if ("modifier".equals(action)) {
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String motDePasse = request.getParameter("motDePasse");
			String confirmation = request.getParameter("confirmation");

			//Check sur les mots de passe
			if (!motDePasse.isEmpty() && !motDePasse.equals(confirmation)) {
				request.setAttribute("erreur", "Les mots de passe ne correspondent pas");
				request.getRequestDispatcher("/WEB-INF/profil.jsp").forward(request, response);
				return;
			}

			//Check sur le mail
			Utilisateur uEmail = utilisateurDao.findByEmail(email);
			if (uEmail != null && uEmail.getId() != utilisateur.getId()) {
				request.setAttribute("erreur", "Cet email est déjà utilisé");
				request.getRequestDispatcher("/WEB-INF/profil.jsp").forward(request, response);
				return;
			}

			utilisateur.setNom(nom);
			utilisateur.setPrenom(prenom);
			utilisateur.setEmail(email);
			if (!motDePasse.isEmpty()) {
				utilisateur.setMotDePasse(motDePasse);
			}

			utilisateurDao.update(utilisateur);
			
			session.setAttribute("utilisateur", utilisateur);
			request.setAttribute("message", "Profil modifié avec succès");
			request.getRequestDispatcher("/WEB-INF/profil.jsp").forward(request, response);

		}
		if ("supprimer".equals(action)) {
			UtilisateurService utilisateurService = new UtilisateurService();
			if (utilisateurService.supprimerUtilisateurComplet(utilisateur.getId())) {
				session.invalidate();
				response.sendRedirect(request.getContextPath() + "/accueil");
			} else {
				request.setAttribute("erreur", "Erreur lors de la suppression de votre compte.");
				request.getRequestDispatcher("/WEB-INF/profil.jsp").forward(request, response);
			}
		}

	}
}
