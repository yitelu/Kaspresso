package com.kaspersky.components.uiautomator_dsl.dsl.switch

import com.kaspersky.components.uiautomator_dsl.dsl.common.actions.UiBaseActions
import com.kaspersky.components.uiautomator_dsl.dsl.switch.UiSwitchableActions.SwitchableUiOperationType.SWITCH
import com.kaspersky.components.uiautomator_dsl.intercepting.action_and_assertion.UiOperationType

interface UiSwitchableActions : UiBaseActions {

    enum class Direction { RIGHT, LEFT }

    /**
     * Moves the thumb of the switch to the right
     *
     * @param direction for the thumb to move
     */
    fun swipeSwitchThumb(direction: Direction) {
        actionsView.perform(SWITCH, "direction=$direction") {
            val uiDirection: androidx.test.uiautomator.Direction =
                if(direction == Direction.RIGHT)
                    androidx.test.uiautomator.Direction.RIGHT
                else
                    androidx.test.uiautomator.Direction.LEFT
            swipe(uiDirection, 1f)
        }
    }

    enum class SwitchableUiOperationType : UiOperationType {
        SWITCH
    }
}