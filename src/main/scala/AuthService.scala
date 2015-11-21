import java.security.SecureRandom

class AuthService(db: DataBase) {

  def login(username: String) = {
    db.users.find(_.name == username) match {
      case Some(user) =>
        val token = Token.generate
        db.sessions.put(token, user)
        println(s"User logged successfully.\nYour access token is: $token")
      case None => println("Invalid username. Login failed")
    }
  }

  def logout(token: String) = {
    db.sessions.get(token) match {
      case Some(user) =>
        db.sessions.remove(token)
        println(s"User logged out")
      case None => println("Invalid token")
    }
  }

  //provided function allow only to println to console
  def authenticate[A](token: String, priv: Priv)(func: => A): Unit = {
    db.sessions.get(token) match {
      case Some(user) if user.roles.map { role => db.privs.getOrElse(role, throw new RuntimeException("Invalid role")).contains(priv) }.exists(b => b) => func
      case Some(user) => println(s"Access denied. Required priv: ${priv.name}")
      case None => println("Invalid token")
    }
  }

}

object Token {

  val TOKEN_LENGTH = 4
  val TOKEN_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz"
  val secureRandom = new SecureRandom()

  def generate = generateToken(TOKEN_LENGTH)

  private def generateToken(tokenLength: Int): String =
    if (tokenLength == 0) "" else TOKEN_CHARS(secureRandom.nextInt(TOKEN_CHARS.length())) +
      generateToken(tokenLength - 1)

}