class StoreService(db: DataBase) {

  def itemsList() = {
    db.products.map {
      case (item, quantity) =>
      println (s"$item : $quantity pcs")
    }
  }

}
