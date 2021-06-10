<<<<<<< HEAD
=======
//IMAGE SIZE
const imgWidthDefault = 80;
const imgHeightDefault = 90;
class Component {
    constructor(name, description,dimension,ctx) {
      this.name = name;
      this.description = description;
      this.dimension = dimension;
      this.ctx = ctx;
>>>>>>> dd68a154c3aea7c9e9b77fd7c4342f9318cac1f4

class Component {

  static IDMax = 0
  
  constructor (typeID, dimension) {
    this.id = Component.IDMax
    this.typeID = typeID
    this.dimension = dimension
    this.status = 'disconnected'
    this.connections = []
    this.burned = false
    console.log("IDDDD->"+ this.typeID)
    this.image = new Image()
    Component.IDMax++
  }
  setId(newId){
    this.id = newId
   }
}
