package no.mesan.lunsjtavle.actors.routes

import akka.actor.{Props, Actor}
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

trait RegisterRouteTrait extends HttpService {
  val route = {
    complete("test")
  }
}
