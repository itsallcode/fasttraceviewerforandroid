/*-
 * #%L
 \* FastTraceViewerForAndroid
 * %%
 * Copyright (C) 2017 - 2018 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package itsallcode.org.fasttraceviewerforandroid.utils

import itsallcode.org.fasttraceviewerforandroid.platformaccess.AppExecutorsImpl
import java.util.concurrent.Executor

/**
 * Allow instant execution of tasks.
 */
class SingleExecutors : AppExecutorsImpl(instant, instant) {
    companion object {
        private val instant = Executor { command -> command.run() }
    }
}