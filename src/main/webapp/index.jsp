


<%@page import="com.daniel.appweb.electuit_project.entity.User"%>
<%@page import="com.daniel.appweb.electuit_project.entity.Circuit"%>
<html>
    <head>
    <meta charset="ISO-8859-1">
    
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="javascript/jquery-ui/jquery-ui.min.css">
    <link rel="stylesheet" href="styles/styles.css">
    <link rel="stylesheet" href="styles/navbar.css">
   
    
    
    
    <title>Electuit</title>
    </head>
    <body class="body_background border-gray">
        <header >
            <nav class="container-fluid header_background border-gray d-flex">
                <% User userLogged = (User) request.getSession().getAttribute("userLogged");
                
               

               
               
                 if(userLogged == null) {  %>
                
       		 <div class="col-6 d-flex flex-wrap align-items-center ">
                <a href="index.jsp"><img class="img-thumbnail img-fluid w-15" src="img/logo.png"></a>
            </div>
         
            <div class="col-6 d-flex flex-row-reverse align-items-center text-right container-fluid">
            <div class="dropdown col-1">
					  <img  onclick="showMenu()" class="dropbtn img-fluid w-50 " src="img/icono_perfil.png">
					  <div id="myDropdown" class="dropdown-content ">  
					    <a href="login.html" >Log in</a>
					  </div>
				  </div>
          
         	</div>
        	 
         <%}else {%>
            
            	<div class="col-5 d-flex flex-wrap align-items-center ">
                    <a href="index.jsp"><img class="img-thumbnail img-fluid w-15" src="img/logo.png"></a>
                </div>
                <div class="col-1 d-flex flex-wrap  align-items-center  justify-content-end border-right m-2 ">
                    <a href="profile.jsp" class=" otherpage"><h6>Profile</h6></a>
                </div>
                <div class="col-1 d-flex flex-wrap align-items-center  p-1">
                    <a href="circuits.jsp" class="otherpage"><h6>Circuits</h6></a>
                    </div>
	        	<div class="col-5 d-flex flex-row-reverse align-items-center text-right container-fluid">   
	                <div class="dropdown col-1">
	                    <img  onclick="showMenu()" class="dropbtn img-fluid w-60 " src="img/icono_perfil.png">
	                    <div id="myDropdown" class="dropdown-content ">  
	                        <a href="profile.jsp">Profile</a>
	                        <a href="modifyUser?action=logout" >Log out</a>
	                    </div>
	                </div>
                 
	         <div class= "col-2">
         	 	<h5><%=userLogged.getUsername()%></h5>
	         </div>
	         </div>

         <%}%>
        </nav>
        </header>
<<<<<<< HEAD
        <section class="rounded">
             <div class="container-fluid header_background border-gray d-flex " id="canvasTools">
                <div class="col-1 d m-2">
                    <a href="#" id="play_button_link"><img id="play_button" class="img-fluid" src="img/play_button_active.png"></a>
                </div>
                <div class="col-1  m-2">
                    <a href="#"  id="save_button_link" data-toggle="modal" data-target="#modal_save_circuit"><img id="save_button" class="img-fluid" src="img/save_button_inactive.png"></a>
                </div>
                <div class="col-1 m-2">
                    <a href="#" id="delete_button_link"><img id="delete_button" class="img-fluid" src="img/delete_button_inactive.png"></a>
                </div>
                <div class="col-1 m-2">
                    <a href="#" id="cleanall_button_link"><img id="cleanall_button" class="img-fluid" src="img/cleanall_button_inactive.png"></a>
                </div>
            </div>

               <canvas id="electuit-canvas" class="border-gray" ></canvas>

=======
        <section>
    
            <canvas id="electuit-canvas" class="border-gray"></canvas>
    
>>>>>>> dd68a154c3aea7c9e9b77fd7c4342f9318cac1f4
        </section>
        <section id="tool-bench" class="header_background border-gray w-20 h-70 rounded" class="window card z-depth-3 " >
            <div class="white-bar border-gray text-center align-items-center rounded">
                <h6>Components</h6> 
            </div>
            <div class="container-fluid d-flex flex-wrap">
                <div class="col-6 text-center">
                    <label>Generators</label>
                    <img class="component_container border-gray" id="generator"  src="img/generator.png">
                </div>
                 <div class="col-6 text-center">
                     <label>Wires</label>
                    <img class="component_container border-gray" id="wire"  src="img/wire.png">
                 </div>
                 <div class="col-6 text-center">
                     <label>Bulbs</label>
                     <img class="component_container border-gray" id="bulb" src="img/bulb_on.png">
                    </div>
                 <div class="col-6 text-center">
                    <label>Resistors</label>
                    <img class="component_container border-gray"  src="img/no_image.png">
                 </div>
            </div>
        </section>
        <% if(userLogged != null) {  %>

 <!-- Modal -->
 <div class="modal fade " id="modal_save_circuit" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content bg-dark">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLongTitle">Save circuit</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>  
        <form  id="form_save_circuit" action="javascript:;" onSubmit="saveCircuitInDB()">  
        <div class="modal-body center ">

           
                <div class="form-group ">
                    <input id="circuit_title" type="text" class="form-control" name="title"  placeholder="Circuit title" required>
                  </div>
                
            <input id="circuit_preview_input" name="circuit_preview" type="file" class="invisible">
            <img id="circuit_preview" class="w-75 img-thumbnail ml-12">
           
        </div>
        <div class="modal-footer">
          <input type="submit" class="btn btn-info" value="Save changes">
	      <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
        </div>
        </form>
      </div>
    </div>
    </div>
        <%}else {%>


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
            
                       <h4>You need to login to save a circuit</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
                        <a href="login.html"><button type="button" class="btn btn-info" >Log in</button></a>
                    </div>
                  </div>
                </div>
              </div>

        <%}%>

        <script src="javascript/jquery-ui/external/jquery/jquery.js"></script>
        <script src="javascript/jquery-ui/jquery-ui.min.js"></script>
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <script src="javascript/navbar.js"></script>
        <script src="javascript/component.js"></script>
        <script src="javascript/generator.js"></script>
        <script src="javascript/bulb.js"></script>
        <script src="javascript/workspace.js"></script>
        <script src="javascript/index.js"></script>
        <%String circuitActive =(String)request.getSession().getAttribute("circuitActiveInJson");
        
         if(circuitActive != null){%>
                <script type="text/javascript">
                
                
                	workspace.loadJson('<%=circuitActive%>')
                
                
                </script>
                	
                
                
        <% }else{ %>	
          <script type="text/javascript">
                
              
                workspace.init('electuit-canvas');
          
          
          </script>

          <% } %>	
    </body>
    </html>
    