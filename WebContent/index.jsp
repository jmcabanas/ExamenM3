<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="curso.examen.m3.libreria.Util" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	<meta charset="ISO-8859-1">
	<title>Examen M3 - Jose Miguel Cabanas Garcia</title>
</head>
<body>
<%ArrayList<String> provincias = Util.obtenerListadoProvincias();%>
<%Boolean enviado = (Boolean) request.getAttribute("enviado"); %>

<div class="jumbotron text-center">
	<h1>Examen M3</h1>
	<p>Jose Miguel Cabanas Garcia</p>
</div>

<div class="container">
	<form id="mailForm" class="form-group" action="<% request.getContextPath();%>" method="post">
		<label>Nombre:</label><input class="form-control" type="text" name="nombre" placeholder="Nombre destinatario" required><br>
		<label>Correo:</label><input class="form-control" type="email" name="correo" placeholder="Email destinatario" maxlength="40" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" required><br>
		<label>Provincia:</label>
		<select class="form-control" name="provincia">
			<%if(provincias != null){
				for(String txt:provincias){%>
					<option><%=txt%></option>
				<%}
			}%>
		</select><br>
		<label>Asunto:</label><input class="form-control" type="text" name="asunto" placeholder="Asunto"><br>
		<label>Mensaje:</label><textarea class="form-control" rows="5" name="mensaje" placeholder="Mensaje" required></textarea><br>
		<input class="form-control" type="submit" name="botonEnviar" value="Enviar">
	</form><br>
		
	<%if(enviado != null){ 
		if(enviado){%>
			<div class="alert alert-success">
				<p>Mensaje enviado con éxito.</p>
			</div>
		<%}else{ %>
			<div class="alert alert-warning">
				<p>Algo salió mal, el mensaje no se envió.</p>
			</div>
		<%}%>
	<%}%>
	
</div>
</body>
</html>