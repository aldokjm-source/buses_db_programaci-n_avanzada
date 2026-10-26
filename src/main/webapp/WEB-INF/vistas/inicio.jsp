<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Transporte - Gestión de buses</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 32px; color: #222; max-width: 900px; }
        .estado { padding: 16px; border: 1px solid #ccc; margin: 20px 0; }
        .ok { color: #0f5132; background: #d1e7dd; }
        .error { color: #842029; background: #f8d7da; }
        li { margin-bottom: 8px; }
    </style>
</head>
<body>
    <h1>Gestión de buses y viajes</h1>
    <p>Verificación de conexión y acceso a la gestión de buses.</p>

    <c:choose>
        <c:when test="${conectado}">
            <div class="estado ok" role="status"><c:out value="${mensaje}" /></div>
        </c:when>
        <c:otherwise>
            <div class="estado error" role="alert"><c:out value="${mensaje}" /></div>
        </c:otherwise>
    </c:choose>

    <p>Esta comprobación valida la conexión, no la existencia de las tablas ni sus datos.</p>
    <c:url var="urlInicio" value="/inicio" />
    <p><a href="${urlInicio}">Volver a verificar conexión</a></p>

    <c:url var="urlBuses" value="/buses" />
    <p><a href="${urlBuses}"><strong>Ir a la gestión de buses &rarr;</strong></a></p>

    <h2>Funcionalidades</h2>
    <ul>
        <li>Listado de buses con el nombre y código de su viaje.</li>
        <li>Creación, edición y eliminación (con confirmación) de buses.</li>
        <li>Selector de viajes cargado desde la base de datos.</li>
        <li>Validaciones en servidor y mensajes comprensibles.</li>
    </ul>
</body>
</html>
