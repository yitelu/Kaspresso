package com.kaspersky.kaspressample.device_tests

import com.kaspersky.kaspresso.internal.exceptions.AdbServerException
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Assert.assertTrue
import org.junit.Test

class DeviceServerSampleTest : TestCase() {

    @Test
    fun serverSampleTest() {
        run {

            step("Execute command on host") {
                val result = adbServer.performCmd("hostname")
                assertTrue(result.isNotEmpty())
            }

            step("Execute ADB command") {
                val command = "undefined_command"

                try {
                    adbServer.performAdb(command)
                } catch (ex: AdbServerException) {
                    assertTrue("unknown command $command" in ex.message)
                }
            }

            step("Execute ADB Shell command") {
                val command = "pm list packages ${device.targetContext.packageName}"

                val result = adbServer.performShell(command)
                assertTrue("package:${device.targetContext.packageName}" in result.first())
            }
        }
    }
}
