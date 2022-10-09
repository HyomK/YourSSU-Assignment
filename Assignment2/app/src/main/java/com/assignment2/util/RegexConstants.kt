package com.assignment2.util

object RegexConstants {
    val PWD_REGEX = Regex("^(?=.*[A-Za-z])(?=.*\\d)[!-~₩]{8,100}\$")
    val NICKNAME_REGEX = Regex("""^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$""")
}