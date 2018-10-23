package models

import play.api.data._
import play.api.data.Forms._

import scala.collection.immutable.Range
import scala.collection.mutable.ArrayBuffer

case class Person(firstName: String, LastName: String, age:Int)

object Person {

  val createPersonForm = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "age"  -> number(min = 0,max = 122)
    )(Person.apply)(Person.unapply)
  )

  val Persons = ArrayBuffer(
    Person("John", "Smith",5),
    Person("Sam", "Johnson",27),
    Person("Rob", "Brown",98)
  )

}