<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Buses - Listado</title>
    <%@ include file="_estilos.jspf" %>
</head>
<body>
    <c:url var="urlInicio" value="/inicio" />
    <c:url var="urlCrear" value="/buses/crear" />
    <nav><a href="${urlInicio}">Inicio</a></nav>

    <h1>Buses</h1>
    <p>Listado de buses registrados y el viaje al que está asociado cada uno.</p>

    <%@ include file="_mensajes.jspf" %>

    <a class="btn" href="${urlCrear}">+ Nuevo bus</a>

    <c:choose>
        <c:when test="${empty buses}">
            <p>No hay buses para mostrar.</p>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                    <tr>
                        <th>Marca</th>
                        <th>Modelo</th>
                        <th>Patente</th>
                        <th>Viaje</th>
                        <th>Código viaje</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="b" items="${buses}">
                        <c:url var="urlEditar" value="/buses/editar"><c:param name="id" value="${b.id}" /></c:url>
                        <c:url var="urlEliminar" value="/buses/eliminar"><c:param name="id" value="${b.id}" /></c:url>
                        <tr>
                            <td><c:out value="${b.marca}" /></td>
                            <td><c:out value="${b.modelo}" /></td>
                            <td><c:out value="${b.patente}" /></td>
                            <td><c:out value="${b.viaje.nombre}" /></td>
                            <td><c:out value="${b.viaje.codigo}" /></td>
                            <td>
                                <a href="${urlEditar}">Editar</a> |
                                <a href="${urlEliminar}">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
