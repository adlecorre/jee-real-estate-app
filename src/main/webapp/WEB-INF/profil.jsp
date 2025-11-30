<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Mon Profil</title>
<%@ include file="partial/_links.jsp"%>
</head>
<body>
	<%@ include file="partial/_menu.jsp"%>

	<div class="container mt-4">
		<h2 class="text-center mb-4">Mon Profil</h2>

		<hr>

		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>

		<c:if test="${not empty erreur}">
			<div class="alert alert-danger">${erreur}</div>
		</c:if>

		<form action="${pageContext.request.contextPath}/profil" method="post">
			<input type="hidden" name="action" value="modifier">

			<div class="mb-3">
				<label for="nom" class="form-label">Nom</label> <input type="text"
					id="nom" name="nom" class="form-control" required
					value="${utilisateur.nom}">
			</div>

			<div class="mb-3">
				<label for="prenom" class="form-label">Prénom</label> <input
					type="text" id="prenom" name="prenom" class="form-control" required
					value="${utilisateur.prenom}">
			</div>

			<div class="mb-3">
				<label for="email" class="form-label">Email</label> <input
					type="email" id="email" name="email" class="form-control" required
					value="${utilisateur.email}">
			</div>

			<div class="mb-3">
				<label for="motDePasse" class="form-label">Nouveau mot de
					passe (optionnel)</label> <input type="password" id="motDePasse"
					name="motDePasse" class="form-control">
			</div>

			<div class="mb-3">
				<label for="confirmation" class="form-label">Confirmer mot
					de passe</label> <input type="password" id="confirmation"
					name="confirmation" class="form-control">
			</div>

			<button type="submit" class="btn btn-primary w-100">Modifier
				mon profil</button>
		</form>

		<hr>

		<form action="${pageContext.request.contextPath}/profil" method="post"
			onsubmit="return confirm('Voulez-vous vraiment supprimer votre compte ?');">
			<input type="hidden" name="action" value="supprimer">
			<button type="submit" class="btn btn-danger w-100 mt-3">Supprimer
				mon compte</button>
		</form>
	</div>

</body>
</html>