import cats.effect.{ExitCode, IO, IOApp}
import AlpacaIOAlgebras.{IOAccountAlg, baseURI}
import model.Account
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.{Header, Headers, Request}

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = for {
//    x <- (BlazeClientBuilder[IO](global).resource.use { client =>
//
//      client.expect[String]("https://www.ta3.com")
//    })
//    _ <- IO(println(x))
    acc <- new IOAccountAlg().getAccount
    _ <- IO(println(acc))
  } yield ExitCode.Success
}
