package no.mesan.lunsjtavle.model

import java.util.Date

import scala.slick.driver.H2Driver.simple._

/**
 * @author Knut Esten Melandsø Nekså
 */


case class Registration(userId: Int, date: Date, id: Option[Int] = None)

class Registrations(tag: Tag) extends Table[Registration](tag, "registrations") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def userId = column[Int]("user_id", O.NotNull)

  def date = column[Date]("date", O.NotNull)

  def * = (userId, date, id.?) <>(Registration.tupled, Registration.unapply)

  def user = foreignKey("user_id", userId, TableQuery[Users])(_.id)
}
