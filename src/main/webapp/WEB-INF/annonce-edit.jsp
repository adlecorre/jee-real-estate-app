<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier l'annonce</title>
    <%@ include file="partial/_links.jsp"%>
</head>
<body>
    <%@ include file="partial/_menu.jsp"%>

    <div class="container mt-5" style="max-width:600px;">
        <h2 class="mb-4 text-center">Modifier l'annonce</h2>

        <form action="${pageContext.request.contextPath}/annonce/edit" method="post">
            <input type="hidden" name="id" value="${annonce.id}">

            <div class="mb-3">
                <label for="titre" class="form-label">Titre</label>
                <input type="text" id="titre" name="titre" class="form-control" value="${annonce.titre}" required>
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea id="description" name="description" class="form-control" rows="3">${annonce.description}</textarea>
            </div>

            <div class="mb-3">
                <label for="prix" class="form-label">Prix</label>
                <input type="number" id="prix" name="prix" class="form-control" step="0.01" value="${annonce.prix}" required>
            </div>

            <div class="mb-3">
                <label for="ville" class="form-label">Ville</label>
                <input type="text" id="ville" name="ville" class="form-control" value="${annonce.ville}">
            </div>

            <button type="submit" class="btn btn-primary w-100">Enregistrer les modifications</button>
        </form>
    </div>
</body>
</html>
