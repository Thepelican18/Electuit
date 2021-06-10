
  class Generator extends Component {
    constructor(typeID,dimension) {
      super(typeID,dimension)

      this.image.src = 'img/generator.png'
     
      }
     
    refreshComponent(){
      this.componentSetting = {

        width: 80,
        height: 90,
        startWireDimensionLeft: {
          x:this.dimension.x + 26,
          y:this.dimension.y + 4 

        },
        startWireDimensionRight: {
          x:this.dimension.x + 48,
          y:this.dimension.y + 4 

        }
      }
      if(!this.burned){

        if(this.connections.length == 0){
          this.image.src = 'img/generator.png'
        }else if(this.connections.length == 1){
          
          this.image.src = 'img/'+ this.typeID + '_wired.png'
        }else if(this.connections.length == 2){
          this.image.src = 'img/'+ this.typeID + '_wired_twice.png'
        }
      }
    }
    setOn(){
      this.image.src = 'img/generator_burned.png'
      this.burned = true
    }

  }