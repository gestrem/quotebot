package quotebot

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:8080")
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
		.exec(http("request_5")
			.post("/api/order")
			.headers(headers_0)
			.body(RawFileBody("default1/recordedsimulation/0005_request.json")))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}