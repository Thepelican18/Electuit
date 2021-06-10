<%@page import="com.daniel.appweb.electuit_project.entity.User"%>
<!DOCTYPE html>
<head>
<meta charset="ISO-8859-1">

<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/styles.css">
<link rel="stylesheet" href="styles/navbar.css">


<title>Electuit</title>
</head>
<body class="body_background">
    <header >
        <nav class="container-fluid header_background border-gray d-flex">
            <div class="col-5 d-flex flex-wrap align-items-center ">
                <a href="index.jsp"><img class="img-thumbnail img-fluid w-15" src="img/logo.png"></a>
            </div>
            <div class="col-1 d-flex flex-wrap  align-items-center  justify-content-end border-right m-2 ">
                <a href="profile.jsp" class=" actualpage"><h6>Profile</h6></a>
            </div>
            <div class="col-1 d-flex flex-wrap align-items-center  p-1">
                <a href="circuits.jsp" class="otherpage"><h6>Circuits</h6></a>
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
 
</div>
   <h1 class="text-center ">Profile</h1>
   <hr class="white-bar">
<%if(userLogged != null) {  %>
<div class="col-4 mt-5  center">
    <form action="modifyUser?action=updateUser" method="post">
        <div class="form-group ">
            <input type="text" class="form-control" name="username" id="exampleInputUsername1"  placeholder="<%=userLogged.getUsername()%>">
          </div>
        <div class="form-group ">
            <input type="email" class="form-control" name="email" id="exampleInputEmail1"  aria-describedby="emailHelp" placeholder="<%=userLogged.getEmail()%>">
          </div>
          <div class="form-group"> 
            <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
          </div>
        <div class=" form-group justify-content-center d-flex container-fluid">
        <button type="button"class="btn btn-danger btn-md  col-5 mr-2" data-toggle="modal" data-target="#modal_delete_user">Delete Account</button>
        <input type="submit" value="Save" class="btn btn-info btn-md  col-5 ml-2 " >
    </div>
    </form><br>
</div>

<div class="modal fade " id="modal_delete_user" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                  <div class="modal-content bg-dark">
                    <div class="modal-header">
                      <h5 class="modal-title" id="exampleModalLongTitle">Are you sure to delete your profile?</h5>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
        
                    <div class="modal-body center ">
            
                       <h4>You lose all save circuits permanently!</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <a href="modifyUser?action=deleteAccount"><button type="button" class="btn btn-danger" >Confirm</button></a>
                    </div>
                  </div>
                </div>
              </div>
            

<%}else { %>
<div class="text-center">

<h4>You need login to see your profile.</h4>
</div>


<%} %>
</section>
<footer>
</footer>
<script src="javascript/jquery-ui/external/jquery/jquery.js"></script>
<script src="javascript/jquery-ui/jquery-ui.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="javascript/navbar.js"></script>
</body>
</html>