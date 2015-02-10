package no.mesan.lunsjtavle.actors.routes

import java.sql.Timestamp

import akka.actor.{Actor, Props}
import no.mesan.lunsjtavle.db.RegistrationDao
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

/**
 * @author Knut Esten Melandsø Nekså
 */
object ReportRoute {
  def props = Props(new ReportRoute)
}

class ReportRoute extends Actor with ReportRouteTrait {
  def actorRefFactory = context

  def receive = runRoute(report)
}

trait ReportRouteTrait extends HttpService with SprayJsonSupport {
  import no.mesan.lunsjtavle.model.RegistrationJsonProtocol._

  val report = {
    path(IntNumber) { (userId) =>
      parameters('start.as[Int], 'end.as[Int]) { (start, end) =>
        complete(RegistrationDao.getRegistrations(userId, new Timestamp(start), new Timestamp(end)))
      }
    }
  }
}