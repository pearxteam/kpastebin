/*
 * Copyright © 2020, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package net.pearx.kpastebin.model

/**
 * The list of possible Pastebin paste privacy states.
 */
public enum class Privacy {
    /** Paste is visible for all users. */
    PUBLIC,

    /** Paste is invisible for others unless you share your paste link. */
    UNLISTED,

    /** Paste is visible only for you. */
    PRIVATE;
}
