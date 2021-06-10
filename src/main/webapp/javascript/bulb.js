
  class Bulb extends Component {
    constructor(typeID,dimension) {
      super(typeID,dimension)

      this.image.src = 'img/bulb_off.png'
    }
    refreshComponent(){
      this.componentSetting = {

        width: 50,
        height: 60,
        startWireDimensionLeft: {
          x:this.dimension.x + 44,
          y:this.dimension.y + 54

        },
        startWireDimensionRight: {
          x:this.dimension.x + 6 ,
          y:this.dimension.y + 54

        }
      }

    }
    setOn(){

      this.image.src = 'img/bulb_on.png'
    }
    setOff(){

      this.image.src = 'img/bulb_off.png'
    }

  }