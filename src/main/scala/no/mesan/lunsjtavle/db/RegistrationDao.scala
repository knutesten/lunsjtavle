package no.mesan.lunsjtavle.db

import no.mesan.lunsjtavle.model.Registrations

import scala.slick.driver.H2Driver.simple._

/**
 * @author Knut Esten Melandsø Nekså
 */
object RegistrationDao {
  private val registrations = TableQuery[Registrations]

  def create() = H2.database.withSession { implicit session =>
    registrations.ddl.create
  }
}
