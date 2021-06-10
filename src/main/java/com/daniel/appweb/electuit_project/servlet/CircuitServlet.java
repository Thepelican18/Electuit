package com.daniel.appweb.electuit_project.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.*;

import com.daniel.appweb.electuit_project.entity.Circuit;
import com.daniel.appweb.electuit_project.entity.Component;
import com.daniel.appweb.electuit_project.entity.Connection;
import com.daniel.appweb.electuit_project.entity.User;
import com.google.gson.Gson;
import com.mysql.cj.jdbc.Blob;


/**
 * Servlet implementation class CircuitServlet
 */
public class CircuitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CircuitServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
			
		
	if(request.getParameter("action") != null) {
			
			if(request.getParameter("action").equals("delete_circuit")) {
				Circuit circuit = (Circuit) session.get(Circuit.class,Integer.valueOf(request.getParameter("id").toString()));
				request.getSession().removeAttribute("circuitsList");
		         
				if(circuit!=null) {
					List<Connection> connectionsCircuitList = (List<Connection>) session.createQuery("FROM Connection C WHERE C.IdCircuit = :id_circuit").setParameter("id_circuit", circuit.getId()).list();
					connectionsCircuitList.forEach(session::delete);
					session.delete(circuit);
		            session.getTransaction().commit();
		            System.out.println("circuit deleted");
		         }
				
			} else if (request.getParameter("action").equals("edit_circuit")) {
			
			
			
			Circuit circuit = (Circuit) session.get(Circuit.class,Integer.valueOf(request.getParameter("id").toString()));
			
			
			request.getSession().removeAttribute("circuitsList");
			
			
		StringBuilder json = new StringBuilder();
		
		
		String imgSrc = Base64.getEncoder().encodeToString(circuit.getCircuitPreview());
		
		json.append("{\"title\":\""+circuit.getTitle()+"\","+
				"\"image\":\"undefined\","+
				"\"date\":\""+circuit.getCreationDate()+"\","
				);
		
		if(circuit.getComponentsSet() != null) {
			if(!circuit.getComponentsSet().isEmpty()) {
		
			json.append("\"components\":[");
			for(Component component: circuit.getComponentsSet()) {
				json.append(
								"{\"id\":"+component.getIdInCircuit()+ ","+
								"\"typeID\":\""+component.getComponentType()+"\","+
								"\"status\":\""+component.getStatus()+"\","+
								"\"burned\":\""+component.getBurned()+"\","+
								"\"dimension\":{"+
								"\"x\":"+component.getX()+","+
								"\"y\":"+component.getY()+""+
								"},"	
				);
				List<Connection> connectionsCircuitList = (List<Connection>) session.createQuery("FROM Connection C WHERE C.IdCircuit = :id_circuit and C.IdInCircuit = :id_in_circuit")
						.setParameter("id_circuit", circuit.getId())
						.setParameter("id_in_circuit", component.getIdInCircuit())
						.list();
				//List<Connection> connectionsList = getComponentConnectionsFromConnectionsWithId(connectionsCircuitList,component.getIdInCircuit());
				if(connectionsCircuitList != null) {
					
					if(!connectionsCircuitList.isEmpty()) {
					
					json.append("\"connections\":[");
					for(Connection connection: connectionsCircuitList) {
								
						json.append(
								"{\"connectedComponentID\":\""+connection.getComponentConnectedIdInCircuit()+"\","+
								"\"connectionOrientation\":\""+connection.getConnectionOrientation()+"\""+
								"},"	
							);
	
					}
					
					json.deleteCharAt(json.length()-1);
				}else {
					json.append("\"connections\":[");
				}
				
				json.append("]},");
				
			}
			
			
			}
			json.deleteCharAt(json.length()-1);
			
			} else {
			json.append("\"components\":[");
		}
			
		json.append("]}");
		}
			
			System.out.println("circuito pa subir"+json.toString());
				
			request.getSession().removeAttribute("circuitActiveInJson");
			request.getSession().removeAttribute("circuitActive");
			request.getSession().setAttribute("circuitActiveInJson", json.toString());
			request.getSession().setAttribute("circuitActive", circuit);
			response.sendRedirect("index.jsp");
			session.close();
			return;
		}
	}
		User userLogged = (User) request.getSession().getAttribute("userLogged");
		List<Circuit> circuitList = (List<Circuit>) session.createQuery("FROM Circuit C WHERE C.user = :user").setParameter("user", userLogged).list();
		
		
		if(circuitList != null) {
			
			request.getSession().setAttribute("circuitsList", circuitList);
			

		}
		response.sendRedirect("circuits.jsp");
		session.close();
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		  BufferedReader br = 
					new BufferedReader(new InputStreamReader(request.getInputStream()));
					
					String json = "";
					if(br != null){
						json = br.readLine();
					
					}

	

		JSONObject jObj = new JSONObject(json); // this parses the json
		
		User userLogged = (User) request.getSession().getAttribute("userLogged");

		 String sourceData = jObj.get("image").toString();
		 String base64Image = sourceData.split(",")[1];
		 byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
		
		 //FileUtils.writeByteArrayToFile(new File(outputFileName), decodedBytes);
		
		//Date date= Date.valueOf(jObj.get("date").toString()); 
		Set<Component> componentsSet = new HashSet<>();
		
		
		
		Circuit circuit;
		if(request.getSession().getAttribute("circuitActive") == null){
			
			 circuit = new Circuit(null, (String) jObj.get("title"),imageBytes, LocalDate.now(), userLogged, null);
			
		} else {
			 circuit = (Circuit) request.getSession().getAttribute("circuitActive");
			
			 circuit.setTitle(jObj.get("title").toString());
			 circuit.setCircuitPreview(imageBytes);
			 circuit.setCreationDate(LocalDate.now());
			 circuit.setUser(userLogged);
			 Set<Component> componentSet = circuit.getComponentsSet();
			 circuit.setComponentsSet(null);
			 if(componentSet != null) {
				 
				 componentSet.forEach(session::delete);
			 }
				
				List<Connection> connectionsCircuitListToDelete = (List<Connection>) session.createQuery("FROM Connection C WHERE C.IdCircuit = :id_circuit").setParameter("id_circuit", circuit.getId()).list();
				if(connectionsCircuitListToDelete != null) {
					
					connectionsCircuitListToDelete.forEach(session::delete);
				}
			
			 
			
		}
			
			
		
		
		JSONArray jsonArray = (JSONArray) jObj.get("components");
		
		session.saveOrUpdate(circuit);

		jsonArray.forEach(component->{
			JSONObject componentjObj = (JSONObject)component;

			Integer burned = 0;
			if(componentjObj.get("burned").toString().equals("true")) {
				burned = 1;
				
			}
			JSONObject dimension = (JSONObject)componentjObj.get("dimension");
			Component newComponent = new Component(null,
					componentjObj.get("typeID").toString(),
					componentjObj.get("status").toString(),
					Integer.valueOf(componentjObj.get("id").toString()),
					Float.valueOf(dimension.get("x").toString()),Float.valueOf(dimension.get("y").toString()),
					burned,
					circuit
					);
			
			componentsSet.add(newComponent);
		
			JSONArray jsonConnectionsArray = (JSONArray) componentjObj.get("connections");
			
			if(jsonConnectionsArray.length() > 0) {
				List<Connection> connectionsList = new ArrayList<>();
				jsonConnectionsArray.forEach(connection->{
					JSONObject connectionjObj = (JSONObject)connection;
					
					connectionsList.add(new Connection(null, newComponent.getIdInCircuit(),Integer.valueOf(connectionjObj.get("connectedComponentID").toString()),connectionjObj.get("connectionOrientation").toString(),circuit.getId()));

				});
				connectionsList.forEach(session::save);
				connectionsList.forEach(System.out::println);
			}
			
		});
		componentsSet.forEach(session::save);
		componentsSet.forEach(System.out::println);
		
		
	
		JSONArray jsonComponentsArrayToInsertConnections = (JSONArray) jObj.get("components");
		
		//session.saveOrUpdate(circuit);

		jsonComponentsArrayToInsertConnections.forEach(component->{
			JSONObject componentjObj = (JSONObject)component;
			Iterator it = componentjObj.keys();
		
		
			
			
		});
		circuit.setComponentsSet(componentsSet);
		
		System.out.println("circuit-> "+circuit);
		
	
		session.getTransaction().commit();
		request.getSession().removeAttribute("circuitActiveInJson");
		request.getSession().removeAttribute("circuitActive");
		 
		//getServletContext().getRequestDispatcher("/circuis.jsp").forward(request, response); 
		//response.sendRedirect("circuis.jsp");
		session.close();
	
	}


}
