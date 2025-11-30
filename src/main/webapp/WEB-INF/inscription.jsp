<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Inscription</title>
<%@ include file="partial/_links.jsp"%>
</head>
<body class="bg-light">
	<%@ include file="partial/_menu.jsp"%>

	<div class="container mt-5">
    <div class="card shadow p-4 mx-auto" style="max-width: 700px;">
        <h2 class="mb-4 text-center">Créer un compte</h2>

        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>

        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">${erreur}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/inscription" method="post">
            <div class="mb-3">
                <label for="nom" class="form-label">Nom</label>
                <input type="text" class="form-control form-control-lg" id="nom" name="nom"
                       required value="${utilisateurSaisie.nom}">
            </div>

            <div class="mb-3">
                <label for="prenom" class="form-label">Prénom</label>
                <input type="text" class="form-control form-control-lg" id="prenom" name="prenom"
                       required value="${utilisateurSaisie.prenom}">
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control form-control-lg" id="email" name="email"
                       required value="${utilisateurSaisie.email}">
            </div>

            <div class="mb-3">
                <label for="motDePasse" class="form-label">Mot de passe</label>
                <input type="password" class="form-control form-control-lg" id="motDePasse" name="motDePasse"
                       required>
            </div>

            <div class="mb-3">
                <label for="confirmation" class="form-label">Confirmer le mot de passe</label>
                <input type="password" class="form-control form-control-lg" id="confirmation" name="confirmation"
                       required>
            </div>

            <button type="submit" class="btn btn-primary btn-lg w-100">S'inscrire</button>
        </form>
    </div>
</div>

</body>
</html>
