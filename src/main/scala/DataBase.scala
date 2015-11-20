import scala.collection.parallel.mutable

sealed trait Item

case object Item1 extends Item

sealed abstract class Role(val name:String)

case object Customer extends Role("customer")
case object Admin extends Role("admin")

case class User(name:String,roles:Seq[Role])

// very simple key value store
class DataBase {

  //product -> quantity
  val products = mutable.ParHashMap.empty[Item, Int]
  //username -> roles
  val users = mutable.ParSet.empty[User]
  //token -> username
  val sessions = mutable.ParHashMap.empty[String, User]

  //db initialization here
  users += User("admin", Seq(Admin))
  users += User("john", Seq(Admin))
  products.put(Item1,5)

}
