package no.mesan.lunsjtavle

import akka.actor.ActorSystem
import akka.io.IO
import no.mesan.lunsjtavle.actors.routes.{RegisterRoute, ApiRoute, UserRoute}
import no.mesan.lunsjtavle.db.{RegistrationDao, UserDao, H2}
import no.mesan.lunsjtavle.model.{User, Users}
import spray.can.Http

import scala.slick.driver.H2Driver.simple._


/**
 * @author Knut Esten Melandsø Nekså
 */
object Boot extends App {
  UserDao.create()
  UserDao.insert(User("Knut"))
  UserDao.insert(User("Anders"))
  UserDao.insert(User("Simen"))
  UserDao.insert(User("Mikkel"))
  RegistrationDao.create()

  implicit val system = ActorSystem("lunsjtavle-actor-system")

  val registerRoute = system.actorOf(RegisterRoute.props, "register-route")
  val userRoute = system.actorOf(UserRoute.props, "user-route")
  val apiRoute = system.actorOf(ApiRoute.props(userRoute, registerRoute), "api-route")

  IO(Http) ! Http.Bind(apiRoute, interface = "localhost", port = 8080)
}
