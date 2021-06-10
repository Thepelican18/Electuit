
<%@page import="com.daniel.appweb.electuit_project.entity.User"%>
<%@page import="com.daniel.appweb.electuit_project.entity.Circuit"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Base64"%>

<!DOCTYPE html>
<head>
<meta charset="ISO-8859-1">

<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/styles.css">
<link rel="stylesheet" href="styles/navbar.css">


<title>Electuit</title>
</head>
<body class="scrollable">
    <header >
        <nav class="container-fluid header_background border-gray d-flex">
            <div class="col-5 d-flex flex-wrap align-items-center ">
                <a href="index.jsp"><img class="img-thumbnail img-fluid w-15" src="img/logo.png"></a>
            </div>
            <div class="col-1 d-flex flex-wrap  align-items-center  justify-content-end border-right m-2 ">
                <a href="profile.jsp" class=" otherpage"><h6>Profile</h6></a>
            </div>
            <div class="col-1 d-flex flex-wrap align-items-center  p-1">
                <a href="circuits.jsp" class="actualpage"><h6> Circuits</h6></a>
            </div>
             <% User userLogged = (User) request.getSession().getAttribute("userLogged");
         
			if(userLogged != null) {  %>
            <div class="col-5 d-flex flex-row-reverse align-items-center text-right container-fluid">   
                <div class="dropdown col-1">
                    <img  onclick="showMenu()" class="dropbtn img-fluid w-60 " src="img/icono_perfil.png">
                    <div id="myDropdown" class="dropdown-content ">  
                        <a href="#">Profile</a>
                        <a href="modifyUser?action=logout" >Log out</a>
                    </div>
                </div>
                 <div class= "col-2">
         	 	<h5><%=userLogged.getUsername()%></h5>
	         	</div>
            </div>
            <%} else { %>
            <div class="col-5 d-flex flex-row-reverse align-items-center text-right container-fluid">
            <div class="dropdown col-1">
					  <img  onclick="showMenu()" class="dropbtn img-fluid w-60 " src="img/icono_perfil.png">
					  <div id="myDropdown" class="dropdown-content ">  
					    <a href="login.html" >Log in</a>
					  </div>
				  </div>
            
            <%} %>
        </nav>
    </header>
<section class="header_background  align-items-center justify-content-center w-70 center mt-5 p-4 rounded">
<div class="col-12">
    <h1 class="text-center ">Circuits</h1>
</div><hr class="white-bar">
<% if(userLogged != null) {  

	List<Circuit> circuitsList = (List<Circuit>) request.getSession().getAttribute("circuitsList");
if(circuitsList != null && !circuitsList.isEmpty()) {	
	for(Circuit circuit: circuitsList){	%>
<div class="card bg-dark ">
    <div class="card-header d-flex justify-content-end">
        <div  class="col-5 m-1">
            <a href="saveCircuit?action=delete_circuit&id=<%=circuit.getId().toString()%>">  
                <img src="img/delete_button_circuits.png" class="ml-3">
            </a>
            <a href="saveCircuit?action=edit_circuit&id=<%=circuit.getId().toString()%>">
                <img src="img/edit_button_circuits.png" class="ml-3">
            </a>
           
        </div>
        <h5 class="col-5 m-1">
           <%=circuit.getTitle()%>
        </h5>
      <span class="badge badge-secondary col-2 m-2 " ><%=circuit.getCreationDate()%></span>
    </div>
    <div class="card-body center text-center">
<%
 String imgSrc = Base64.getEncoder().encodeToString(circuit.getCircuitPreview());
%>
        <img src="data:image/png;base64,<%=imgSrc%>" class="card-title img-thumbnail w-50">
    </div>
  </div><br>
  <%} %>
   <div class="modal fade " id="modal_save_circuit" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                  <div class="modal-content bg-dark">
                    <div class="modal-header">
                      <h5 class="modal-title" id="exampleModalLongTitle">Save circuit</h5>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
        
                    <div class="modal-body center ">
            
                       <h4>The circuit will be delete, are you sure?</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <a href="login.html"><button type="button" class="btn btn-danger" >Confirm</button></a>
                    </div>
                  </div>
                </div>
              </div>
  <%} else{%> 
  
  <div class="text-center">

	<h4>You have not save circuits</h4>
</div>
  <%} %> 
<%} else { %>
<div class="text-center">

<h4>You need login to see your circuits.</h4>
</div>

   <%} %> 
</section>
<script src="javascript/jquery-ui/external/jquery/jquery.js"></script>
<script src="javascript/jquery-ui/jquery-ui.min.js"></script>
<script src="javascript/navbar.js"></script>
<script type="text/javascript">
$.ajax({
    url:"saveCircuit",
    type:'GET',
    success:function(result) {

    
    },
    error: function (error) { console.log(error) }
})

</script>

</body>
</html>