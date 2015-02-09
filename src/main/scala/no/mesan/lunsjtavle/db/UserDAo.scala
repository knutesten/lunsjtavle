package no.mesan.lunsjtavle.db

import no.mesan.lunsjtavle.model.{User, Users}

import scala.slick.driver.H2Driver.simple._


/**
 * @author Knut Esten Melandsø Nekså
 */
object UserDao {
  private val users = TableQuery[Users]
  private val byId = users.findBy(_.id)

  def create() = H2.database.withSession { implicit session =>
    users.ddl.create
  }

  def delete(id: Int) = H2.database.withSession { implicit session =>
    byId(id).delete
  }

  def insert(user: User) : User = H2.database.withSession { implicit session =>
    val userId = users returning users.map(_.id) insert user
    byId(userId).list.head
  }

  def update(newUser: User) = H2.database.withSession { implicit session =>
    byId(newUser.id.get).update(newUser)
  }

  def findById(id: Int): Option[User] = H2.database.withSession { implicit session =>
    byId(id).list.headOption
  }

  def all : List[User] = H2.database.withSession { implicit session =>
    users.list
  }
}
