package net.andimiller.http4s.dsl

import org.http4s.HttpRoutes
import org.http4s.implicits._
import org.http4s._
import cats._, cats.data._, cats.implicits._
import cats.effect._, cats.effect.implicits._

object DSL {

  object BiHttpRoutes {
    def of[F[_]](pf: PartialFunction[Request[F], EitherT[F, Response[F], Response[F]]])(
      implicit F: Sync[F]): HttpRoutes[F] =
    Kleisli(req => OptionT(F.suspend(pf.lift(req).map(_.merge).sequence)))
  }

}
