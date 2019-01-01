package model

import java.time.LocalDateTime

import io.circe.generic.AutoDerivation

package object account {

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
}
