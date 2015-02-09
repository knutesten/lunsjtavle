package no.mesan.lunsjtavle.db

import no.mesan.lunsjtavle.model.{Registration, Registrations}

import scala.slick.driver.H2Driver.simple._

/**
 * @author Knut Esten Melandsø Nekså
 */
object RegistrationDao {
  private val registrations = TableQuery[Registrations]
  private val byId = registrations.findBy(_.id)

  def create() = H2.database.withSession { implicit session =>
    registrations.ddl.create
  }

  def insert(registration: Registration) : Registration = H2.database.withSession { implicit session =>
    val registrationId = registrations returning registrations.map(_.id) insert registration
    byId(registrationId).list.head
  }

  def delete(id: Int) = H2.database.withSession { implicit session =>
    byId(id).delete
  }

  def all : List[Registration] = H2.database.withSession { implicit session =>
    registrations.list
  }
}
