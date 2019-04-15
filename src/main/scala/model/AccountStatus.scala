package model

import cats.effect.IO
import cats.syntax.functor._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

sealed trait AccountStatus

case object Onboarding extends AccountStatus

case object SubmissionFailed extends AccountStatus

case object Submitted extends AccountStatus

case object AccountUpdated extends AccountStatus

case object ApprovalPending extends AccountStatus

case object Active extends AccountStatus

case object Rejected extends AccountStatus

object AccountStatus {

  implicit val accountStatusEncoder: Encoder[AccountStatus] =
    Encoder.instance {
      status => status match {
        case Onboarding => status.asJson
        case SubmissionFailed => status.asJson
        case Submitted => status.asJson
        case AccountUpdated => status.asJson
        case ApprovalPending => status.asJson
        case Active => status.asJson
        case Rejected => status.asJson
      }
    }

  implicit val accountStatusDecoder: Decoder[AccountStatus] =
    List[Decoder[AccountStatus]](
      Decoder[Onboarding.type].widen,
      Decoder[SubmissionFailed.type].widen,
      Decoder[Submitted.type].widen,
      Decoder[AccountUpdated.type].widen,
      Decoder[ApprovalPending.type].widen,
      Decoder[Active.type].widen,
      Decoder[Rejected.type].widen
    ).reduceLeft(_ or _)

  implicit val AccountStatusEntityDecoder: EntityDecoder[IO, AccountStatus] = jsonOf
}