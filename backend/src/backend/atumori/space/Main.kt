package backend.atumori.space

import io.ktor.network.tls.certificates.generateCertificate
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.File

fun main(args: Array<String>) {

    val file = File("build/temporary.jks")
    if (!file.exists()) {
        file.parentFile.mkdirs()
        generateCertificate(file)
    }

    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

