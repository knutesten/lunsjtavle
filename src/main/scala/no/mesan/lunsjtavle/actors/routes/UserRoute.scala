package no.mesan.lunsjtavle.actors.routes

import akka.actor.{Actor, Props}
import no.mesan.lunsjtavle.db.UserDao
import no.mesan.lunsjtavle.model.User
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

/**
 * @author Knut Esten Melandsø Nekså
 */


object UserRoute {
  def props = Props(new UserRoute)
}

class UserRoute extends Actor with UserRouteTrait {
  def actorRefFactory = context

  def receive = runRoute(user)
}

trait UserRouteTrait extends HttpService with SprayJsonSupport {

  import no.mesan.lunsjtavle.model.UserJsonProtocol._

  val user = {
    get {
      pathEnd {
        complete(UserDao.all)
      } ~
      path(IntNumber) { id =>
        complete(UserDao.findById(id))
      }
    } ~
    post {
      path(Segment) { name =>
        complete(UserDao.insert(User(name)))
      }
    } ~
    put {
      path(IntNumber / Segment) { (id, newName) =>
        UserDao.update(User(newName, Option(id)))
        complete(StatusCodes.OK)
      }
    } ~
    delete {
      path(IntNumber) { id =>
        UserDao.delete(id)
        complete(StatusCodes.OK)
      }
    }
  }
}
