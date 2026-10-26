<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Buses - Crear</title>
    <%@ include file="_estilos.jspf" %>
</head>
<body>
    <c:url var="urlListar" value="/buses" />
    <c:url var="urlGuardar" value="/buses/crear" />
    <nav><a href="${urlListar}">&larr; Volver al listado</a></nav>

    <h1>Nuevo bus</h1>

    <%@ include file="_mensajes.jspf" %>

    <form class="bus" method="post" action="${urlGuardar}">
        <%@ include file="_formulario-bus.jspf" %>
        <div class="acciones">
            <button type="submit" class="btn">Guardar</button>
            <a class="btn sec" href="${urlListar}">Cancelar</a>
        </div>
    </form>
</body>
</html>
