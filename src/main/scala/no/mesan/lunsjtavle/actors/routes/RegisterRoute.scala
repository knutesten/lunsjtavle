package no.mesan.lunsjtavle.actors.routes

import java.sql.Timestamp

import akka.actor.{Props, Actor}
import no.mesan.lunsjtavle.db.RegistrationDao
import no.mesan.lunsjtavle.model.Registration
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

/**
 * @author Knut Esten Melandsø Nekså
 */

object RegisterRoute {
  def props = Props(new RegisterRoute)
}

class RegisterRoute extends Actor with RegisterRouteTrait {
  val actorRefFactory = context
  val receive = runRoute(route)
}

trait RegisterRouteTrait extends HttpService with SprayJsonSupport {
  import no.mesan.lunsjtavle.model.RegistrationJsonProtocol._

  val route = {
    get {
      pathEnd {
        complete(RegistrationDao.all)
      }
    } ~
    post {
      path(IntNumber / "date" / IntNumber) { (userId, timestamp) =>
        complete(RegistrationDao.insert(Registration(userId, new Timestamp(timestamp))))
      }
    } ~
    delete {
      path(IntNumber) { registrationId =>
        RegistrationDao.delete(registrationId)
        complete(StatusCodes.OK)
      }
    }
  }
}
