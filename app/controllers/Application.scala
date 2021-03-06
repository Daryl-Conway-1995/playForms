package controllers

import javax.inject.Inject
import models.{CD, Person}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

class Application @Inject()(val messagesApi: MessagesApi, environment: play.api.Environment) extends Controller with I18nSupport {

  def listCDs = Action { implicit request =>
    // we return a view file which expects two arguments passed to it
    // The first argument is the Seq[CD] which contains all the CDs we want to display
    // The second argument is the Form[CD], where we pass the form we have created
    Ok(views.html.listCDs(CD.cds, CD.createCDForm))
  }

  def createCD = Action { implicit request =>
    // we create a value to which we assign the form and bind the values that were submitted and are in the response
    val formValidationResult = CD.createCDForm.bindFromRequest
    // we then fold over the form where fold is a method that belongs to Form and what it does is
    // takes in two functions, where the first one has to handle the form with errors
    // and the second one has to handle the successful form submission
    // you could look at fold like this .fold({ Function for error}, { Function for success })
    // the first function of folding is the one where the data passed for the form was incorrect
    // therefore our form with the errors on it is binded to the formWithErrors
    // And we then return a BadRequest to signify that it wasn't correct and for the BadRequest we then pass
    // the same view file, as the view file requires an argument of Seq[CD] we pass that from the CD object
    // as well as the form with errors, where passing the form with errors is going to display the errors on the page
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.listCDs(CD.cds, formWithErrors))
    }, { cd =>
      // the second case is the good one were the data is of the correct type therefore the cd will hold the value
      // of CD, which we then add to the list of the CDs that we have inside the CD object
      // and lastly we return a Redirect response where we take the person to the same page but list all the CDs out
      // as we're using the reverse route of .listCDs, therefore after adding a CD and submitting the form
      // we will see the CD come up on the page
      CD.cds.append(cd)
      Redirect(routes.Application.listCDs)
    })
  }


  def listPerson = Action { implicit request =>
    Ok(views.html.listPersons(Person.Persons, Person.createPersonForm))
  }

  def createPerson = Action { implicit request =>
    val formValidationResult = Person.createPersonForm.bindFromRequest

    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.listPersons(Person.Persons, formWithErrors))
    }, { person =>
      Person.Persons.append(person)
      Redirect(routes.Application.listPerson)
    })
  }



}


