package com.uglyeagle

import org.apache.ftpserver.FtpServerFactory
import org.apache.ftpserver.listener.ListenerFactory
import org.apache.ftpserver.usermanager.impl.BaseUser
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission
import org.apache.ftpserver.usermanager.impl.TransferRatePermission
import org.apache.ftpserver.usermanager.impl.WritePermission

fun main() {
    val serverFactory = FtpServerFactory()

    // Listener on port 2221
    val listenerFactory = ListenerFactory().apply {
        port = 2221
    }
    serverFactory.addListener("default", listenerFactory.createListener())

    println("-------------------------------------------------------")

    val user = BaseUser().apply {
        name = "root"
        password = "root"
        homeDirectory = "C:/root-ftp" // Make sure this folder exists
        authorities = listOf(
            WritePermission(),
            ConcurrentLoginPermission(5, 2),
            TransferRatePermission(1024, 2048)
        )
    }

    serverFactory.userManager.save(user)

    println("-------------------------------------------------------")

    val server = serverFactory.createServer()
    server.start()
}
