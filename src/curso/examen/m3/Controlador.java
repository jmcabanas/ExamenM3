package curso.examen.m3;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.*;
import org.apache.log4j.helpers.Loader;

import curso.examen.m3.db.dao.SQLContactoDAO;
import curso.examen.m3.db.modelo.Contacto;
import curso.examen.m3.libreria.Util;

/**
 * Servlet implementation class Controlador
 */
@WebServlet("")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Logger.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controlador() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PropertyConfigurator.configure(Loader.getResource("log4j.properties"));
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
    	URL appResourceURL = loader.getResource("email.properties");
    	String propertiesFileRoute = appResourceURL.getPath();
    	propertiesFileRoute = propertiesFileRoute.substring(1, propertiesFileRoute.length());
    	
		Properties properties = new Properties();
		properties.load(new FileReader(propertiesFileRoute));
		
		String emailUser = properties.getProperty("emailUser");
		String passUser = properties.getProperty("passUser");
		
		String nombre = request.getParameter("nombre");
		String emailDestino = request.getParameter("correo");
		String provincia = request.getParameter("provincia");
		String asuntoEmail = request.getParameter("asunto");
		String cuerpoEmail = request.getParameter("mensaje");
		
		Boolean enviado = null;
		
		if(emailDestino!=null && asuntoEmail!=null && cuerpoEmail!=null) {
			SQLContactoDAO scd = new SQLContactoDAO();
			Contacto c = new Contacto();
			
			c.setNombre(nombre);
			c.setCorreo(emailDestino);
			c.setProvincia(provincia);
			c.setAsunto(asuntoEmail);
			c.setMensaje(cuerpoEmail);
			
			enviado = Util.enviarEmail(emailUser, passUser, emailDestino, asuntoEmail, cuerpoEmail);
			if(enviado) {
				c.setEnviado("Si");
				request.setAttribute("enviado", true);
				log.info("Mensaje enviado correctamente");
			}else{
				c.setEnviado("No");
				request.setAttribute("enviado", false);
				log.warn("No se ha podido enviar el mensaje");
			}
			scd.setContacto(c);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}
}