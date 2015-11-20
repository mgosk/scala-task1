

object Run {

  val db = new DataBase
  val authService = new AuthService(db)
  val storeService = new StoreService(db)

  val parser = new scopt.OptionParser[Config]("scopt") {
    head("Simple CLI store")
    help("help") text ("prints this usage text")

    cmd("login") action { (_, c) => c.copy(command = Login)
    } text ("Log user to system") children (
      arg[String]("<username>") action { (x, c) =>
        c.copy(username = Some(x))
      })
    cmd("items") action { (_, c) => c.copy(command = Items)
    } text ("Show items list") children (
      opt[String]("token") abbr ("t") action { (t, c) =>
        c.copy(token = Some(t))
      }, checkConfig { c =>
        if ((c.command != Login && c.command != Undefined) && c.token.isEmpty) failure("Please provide access token") else success
      })
  }

  def main(args: Array[String]) {
    parser.parse(args, Config()) match {
      case Some(config) =>
        config.command match {
          case Undefined => println("Please provide any command")
          case Login => authService.login(config.username.get)
          case Items => authService.authenticate(config.token.get, Customer)(storeService.itemsList)
        }
      case None => println("Please provide any command")
    }
  }
}

sealed abstract class Command(val name: String)

case object Undefined extends Command("default")
case object Login extends Command("login")
case object Items extends Command("items")