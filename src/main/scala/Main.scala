import cats.effect.{ExitCode, IO, IOApp}
import AlpacaIOAlgebras.{IOAccountAlg, IOAssetAlg}
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp {

  implicit val clientResource = BlazeClientBuilder[IO](global).resource

  override def run(args: List[String]): IO[ExitCode] = for {
    acc            <- new IOAccountAlg().account
    assets         <- new IOAssetAlg().assets
    _              <- IO(println(acc))
    _              <- IO(println(assets))
  } yield ExitCode.Success
}


