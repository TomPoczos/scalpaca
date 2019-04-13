package model

import java.time.LocalDateTime
import cats.syntax.functor._

import cats.effect.IO
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import org.http4s.{EntityDecoder, EntityEncoder}
import org.http4s.circe.jsonOf
import io.circe.generic.auto._
import io.circe.syntax._

package object account {

  sealed trait AccountStatus
  case object Onboarding       extends AccountStatus
  case object SubmissionFailed extends AccountStatus
  case object Submitted        extends AccountStatus
  case object AccountUpdated   extends AccountStatus
  case object ApprovalPending  extends AccountStatus
  case object Active           extends AccountStatus
  case object Rejected         extends AccountStatus

  object AccountStatus {

    implicit val accountStatusEncoder: Encoder[AccountStatus] = Encoder.instance {
      case onboarding       @ Onboarding       => onboarding.asJson
      case submissionFailed @ SubmissionFailed => submissionFailed.asJson
      case submitted        @ Submitted        => submitted.asJson
      case accountUpdated   @ AccountUpdated   => accountUpdated.asJson
      case approvalPending  @ ApprovalPending  => approvalPending.asJson
      case active           @ Active           => active.asJson
      case rejected         @ Rejected         => rejected.asJson
    }

    implicit val accountStatusDecoder: Decoder[AccountStatus] =
      List[Decoder[AccountStatus]] (
          Decoder[Onboarding].widen,
          Decoder[SubmissionFailed].widen,
          Decoder[Submitted].widen,
          Decoder[AccountUpdated].widen,
          Decoder[ApprovalPending].widen,
          Decoder[Active].widen,
          Decoder[Rejected].widen
      ).reduceLeft(_ or _)

    implicit val AccountStatusEntityDecoder = jsonOf[IO, AccountStatus]
  }

  case class Account(
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

  object Account {
    implicit val AccountDecoder: EntityDecoder[IO, Account] = jsonOf[IO, Account]
  }

}
