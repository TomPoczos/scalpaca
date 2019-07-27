import cats.effect.{ExitCode, IO, IOApp}
import AlpacaIOAlgebras.IOAccountAlg
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp {

  implicit val clientResource = BlazeClientBuilder[IO](global).resource

  override def run(args: List[String]): IO[ExitCode] = for {
    acc            <- new IOAccountAlg().getAccount
    _              <- IO(println(acc))
  } yield ExitCode.Success
}


