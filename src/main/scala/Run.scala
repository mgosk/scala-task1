

object Run {

  val db = new DataBase
  val authService = new AuthService(db)
  val storeService = new StoreService(db)

  def main(args: Array[String]) = {
    println("Write a command")
    io.Source.stdin.getLines.takeWhile(ln => ln != "exit").foreach { ln =>
      println("? " + ln)
      ln.split(" ") match {
        case Array("login") => println("Please provide username")
        case Array("login", username: String) => authService.login(username)
        case Array("logout") => println("Please provide access token")
        case Array("logout", token: String) => authService.logout(token)
        case Array("items") => println("Please provide access token")
        case Array("items", token: String) => authService.authenticate(token, ItemsList)(storeService.itemsList)
        case Array("buy") => println("Please provide rest of params")
        case Array("buy", token: String, itemName: String, quantity: String) => authService.authenticate(token, Buy)(storeService.buy(itemName, quantity.toInt))
        case Array("add") => println("Please provide rest of params")
        case Array("add", token: String, itemName: String, quantity: String) => authService.authenticate(token, Add)(storeService.add(itemName, quantity.toInt))
        case _ => println(helpText)
      }
    }
  }

  val helpText = "Help. Option" +
    "\n\texit to exit" +
    "\n\tlogin <username> to login" +
    "\n\tlogout <token> to logout" +
    "\n\titems <token> to get items list" +
    "\n\tbuy <token> <item-name> <quantity> to buy items"
    "\n\tadd <token> <item-name> <quantity> to add items"

}

sealed abstract class Command(val name: String)

case object Undefined extends Command("default")
case object Login extends Command("login")
case object Items extends Command("items")