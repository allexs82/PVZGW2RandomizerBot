package ru.allexs82.render

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.Executors

object RenderWebService {
    val server: HttpServer = HttpServer.create(InetSocketAddress("0.0.0.0", 8080), 0)

    fun start() {
        server.createContext("/") { exchange ->
            exchange.sendResponseHeaders(403, 0)
        }
        server.executor = Executors.newSingleThreadExecutor()
        server.start()
    }
}