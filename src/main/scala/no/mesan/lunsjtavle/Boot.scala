package no.mesan.lunsjtavle

import java.sql.Timestamp

import akka.actor.ActorSystem
import akka.io.IO
import no.mesan.lunsjtavle.actors.routes.{ReportRoute, RegisterRoute, ApiRoute, UserRoute}
import no.mesan.lunsjtavle.db.{RegistrationDao, UserDao, H2}
import no.mesan.lunsjtavle.model.{Registration, User, Users}
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
  RegistrationDao.insert(Registration(1, new Timestamp(0)))
  RegistrationDao.insert(Registration(1, new Timestamp(1)))
  RegistrationDao.insert(Registration(2, new Timestamp(2)))
  RegistrationDao.insert(Registration(2, new Timestamp(3)))
  RegistrationDao.insert(Registration(3, new Timestamp(4)))


  implicit val system = ActorSystem("lunsjtavle-actor-system")

  val reportRoute = system.actorOf(ReportRoute.props, "report-route")
  val registerRoute = system.actorOf(RegisterRoute.props, "register-route")
  val userRoute = system.actorOf(UserRoute.props, "user-route")
  val apiRoute = system.actorOf(ApiRoute.props(userRoute, registerRoute, reportRoute), "api-route")

  IO(Http) ! Http.Bind(apiRoute, interface = "localhost", port = 8080)
}
