<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes annonces</title>
    <%@ include file="partial/_links.jsp"%>
</head>
<body>
    <%@ include file="partial/_menu.jsp"%>

    <div class="container mt-4">
        <h2 class="text-center mb-4">Mes annonces</h2>

        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">${erreur}</div>
        </c:if>

        <hr>
        <c:if test="${empty annonces}">
            <p>Aucune annonce pour le moment.</p>
        </c:if>

        <div class="row">
            <c:forEach var="annonce" items="${annonces}">
                <div class="col-md-4 mb-4">
                    <div class="card shadow-sm position-relative">
                        <div class="card-body">
                            <h5 class="card-title">${annonce.titre}</h5>
                            <p class="card-text">${annonce.description}</p>
                            <p><strong>Prix :</strong>
                                <fmt:formatNumber value="${annonce.prix}" type="number" groupingUsed="true"/> €
                            </p>
                            <p><strong>Ville :</strong> ${annonce.ville}</p>

                            <!-- Boutons modifier / supprimer -->
                            <div class="d-flex justify-content-between mt-3">
                                <a href="${pageContext.request.contextPath}/annonce/edit?id=${annonce.id}" class="btn btn-sm btn-primary">Modifier</a>
                                
                                <form action="${pageContext.request.contextPath}/annonce/delete" method="post" 
                                      onsubmit="return confirm('Voulez-vous vraiment supprimer cette annonce ?');">
                                    <input type="hidden" name="id" value="${annonce.id}">
                                    <button type="submit" class="btn btn-sm btn-danger">Supprimer</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <hr>

        <!-- Formulaire création d'une nouvelle annonce -->
        <h3 class="mt-4">Ajouter une nouvelle annonce</h3>
        <form action="${pageContext.request.contextPath}/annonce/create" method="post">
            <div class="mb-3">
                <label for="titre" class="form-label">Titre</label>
                <input type="text" id="titre" name="titre" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea id="description" name="description" class="form-control" rows="3"></textarea>
            </div>

            <div class="mb-3">
                <label for="prix" class="form-label">Prix</label>
                <input type="number" id="prix" name="prix" class="form-control" step="0.01" required>
            </div>

            <div class="mb-3">
                <label for="ville" class="form-label">Ville</label>
                <input type="text" id="ville" name="ville" class="form-control">
            </div>

            <button type="submit" class="btn btn-success w-100">Ajouter l'annonce</button>
        </form>

    </div>
</body>
</html>
