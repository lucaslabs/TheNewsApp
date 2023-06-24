package com.thenewsapp.playground

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

fun threadName(): String = Thread.currentThread().name

fun main() = runBlocking {

    // Launch -> fire and forget (e.g: Analytics)
    val job = launch {
        try {
            println("Executing doHelloWorld in Thread ${threadName()}")
            doHelloWorld()
        } catch (e: Exception) {
            println("*** Launch Something went wrong: ${e.message}")
        }
    }
//    job.cancel()

    println("Hello World in Thread ${threadName()}")

    // Async - await -> parallel execution with result
    val time = measureTimeMillis {
        supervisorScope {
            val helloWorldDeferred = async(Dispatchers.IO) {
                println("Executing doHelloWorldAsync in Thread ${threadName()}")
                doHelloWorldAsync()
            }

            val helloWorld = try {
                helloWorldDeferred.await()
            } catch (e: Exception) {
                "Error in helloWorld"
            }

            val helloMoonDeferred = async(Dispatchers.IO) { doHelloMoonAsync() }

            val helloMoon = try {
                helloMoonDeferred.await()
            } catch (e: Exception) {
                "Error in helloMoon"
            }

            val helloWorldAndMoonAsync = "$helloWorld \n$helloMoon"

            println("Result of helloWorldAndMoonAsync in Thread ${threadName()}")
            println(helloWorldAndMoonAsync)
        }
    }

    println("Async - await completed in $time ms")

    // With context -> serial execution with result
    try {
        coroutineScope {
            val helloWithContext = withContext(Dispatchers.IO) {
                println("Executing doHelloWithContext in Thread ${threadName()}")
                doHelloWithContext()
            }
            val helloWorldWithContext =
                withContext(Dispatchers.IO) { doHelloWorldWithContext(helloWithContext) }

            println("Result of helloWorldWithContext in Thread ${threadName()}")
            println(helloWorldWithContext)
        }
    } catch (e: Exception) {
        println("*** With context Something went wrong: ${e.message}")
    }
}

// My first Coroutine :)
suspend fun doHelloWorld() {
    delay(100L)
    println("Hello World launch")
//    throw Exception("Exception")
}

suspend fun doHelloWorldAsync(): String? {
    delay(100L)
    return "Hello World async"
}

suspend fun doHelloMoonAsync(): String? {
    delay(100L)
    return "Hello Moon async"
//    throw Exception("Exception")
}

suspend fun doHelloWithContext(): String {
    delay(100L)
    return "Hello"
}

suspend fun doHelloWorldWithContext(hello: String = "Hello "): String? {
    delay(100L)
    return "$hello World withContext"
//    throw Exception("Exception")
}
