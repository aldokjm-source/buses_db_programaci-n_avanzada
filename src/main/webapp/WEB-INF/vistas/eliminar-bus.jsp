<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Buses - Eliminar</title>
    <%@ include file="_estilos.jspf" %>
</head>
<body>
    <c:url var="urlListar" value="/buses" />
    <c:url var="urlEliminar" value="/buses/eliminar" />
    <nav><a href="${urlListar}">&larr; Volver al listado</a></nav>

    <h1>Eliminar bus</h1>

    <%@ include file="_mensajes.jspf" %>

    <div class="msg error">¿Está seguro de que desea eliminar este bus? Esta acción no se puede deshacer.</div>

    <dl class="detalle">
        <dt>Marca</dt>   <dd><c:out value="${bus.marca}" /></dd>
        <dt>Modelo</dt>  <dd><c:out value="${bus.modelo}" /></dd>
        <dt>Patente</dt> <dd><c:out value="${bus.patente}" /></dd>
        <dt>Viaje</dt>   <dd><c:out value="${bus.viaje.nombre}" /> (<c:out value="${bus.viaje.codigo}" />)</dd>
    </dl>

    <form method="post" action="${urlEliminar}" class="acciones">
        <input type="hidden" name="id" value="${bus.id}">
        <button type="submit" class="btn peligro">Confirmar eliminación</button>
        <a class="btn sec" href="${urlListar}">Cancelar</a>
    </form>
</body>
</html>
