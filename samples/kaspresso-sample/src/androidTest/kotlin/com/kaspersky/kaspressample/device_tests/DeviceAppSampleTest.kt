package com.kaspersky.kaspressample.device_tests

import android.Manifest
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspressample.MainActivity
import com.kaspersky.kaspresso.device.apps.Apps
import com.kaspersky.kaspresso.device.server.AdbServer
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Installs and then uninstalls an apk file placed at /artifacts directory.
 * [Apps.install] uses the [TEST_APK_FILE_RELATIVE_PATH] relative path to install the apk.
 * So, you should run the server with command `cd /absolute/path/to/project/directory & java -jar artifacts/adbserver-desktop.jar`
 */
class DeviceAppSampleTest : TestCase() {

    companion object {
        /**
         * Compiled 'Hello, World' project, auto-generated by Android Studio.
         */
        private const val TEST_APK_FILE_RELATIVE_PATH = "artifacts/hello_world.apk"
        private const val TEST_APK_PACKAGE_NAME = "com.example.helloworld"
    }

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun test() {
        run {
            step("Install hello world apk") {
                device.apps.install(TEST_APK_FILE_RELATIVE_PATH)
                assertTrue(isAppInstalled(adbServer, TEST_APK_PACKAGE_NAME))
            }

            step("Delete the application") {
                device.apps.uninstall(TEST_APK_PACKAGE_NAME)
                assertFalse(isAppInstalled(adbServer, TEST_APK_PACKAGE_NAME))
            }
        }
    }

    private fun isAppInstalled(adbServer: AdbServer, packageName: String): Boolean =
        "package:$packageName" in adbServer.performShell("pm list packages $packageName").first()
}
