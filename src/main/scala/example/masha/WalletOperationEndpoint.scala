package example.masha

import zio._
import zio.http._
import zio.json._
import scala.io.Source

object WalletOperationEndpoint extends ZIOAppDefault {

  case class Transaction(src: Option[String], dst: Option[String], amount: Option[Int])

  object Transaction {
    implicit val decoder: JsonDecoder[Transaction] =
      DeriveJsonDecoder.gen[Transaction]
  }

  val route: Route[Any, Response] =
    Method.POST / "wallet-operation" -> handler { (req: Request) =>

      val bodyString = Unsafe.unsafe { implicit unsafe =>
        Runtime.default.unsafe.run(req.body.asString).getOrThrowFiberFailure()
      }

      val body = bodyString.fromJson[Transaction]

      val resource = Source.fromResource("blacklist.txt")

      val lines = resource.getLines().toList

      body match {
        case Left(_) => Response.badRequest("error in body")
        case Right(x) =>
          (x.src, x.dst, x.amount) match {
            case (src, _, _)
              if src.isEmpty => Response.badRequest("src is not specified")
            case (_, dst, _)
              if dst.isEmpty => Response.badRequest("dst is not specified")
            case (_, _, amount)
              if amount.isEmpty => Response.badRequest("amount is not specified")
            case (Some(src), Some(dst), _)
              if lines.contains(src) || lines.contains(dst) => Response.badRequest("src or dst in black list")
            case (_, _, _) => Response.ok
          }
      }
    }

  def run = Server.serve(route.toRoutes).provide(Server.default)

}
