import cats.effect.{ExitCode, IO, IOApp}
import AlpacaIOAlgebras.IOAccountAlg
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = for {

    clientResource <- IO(BlazeClientBuilder[IO](global).resource)
    acc            <- new IOAccountAlg(clientResource).getAccount
    _              <- IO(println(acc))
  } yield ExitCode.Success
}


