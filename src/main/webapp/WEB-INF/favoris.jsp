<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Mes Favoris</title>
<%@ include file="partial/_links.jsp"%>
</head>
<body class="bg-light">
	<%@ include file="partial/_menu.jsp"%>

	<div class="container mt-4 ">

		<c:if test="${empty utilisateurConnecte}">
			<h2 class="text-center mb-4">Mes favoris</h2>
		</c:if>

		<hr>
		
		<c:if test="${empty favoris}">
            <p>Aucune annonce pour le moment.</p>
        </c:if>

		<div class="row">
			<c:forEach var="annonce" items="${favoris}">
				<div class="col-md-4 mb-4">
					<div class="card shadow-sm position-relative">
						<!-- Formulaire pour retirer le favori -->
						<c:if test="${not empty sessionScope.utilisateur}">
							<form action="${pageContext.request.contextPath}/favori"
								method="post" class="position-absolute top-0 end-0 m-2">
								<input type="hidden" name="idAnnonce" value="${annonce.id}">
								<!-- Ici, toutes les annonces sont des favoris donc étoile pleine -->
								<button type="submit" class="btn btn-link p-0 m-0">
									<i class="fas fa-star text-warning fa-lg"></i>
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