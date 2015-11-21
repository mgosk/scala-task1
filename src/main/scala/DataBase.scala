import com.pellucid.sealerate

import scala.collection.parallel.mutable

sealed abstract class Item(val name: String)

case object Item1 extends Item("Item1")

object Item {

  def apply(name: String): Item = {
    values.find(_.name == name).getOrElse(throw new RuntimeException("Element not found"))
  }

  def values: Set[Item] = sealerate.values[Item]
}

sealed abstract class Role(val name: String)
case object Customer extends Role("customer")
case object Admin extends Role("admin")

sealed abstract class Priv(val name: String)
case object Buy extends Priv("buy")
case object ItemsList extends Priv("list")
case object Add extends Priv("add")

case class User(name: String, roles: Seq[Role])

// very simple key value store
class DataBase {

  //product -> quantity
  val products = mutable.ParHashMap.empty[Item, Int]
  //username -> roles
  val users = mutable.ParSet.empty[User]
  //token -> username
  val sessions = mutable.ParHashMap.empty[String, User]
  //role -> seq[Priv]
  val privs = mutable.ParHashMap.empty[Role, Seq[Priv]]

  //db initialization here
  users += User("admin", Seq(Admin))
  users += User("john", Seq(Customer))
  users += User("bravo", Seq(Admin, Customer))
  products.put(Item1, 5)
  privs.put(Customer, Seq(ItemsList, Buy))
  privs.put(Admin, Seq(ItemsList, Add))

}
