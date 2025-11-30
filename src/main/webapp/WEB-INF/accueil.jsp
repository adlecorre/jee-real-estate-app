<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Accueil</title>
<%@ include file="partial/_links.jsp"%>
</head>
<body class="bg-light">
	<%@ include file="partial/_menu.jsp"%>

	<div class="container mt-4 ">
		<c:if test="${not empty utilisateur}">
			<h2 class="text-center">Bonjour ${utilisateur.prenom} 👋</h2>
		</c:if>

		<c:if test="${empty utilisateurConnecte}">
			<h2>Bienvenue sur notre site immobilier 🏠</h2>
			<p>Voici les annonces des autres utilisateurs :</p>
		</c:if>

		<hr>

		<div class="row">
			<c:forEach var="annonce" items="${annonces}">
				<div class="col-md-4 mb-4">
					<div class="card shadow-sm position-relative">
						<!-- Formulaire étoile -->
						<c:if test="${not empty sessionScope.utilisateur}">
							<form action="${pageContext.request.contextPath}/favori"
								method="post" class="position-absolute top-0 end-0 m-2">
								<input type="hidden" name="idAnnonce" value="${annonce.id}">
								<button type="submit" class="btn btn-link p-0 m-0">
									<c:choose>
										<c:when test="${annonce.favori}">
											<i class="fas fa-star text-warning fa-lg"></i>
										</c:when>
										<c:otherwise>
											<i class="far fa-star text-warning fa-lg"></i>
										</c:otherwise>
									</c:choose>
								</button>
							</form>
						</c:if>

						<div class="card-body">
							<h5 class="card-title">${annonce.titre}</h5>
							<p class="card-text">${annonce.description}</p>
							<p>
								<strong>Prix :</strong>
								<fmt:formatNumber value="${annonce.prix}" type="number"
									groupingUsed="true" />
								€
							</p>
							<p>
								<strong>Ville :</strong> ${annonce.ville}
							</p>
						</div>
					</div>
				</div>


			</c:forEach>

		</div>
	</div>
</body>
</html>
