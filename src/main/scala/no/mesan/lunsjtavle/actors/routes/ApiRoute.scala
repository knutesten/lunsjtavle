package no.mesan.lunsjtavle.actors.routes

import akka.actor.{Actor, ActorRef, Props}
import spray.routing.HttpService

/**
 * @author Knut Esten Melandsø Nekså
 */

object ApiRoute {
  def props(userRoute: ActorRef, registerRoute: ActorRef) = Props(new ApiRoute(userRoute, registerRoute))
}

class ApiRoute(userRoute: ActorRef, registerRoute: ActorRef) extends Actor with HttpService {
  def actorRefFactory = context

  def receive = runRoute {
    pathPrefix("api") {
      pathPrefix("user") { cxt => userRoute ! cxt } ~
      pathPrefix("register") { cxt => registerRoute ! cxt }
    }
  }
}
