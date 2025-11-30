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
import org.eclipse.repositories.AnnonceDAO;

@WebServlet({"/annonce", "/annonce/create", "/annonce/edit", "/annonce/delete"})
public class AnnoncesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AnnonceDAO annonceDao;

    @Override
    public void init() {
        annonceDao = new AnnonceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String path = request.getServletPath();
        
        //Affichage annonce de l'utilisateur
        if (path.equals("/annonce")) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            List<Annonce> annonces = annonceDao.findByUtilisateur(utilisateur.getId());
            request.setAttribute("annonces", annonces);
            request.getRequestDispatcher("/WEB-INF/annonces.jsp").forward(request, response);
        }
        
        //Lien vers edit
        if (path.equals("/annonce/edit")) {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Annonce annonce = annonceDao.findById(id);
                if (annonce != null) {
                    request.setAttribute("annonce", annonce);
                    request.getRequestDispatcher("/WEB-INF/annonce-edit.jsp").forward(request, response);
                    return;
                }
            }
            response.sendRedirect(request.getContextPath() + "/annonce");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        String path = request.getServletPath();

        switch (path) {
            case "/annonce/create":
                String titre = request.getParameter("titre");
                String description = request.getParameter("description");
                String ville = request.getParameter("ville");
                double prix = Double.parseDouble(request.getParameter("prix"));

                Annonce nouvelle = new Annonce(titre, description, prix, ville, utilisateur.getId());
                annonceDao.save(nouvelle);

                request.setAttribute("message", "Annonce ajoutée avec succès !");
                response.sendRedirect(request.getContextPath() + "/annonce");
                break;

            case "/annonce/edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                String titreEdit = request.getParameter("titre");
                String descriptionEdit = request.getParameter("description");
                String villeEdit = request.getParameter("ville");
                double prixEdit = Double.parseDouble(request.getParameter("prix"));

                Annonce annonce = new Annonce(idEdit, titreEdit, descriptionEdit, prixEdit, villeEdit, utilisateur.getId());
                annonceDao.update(annonce);

                request.setAttribute("message", "Annonce modifiée avec succès !");
                response.sendRedirect(request.getContextPath() + "/annonce");
                break;

            case "/annonce/delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                annonceDao.remove(idDelete);

                request.setAttribute("message", "Annonce supprimée avec succès !");
                response.sendRedirect(request.getContextPath() + "/annonce");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/annonce");
        }
    }
}
