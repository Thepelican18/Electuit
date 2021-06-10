package com.daniel.appweb.electuit_project.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.daniel.appweb.electuit_project.entity.User;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		//User user =  session.get(User.class, 2);
		//User user = new User(null,"OranguNoSeQUedaCalvo", "qwerra18@gmail.com", "the_suspect");
		//Circuit circuit = new Circuit(null,"circuito1",new Date(1000000),user);
		//session.save(circuit);
		if(request.getParameter("action") != null) {
			
			if(request.getParameter("action").equals("logout")) {
				
				request.getSession().removeAttribute("userLogged");
				response.sendRedirect("login.html");
				return;
			}
			if(request.getParameter("action").equals("deleteAccount")) {
				User userLogged = (User) request.getSession().getAttribute("userLogged");
				request.getSession().removeAttribute("userLogged");
		         if(userLogged!=null){
		            session.delete(userLogged);
		            session.getTransaction().commit();
		            System.out.println("user deleted");
		         }
				
				response.sendRedirect("index.jsp");
				return;
			}
			
			
		}
		
		//session.getTransaction().commit();
		String email = request.getParameter("email");
		String pass = request.getParameter("password");
		User userQuery = (User) session.createQuery("FROM User U WHERE U.email like :email").setParameter("email", email).uniqueResult();
		
		if(userQuery != null && pass.equals(userQuery.getPassword())) {
			
	        request.getSession().setAttribute("userLogged", userQuery);
	        response.sendRedirect("index.jsp");
	        //getServletContext().getRequestDispatcher("/champ.jsp").forward(request, response);
			//response.getWriter().append("BIENVENIDO " + userQuery.getUsername());
			session.close();
			return;
		}
//		for (User user : queryTest) {
//			
//			if(request.getParameter("email").equals(user.getEmail()) && request.getParameter("password").equals(user.getPassword())){
//				response.getWriter().append("BIENVENIDO " + user.getUsername());
//				session.close();
//				return;
//			}
//			
//		}
		 response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>");
	        out.println("alert('incorrect email or password');");
	        out.println("window.location.replace('login.html');");
	        out.println("</script>");
		
	        
	        
		session.close();
		
		//response.sendRedirect("login.html");
		

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		if(request.getParameter("action") != null) {
		
			if(request.getParameter("action").equals("updateUser")) {
				User userLogged = (User) request.getSession().getAttribute("userLogged");
				request.getSession().removeAttribute("userLogged");
		         if(userLogged!=null){
		        	 if(request.getParameter("username").length() > 0) {
		        		 userLogged.setUsername(request.getParameter("username"));
		        	 }
		        	 
		        	 if(request.getParameter("email").length() > 0) {
		        		 userLogged.setEmail(request.getParameter("email"));
		        	 }
		        	 if(request.getParameter("password").length() > 0) {
		        		 userLogged.setPassword(request.getParameter("password"));
		        	 }
		        	
		            session.saveOrUpdate(userLogged);
		            session.getTransaction().commit();
		            userLogged = session.get(User.class, userLogged.getID());
		            System.out.println("user updated");
		         }
		        request.getSession().setAttribute("userLogged", userLogged);
				response.sendRedirect("profile.jsp");
				return;
			}
		}
		
		User user = new User(null, request.getParameter("username"),  request.getParameter("email"),  request.getParameter("password"),null);
		
		session.save(user);
		session.getTransaction().commit();
		session.close();
		response.sendRedirect("login.html");
		
		
	}
	

}
