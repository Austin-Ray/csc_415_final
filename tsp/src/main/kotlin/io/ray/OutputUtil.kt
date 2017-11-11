
/*
 * Copyright (c) 2017.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.ray

/**
 * Print a formatted message to the console.
 * @param message   Message to print.
 */
fun printMessage(message: String) = println("=== $message ===")

/**
 * Format a floating point number to a desired decimal places.
 * @param arg   Number of decimal places to show.
 */
fun Double.format(arg: Int) : String {
  return this.toString().format("%.${arg}f")
}
