import cats.effect.{ExitCode, IO, IOApp}
import AlpacaIOAlgebras.IOAccountAlg

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = for {
    acc <- new IOAccountAlg().getAccount
    _ <- IO(println(acc))
  } yield ExitCode.Success
}
