package jp.midx.ex

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import spray.testkit.ScalatestRouteTest
import spray.http.StatusCodes.MethodNotAllowed

class MyServiceSpec extends FlatSpec with ScalatestRouteTest with ShouldMatchers with MyService {
  def actorRefFactory = system
  
  "MyService" should "return a greeting for GET requests to the root path" in {
    Get() ~> myRoute ~> check {
      entityAs[String] should include ("Say hello")
    }
  }

  it should "leave GET requests to other paths unhandled" in {
    Get("/kermit") ~> myRoute ~> check {
      handled should be (false)
    }
  }

  it should "return a MethodNotAllowed error for PUT requests to the root path" in {
    Put() ~> sealRoute(myRoute) ~> check {
      status === MethodNotAllowed
      entityAs[String] === "HTTP method not allowed, supported methods: GET"
    }
  }
}
