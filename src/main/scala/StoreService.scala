class StoreService(db: DataBase) {

  def itemsList() = {
    println(s"Items list:")
    db.products.map {
      case (item, quantity) =>
        println(s"\t$item : $quantity pcs")
    }
  }

  def buy(itemName: String, quantity: Int) = {
    db.products.find(_._1.name == itemName) match {
      case Some((item, quantityAvail)) if quantityAvail >= quantity =>
        db.products.update(item, quantityAvail - quantity)
        println(s"$quantity psc of $itemName buyed")
      case Some((item, quantityAvail)) => println(s"Not enough items. Required:$quantity Available:$quantityAvail")
      case None => println(s"Invalid item name")
    }
  }

  def add(itemName: String, quantity: Int) = {
    db.products.find(_._1.name == itemName) match {
      case Some((item, quantityAvail)) =>
        db.products.update(item, quantityAvail + quantity)
        println(s"$quantity psc of $itemName added")
      case None => println(s"Invalid item name")
    }
  }

}
