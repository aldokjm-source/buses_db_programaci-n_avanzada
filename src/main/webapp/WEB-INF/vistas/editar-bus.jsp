<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Buses - Editar</title>
    <%@ include file="_estilos.jspf" %>
</head>
<body>
    <c:url var="urlListar" value="/buses" />
    <c:url var="urlGuardar" value="/buses/editar" />
    <nav><a href="${urlListar}">&larr; Volver al listado</a></nav>

    <h1>Editar bus</h1>

    <%@ include file="_mensajes.jspf" %>

    <form class="bus" method="post" action="${urlGuardar}">
        <input type="hidden" name="id" value="<c:out value='${id}'/>">
        <%@ include file="_formulario-bus.jspf" %>
        <div class="acciones">
            <button type="submit" class="btn">Guardar cambios</button>
            <a class="btn sec" href="${urlListar}">Cancelar</a>
        </div>
    </form>
</body>
</html>
