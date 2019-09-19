package quotebot

import scala.concurrent.duration._
import scala.util.Properties
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	var quoteBaseURL = System.getenv("quoteBaseURL")
	quoteBaseURL = "http://"+quoteBaseURL+":8080"
	print("BASE URL :"+quoteBaseURL)
	val httpProtocol = http
		.baseUrl(quoteBaseURL)
		.proxy(Proxy("localhost", 4200).httpsPort(443))
		.inferHtmlResources(BlackList(), WhiteList())
		.acceptHeader("*/*")
		.contentTypeHeader("application/json")
		.userAgentHeader("insomnia/6.6.2")

	val headers_0 = Map("Proxy-Connection" -> "Keep-Alive")



	val scn = scenario("RecordedSimulation")
		.exec(http("request_0")
			.post("/api/user")
			.headers(headers_0)
			.body(RawFileBody("../resources/bodies/user.json"))) //create quote user 
		.pause(12)
		.exec(http("request_1")
			.post("/api/order")
			.headers(headers_0)
			.body(RawFileBody("../resources/bodies/buy_rh.json"))) // buy 5 RH stocks 
		.pause(3)
		.exec(http("request_2")
			.post("/api/order")
			.headers(headers_0)
			.body(RawFileBody("../resources/bodies/buy_rh.json"))) // buy 5 IBM stocks
		.pause(7)
		.exec(http("request_3")
			.post("/api/order")
			.headers(headers_0)
			.body(RawFileBody("../resources/bodies/sell_ibm.json"))) // sell 5 IMB stocks
		.pause(9)
		.exec(http("request_4")
			.post("/api/order")
			.headers(headers_0)
			.body(RawFileBody("../resources/bodies/sell_rh.json"))) // sell 5 RH stocks
		.pause(20)
	setUp(scn.inject(
		constantUsersPerSec(20) during(15 seconds)
		)).protocols(httpProtocol)
}