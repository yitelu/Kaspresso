package com.kaspersky.kaspressample.flaky_tests

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspressample.MainActivity
import com.kaspersky.kaspressample.R
import com.kaspersky.kaspressample.external_screens.UiCommonFlakyScreen
import com.kaspersky.kaspressample.external_screens.UiMainScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class UiCommonFlakyTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun test() = run {
        step("Open Scroll View Stub Screen") {
            UiMainScreen {
                flakyButton {
                    click()
                }
            }
        }

        step("Check ScrollView screen is visible") {
            UiCommonFlakyScreen {
                scrollView {
                    isDisplayed()
                }
            }
        }

        step("Check btn5's text") {
            UiCommonFlakyScreen {
                btn5 {
                    // automate flaky safety handling is in action
                    // even UiAutomator is using under the hood =)
                    // the text is changing during 3 seconds
                    // the default value of flaky safety timeout = 10 seconds
                    hasText(device.targetContext.getString(R.string.common_flaky_final_button).uppercase())
                }
            }
        }

        step("Check tv6's text") {
            UiCommonFlakyScreen {
                tv6 {
                    // here, the text will be changing longer(summary = 15 seconds) than
                    //     the default value of flaky safety timeout(10 seconds)
                    // that's why we use flakySafely method obviously
                    flakySafely(timeoutMs = 16_000) {
                        hasText(device.targetContext.getString(R.string.common_flaky_final_textview))
                    }
                }
            }
        }
    }
}
