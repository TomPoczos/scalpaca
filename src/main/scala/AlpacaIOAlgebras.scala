import java.time.LocalDateTime

import AlpacaAlgebras.AccountAlg
import org.http4s.client.blaze._
import org.http4s.client._
import org.http4s.client._
import org.http4s.client.dsl.io._
import org.http4s.client.blaze._
import fs2.Stream
import io.circe._

import scala.concurrent.ExecutionContext.Implicits.global
import cats.effect.{Concurrent, ContextShift, IO}
import io.circe.Decoder
import io.circe.generic.AutoDerivation
import org.http4s.{EntityDecoder, Header, Headers, Request, Uri}
import org.http4s.circe._

//import io.circe.generic.semiauto._
//import io.circe.generic.auto._
// import model.account.Account


package object AlpacaIOAlgebras extends AutoDerivation {
//  implicit val fooDecoder: Decoder[Account] = deriveDecoder




  sealed class AccountStatus
  case object Onboarding extends AccountStatus
  case object SubmissionFailed extends AccountStatus
  case object Submitted extends AccountStatus
  case object AccountUpdated extends AccountStatus
  case object ApprovalPending extends AccountStatus
  case object Active extends AccountStatus
  case object Rejected extends AccountStatus

  case class Account (
                       id: String,
                       status: AccountStatus,
                       currency: String,
                       buyingPower: Double,
                       cash: Double,
                       cashWithdrawable: Double,
                       portfolioValue: Double,
                       patternDayTrader: Boolean,
                       tradingBlocked: Boolean,
                       transfersBlocked: Boolean,
                       accountBlocked: Boolean,
                       createdAt: LocalDateTime
                     )

  private implicit val AccDecoder: EntityDecoder[IO, Account] = jsonOf[IO, Account]
  private implicit val cs: ContextShift[IO] = IO.contextShift(global)


  private val baseURI = Uri(path = "https://api.alpaca.markets")


  class IOAccountAlg extends AccountAlg[IO] {
    override def getAccount: IO[Account] = BlazeClientBuilder[IO](global).resource.use { client =>
      val req = Request[IO](
        uri = baseURI / "/v1/account",
        headers = Headers(
          Header("APCA-API-KEY-ID", ""),
          Header("APCA-API-SECRET-KEY", "")
        )
      )
      client.expect[Account](req)
    }

    def apply: IOAccountAlg = new IOAccountAlg()
  }


}
