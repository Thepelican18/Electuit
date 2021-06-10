const workspace = {
<<<<<<< HEAD
  name: 'Electuit',
  version: '1.0',
  canvas: undefined,
  ctx: undefined,
  canvasOffset: undefined,
  offset: undefined,
  offsetX: undefined,
  offsetY: undefined,
  wireColor: '#c88a65',
  rect: undefined,
  mouseX: undefined,
  mouseY: undefined,
  circuit: undefined,
  componentSelected: undefined,

  canvasSettings: {
    w: 800,
    h: 700,
  },

  init (id) {
    this.canvas = document.getElementById(id)

    this.canvas.setAttribute('width', this.canvasSettings.w)
    this.canvas.setAttribute('height', this.canvasSettings.h)
    this.ctx = this.canvas.getContext('2d')
    this.offset = 0
    this.offsetX = this.canvas.offsetLeft
    this.offsetY = this.canvas.offsetTop
    this.rect = this.canvas.getBoundingClientRect()
    this.ctx.strokeStyle = this.wireColor
   

    this.circuit = {
      title: undefined,
      components: [],
      image: undefined,
      date: undefined

    }
    this.setMouseListeners();
    this.setToolBarListeners()
    this.setCanvasButtonsListeners();
    this.setCanvasListeners()
    setInterval(function(){workspace.refresh()}, 200)
  },
  getComponentByID(id){
  
    var componentConnected = undefined
    this.circuit.components.forEach(component =>{
      console.log("Com parando id"+id +"con id " + component.id +"del componente " + component.typeID)
 
      if(component.id == id){
        componentConnected = component
        return;
      }
      
    })
    return componentConnected
  },
  refreshButtons(){
    if(workspace.circuit.components.length > 0){
      $("#save_button").attr("src","img/save_button_active.png")
      $("#cleanall_button").attr("src","img/cleanall_button_active.png")
    
    }else{
      $("#cleanall_button").attr("src","img/cleanall_button_inactive.png")
      $("#save_button").attr("src","img/save_button_inactive.png")
    }

    if(this.componentSelected != undefined){
     
      $("#delete_button").attr("src","img/delete_button_active.png")
    }else{
      $("#delete_button").attr("src","img/delete_button_inactive.png")
    }

  },

  refreshComponentConnections (component) {
    if (component.connections.length > 0) {
        
      this.ctx.beginPath()
      component.connections.forEach(connection => {
        if(connection.connectionOrientation == "left"){

            this.ctx.moveTo(
              component.componentSetting.startWireDimensionLeft.x,
              component.componentSetting.startWireDimensionLeft.y
            )
        } else {
            this.ctx.moveTo(
                component.componentSetting.startWireDimensionRight.x,
                component.componentSetting.startWireDimensionRight.y
              )

        }
       
        if(connection.connectionOrientation == "left"){
            var connectedComponent = this.getComponentByID(connection.connectedComponentID)
            console.log("Intentando obtener el objeto-> "+connectedComponent +"Con el id"+connection.connectedComponentID)
            console.log("componente conectado >"+connectedComponent +"Con el id"+connectedComponent)
            if(connectedComponent!= undefined){
        this.ctx.lineTo(
          
                (connectedComponent.componentSetting.startWireDimensionRight.x +               
                  component.componentSetting.startWireDimensionLeft.x) / 2,
                (connectedComponent.componentSetting.startWireDimensionRight.y + 
                 component.componentSetting.startWireDimensionLeft.y) / 2  
                
      )
    }
    } else {
      var connectedComponent = this.getComponentByID(connection.connectedComponentID)
     // console.log("Intentando obtener el objeto->"+connectedComponent +"Con el id"+connection.connectedComponentID)
      if(connectedComponent!= undefined){
        this.ctx.lineTo(

            (connectedComponent.componentSetting.startWireDimensionLeft.x +               
              component.componentSetting.startWireDimensionRight.x) / 2,
            (connectedComponent.componentSetting.startWireDimensionLeft.y + 
             component.componentSetting.startWireDimensionRight.y) / 2  
            
         )
            }
    }
    this.ctx.stroke()
      })
    }
      if (component.status == 'trying_to_wire') {       
      workspace.ctx.beginPath()
      workspace.ctx.moveTo(
        component.componentSetting.startWireDimensionLeft.x,
        component.componentSetting.startWireDimensionLeft.y
      )
      workspace.ctx.lineTo(workspace.mouseX, workspace.mouseY)
      workspace.ctx.stroke()
    }

  },

  refresh () {
   
    if (this.circuit.components.length > 0) {
      this.ctx.clearRect(0, 0, this.canvasSettings.w, this.canvasSettings.w)
      
      this.circuit.components.forEach(component => {
        this.refreshButtons()
        component.refreshComponent()
        this.refreshComponentConnections(component);
        this.ctx.drawImage(
          component.image,
          component.dimension.x,
          component.dimension.y,
          component.componentSetting.width,
          component.componentSetting.height
        )
        if(component == this.componentSelected){
   
                workspace.ctx.setLineDash([4, 2]);
                workspace.ctx.lineDashOffset = -this.offset;
                workspace.ctx.strokeRect(component.dimension.x-10, component.dimension.y-10, component.componentSetting.width+20, component.componentSetting.height+20);

            }
            this.ctx.setLineDash([0, 0]);
        })
    }
  },

  getComponentBuildByTypeIDAndDimension (typeID,dimension) {
    switch (typeID) {
      case 'generator':
        return new Generator(typeID, {
          x:dimension.x ,
          y:dimension.y
        })
        break
      case 'bulb':
        return new Bulb(typeID, { 
          x:dimension.x ,
          y:dimension.y
        })
        break
      case 'wire':
        return undefined
        break
      default:
        alert('No hay ajustes para este componente')
        return undefined
    }
  },
 
  setMouseListeners(){
   //MOUSE CORDS
   $(document).ready(function () {
    $('*').mousemove(function (event) {
      workspace.mouseX = event.pageX - workspace.rect.left
      workspace.mouseY = event.pageY - workspace.rect.top
    })

  })
  },
  componentColision (component) {
    return (
      component.dimension.x - 50 < workspace.mouseX &&
      component.componentSetting.width + component.dimension.x + 50 >
        workspace.mouseX &&
      component.dimension.y - 50 < workspace.mouseY &&
        component.componentSetting.height + component.dimension.y + 50 >
          workspace.mouseY
    )
  },
  setToolBarListeners () {

 

    $(document).ready(function () {
      $(function () {
        $('#tool-bench').draggable()
        $('.component_container').draggable()
       
        $('.component_container').data({
            
          originalLeft: $('.component_container').css('left'),
          origionalTop: $('.component_container').css('top')
          
        })
      
      })

      $('.component_container').mouseup(function (event) {
        $('.component_container').css({
          left: $('.component_container').data('originalLeft'),
          top: $('.component_container').data('origionalTop')
        })
       
        if((workspace.mouseX > 0 && workspace.mouseX < workspace.canvas.width) && (workspace.mouseY > 0 && workspace.mouseY < workspace.canvas.height)){
          
          var img = new Image()
  
          img.src = $(this).attr('src')
  
          var componentType = $(this).attr('id')
          var posOcuped = false
          var newComponent = workspace.getComponentBuildByTypeIDAndDimension(componentType,{x: workspace.mouseX, y: workspace.mouseY})
  
          if (workspace.circuit.components.length != 0) {
            workspace.circuit.components.forEach(component => {
              if (workspace.componentColision(component)) {
                if (componentType == 'wire') {
                  
                  component.status = 'trying_to_wire'
                 
                  workspace.componentSelected = undefined
                }
                posOcuped = true
              }
            })
  
            if (!posOcuped && componentType != 'wire') {
              workspace.circuit.components.push(newComponent)
             
              workspace.componentSelected = undefined
            }
          } else {
            if (componentType != 'wire') {
              workspace.circuit.components.push(newComponent)
             
             
              workspace.componentSelected = undefined
            }
          } 
          workspace.refresh()
        }

      })
    })
  },
  setCanvasButtonsListeners(){


        $( "#play_button_link" ).click(function() {
        
           workspace.circuit.components.forEach(component =>{

            if(component.typeID == "generator"){

                component.connections.forEach(connection =>{
                  var connectedComponent1 =  workspace.getComponentByID(connection.connectedComponentID)

                    if(connection.connectionOrientation == "left"){
                       
                        function AvisoDeEpilepsia(connectedComponent){
                            connectedComponent.connections.forEach(connectedComponentConnection =>{
                              var connectedComponent2 = workspace.getComponentByID(connectedComponentConnection.connectedComponentID)
                                console.log(connectedComponent2.typeID +" -> " + connectedComponentConnection.connectionOrientation )
                                if(connectedComponentConnection.connectionOrientation == "left"){
                                    if(connectedComponent2.typeID == "generator"){

                                        if(connectedComponent1.typeID == "generator"){
                                           connectedComponent1.setOn()
                                            alert("La pila ha petado")
                                        }else{
                                            
                                            function setOnAll(component) {
        
                                                if(component.typeID != "generator"){
                                                    component.setOn()
        
                                                    
                                                }
                                                component.connections.forEach(connection =>{
                                                  var connectedComponent3 = workspace.getComponentByID(connection.connectedComponentID)
                                                    if(connection.connectionOrientation == "left" && connectedComponent3.typeID != "generator"){
                                                    
                                                    setOnAll(connectedComponent3)
                                                    }
        
        
                                                })
        
                                            }
                                            setOnAll(component)
                                        }
                                       
                                        
                                       
                                    }else{
                                        console.log(connectedComponent.typeID +" To "+connectedComponent2.typeID  )
                                        connectedComponent = connectedComponent2  
                                        AvisoDeEpilepsia(connectedComponent)
        
                                    }

                                }
                                
                        })
                    }
                   
                    AvisoDeEpilepsia(connectedComponent1)

                        

                    }

                })


            }

           })


        });
        $( "#save_button_link" ).click(function() {
          this.componentSelected != undefined
          var dataURL = workspace.canvas.toDataURL("png");
          $("#circuit_preview").attr("src",dataURL)
          $("#circuit_preview_input").attr("value",dataURL)
        });
        $( "#delete_button_link" ).click(function() {
             //get componente para eliminarle al contrario las conexiones
            
            if(workspace.componentSelected != null){
                $(this).data('clicked', true);
              workspace.circuit.components.forEach(component => {
                if(component.connections.length > 0 && component != workspace.componentSelected){

                        component.connections.forEach(connection=>{
                          var connectedComponent = workspace.getComponentByID(connection.connectedComponentID)
                            
                            if(connectedComponent == workspace.componentSelected ){
                              
                                component.connections.splice(component.connections.indexOf(connection),1)
                            }

                        })

               
                }   
            })
            workspace.circuit.components.splice(workspace.circuit.components.indexOf(workspace.componentSelected),1)
            workspace.componentSelected = undefined
        
            }
           
           
        })
        $( "#cleanall_button_link").click(function() {
            workspace.circuit.components = []
            workspace.componentSelected = undefined
      });
        
        

  },
  setCanvasListeners () {
    var componentInMovement = undefined
    var componentPreviousX = undefined
    var componentPreviousY = undefined
    var initialMouseX = undefined
    var initialMouseY = undefined

    function handleMouseDown (e) {
      workspace.circuit.components.forEach(component => {
        if (component.status == 'trying_to_wire') {
          workspace.circuit.components.forEach(otherComponent => {
            if (
              otherComponent.dimension.x < workspace.mouseX &&
              otherComponent.componentSetting.width +
                otherComponent.dimension.x >
                workspace.mouseX &&
              otherComponent.dimension.y < workspace.mouseY &&
                otherComponent.componentSetting.height +
                  otherComponent.dimension.y >
                  workspace.mouseY
            ) {
             
              component.status = 'connected'
              component.connections.push( {connectedComponentID: otherComponent.id, connectionOrientation:"left"}) 
              otherComponent.status = 'connected'
              otherComponent.connections.push({connectedComponentID: component.id, connectionOrientation:"right"})
              workspace.refresh()
            }
          })
        } else {
          workspace.circuit.components.forEach(otherComponent => {
            if (
              otherComponent.dimension.x < workspace.mouseX &&
              otherComponent.componentSetting.width +
                otherComponent.dimension.x >
                workspace.mouseX &&
              otherComponent.dimension.y < workspace.mouseY &&
                otherComponent.componentSetting.height +
                  otherComponent.dimension.y >
                  workspace.mouseY
            ) {
                componentInMovement = otherComponent
                workspace.componentSelected = otherComponent
                
              initialMouseX = workspace.mouseX - componentInMovement.dimension.x
              initialMouseY = workspace.mouseY - componentInMovement.dimension.y
              componentPreviousX = otherComponent.dimension.x
              componentPreviousY = otherComponent.dimension.y
            }
          })
        }
      })
    }

    function handleMouseUp (e) {
    if (componentInMovement != undefined) {
      workspace.circuit.components.forEach(component => {
        if (workspace.componentColision(component) && componentInMovement != component) {
            
            componentInMovement.dimension.x = componentPreviousX
            componentInMovement.dimension.y = componentPreviousY
           
           
            workspace.refresh()
        }
      })
   
      componentInMovement = undefined
    
    }
    if( workspace.componentSelected != undefined){
     
      $("#delete_button").attr("src","img/delete_button_active.png")
    
    }
    
    if(!$("#delete_button_link").data('clicked')){
   
        var  componentFinded = false
            workspace.circuit.components.forEach(component => {
            if (workspace.componentColision(component)) {  
             
                componentFinded = true
            }
        })
        if(!componentFinded){
            
            workspace.componentSelected = undefined
          
        }          
    }else{
        $("#delete_button_link").data('clicked', false);
       
    }

    }

    function handleMouseOut (e) {
      if (componentInMovement != undefined) {
        componentInMovement.dimension.x = componentPreviousX
        componentInMovement.dimension.y = componentPreviousY
       
        componentInMovement = undefined
        workspace.refresh()
      }
    }

    function handleMouseMove (e) {
      
      if (componentInMovement != undefined) {   
        componentInMovement.dimension.x = workspace.mouseX - initialMouseX
        componentInMovement.dimension.y = workspace.mouseY - initialMouseY
       
      }

      workspace.ctx.clearRect(
        0,
        0,
        workspace.canvasSettings.w,
        workspace.canvasSettings.w
      )
      if(workspace.circuit.components.length > 0){

        workspace.refresh()
      }
    }
    $('#electuit-canvas').mousedown(function (e) { handleMouseDown(e) })
    $('#electuit-canvas').mousemove(function (e) { handleMouseMove(e) })
    $('#electuit-canvas').mouseup(function (e) { handleMouseUp(e) })
    $('#electuit-canvas').mouseout(function (e) { handleMouseOut(e) })
  },
  
  loadJson(json){
    workspace.init('electuit-canvas');
    console.log("canvas-> "+this.canvas)
    console.log(json);
    var jsonParsed = JSON.parse(json);
    this.circuit = {
      title: jsonParsed.title,
      components: [],
      image: undefined,
      date: json.date
    }
  console.log("Array de componentes->"+jsonParsed.components)
  jsonParsed.components.forEach(component=>{
   

    var newComponent = this.getComponentBuildByTypeIDAndDimension(component.typeID,component.dimension)
    console.log("newComponent-> "+newComponent.dimension.x+"->image-> "+newComponent.image.src)
    newComponent.setId(component.id)
    component.connections.forEach( connection=>{
      newComponent.connections.push({connectedComponentID:connection.connectedComponentID,connectionOrientation:connection.connectionOrientation })
      console.log("newComponent-> "+newComponent.typeID+"->con id-> "+component.id +"Conectado a " +connection.connectedComponentID)
     })
    newComponent.refreshComponent()
    this.circuit.components.push(newComponent)

  })

},


=======
    name= "Electuit",
    version= '1.0',
    canvas: undefined,
    rect: undefined,
    ctx: undefined,
    relX: undefined,
    relY: undefined,
>>>>>>> dd68a154c3aea7c9e9b77fd7c4342f9318cac1f4

}
function saveCircuitInDB() {

<<<<<<< HEAD


workspace.circuit.title = document.getElementById('circuit_title').value
workspace.circuit.image = $("#circuit_preview").attr('src')

var jsonData = JSON.stringify(workspace.circuit)
console.log("v2  "+jsonData)
=======
        this.canvas = $(id)
        this.canvas.setAttribute('width', this.canvasSettings.w)
        this.canvas.setAttribute('height', this.canvasSettings.h)
        this.ctx = this.canvas.getContext('2d')
        this.rect = canvas.getBoundingClientRect(); 

        //this.bgPlanet = new BgPlanet(this.ctx, 0, 0, this.canvasSettings.w, this.canvasSettings.h, 3)
        
        this.setListeners()
>>>>>>> dd68a154c3aea7c9e9b77fd7c4342f9318cac1f4

    
    $.ajax({
        url:"saveCircuit",
        type:'POST',
        datatype: 'json',
        data:jsonData,
        contentType: 'application/json',
        mimeType: 'application/json',
        success:function(result) { 
          console.log("safdasfdsafsafa")
          $("#modal_save_circuit").modal('hide');
          setInterval(function(){refreshPage()}, 5000);

<<<<<<< HEAD
        },
        error: function (error) { console.log(error) }
    })
    console.log("Fuera deka ajax")
    $("#modal_save_circuit").modal('hide');
}
function refreshPage() {
  location.reload(true);
=======
    },
    start(){
        $(document).ready(function(){
      
            $("*").mousemove(function(event){
               this.relX = (event.pageX - this.rect.left);
               this.relY = (event.pageY - this.rect.top);
          
            });
            
          });
          $(document).ready(function(){
            $(function() {
              $( "#tool-bench" ).draggable();
              $( ".component_container").draggable();
           
        
              $(".component_container").data({
                'originalLeft': $(".component_container").css('left'),
                'origionalTop': $(".component_container").css('top')
            }); 
          });
        
          $(".component_container").mouseup(function(event) {
        
            $(".component_container").css({
              'left': $(".component_container").data('originalLeft'),
              'top': $(".component_container").data('origionalTop'),
               type: $(".component_container").id
            });
            var img = new Image;
        
            img.src = "img/generator.png";
            
            img.onload = function() {  
            ctx.drawImage(this, relX, relY,imgWidthDefault,imgHeightDefault);
            };
            });
          });
        //TEST
          $("#electuit-canvas").mouseup(function(event) {
            ctx.strokeRect(relX-imgWidthDefault, relY-imgHeightDefault, imgWidthDefault+10, imgHeightDefault+10);
          });
}
>>>>>>> dd68a154c3aea7c9e9b77fd7c4342f9318cac1f4
}
