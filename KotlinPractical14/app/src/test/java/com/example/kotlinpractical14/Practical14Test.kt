package com.example.kotlinpractical14

import android.graphics.PointF.length
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.util.regex.Pattern

// Exercise 1: Basic Function Testing
class Calculator {
    fun multiply(a: Int, b: Int): Int = a*b
}


class CalculatorTest {
    @Test
    fun multiplyTest() {
        val calculator = Calculator()
        assertEquals(20, calculator.multiply(4,5))
    }

}

//Exercise 2: Testing string manipulation
class StringUtils {
    fun reverse(str:String): String = str.reversed()
}

class StringUtilsTest {
    @Test
    fun reverseTest() {
        assertEquals("dlrow olleh", StringUtils().reverse("hello world"))
    }

}

//Exercise 3: Validating email address

class EmailValidator {
    fun isValidEmail(email: String): Boolean {
        val EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+")

        return EMAIL_ADDRESS.matcher(email).matches()
    }
}

class EmailValidatorTest {
    @Test
    fun validEmail() {
        assertTrue(EmailValidator().isValidEmail("test@gmail.com"))

    }

    @Test
    fun invalidEmail() {
        assertFalse(EmailValidator().isValidEmail("test"))

    }
}

//Exercise 4: User Authentication
class UserAuthenticator {
    private fun validUser(username: String): Boolean {
        // at least 5 characters long
        // start with a letter
        // only letters and numbers, no special characters, spaces or symbols
        return (username.length >= 5) && (username.all {char -> char.isLetterOrDigit()})
    }

    private fun validPassword(password: String): Boolean {
        // 12 characters or more
        // combination of uppercase letters, lowercase letters, numbers and symbols

        val specialCharacters = Pattern.compile("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]")


        return (password.length >= 12)
                && (password.any { char -> char.isUpperCase()})
                && (password.any { char -> char.isLowerCase()})
                && (password.any { char -> char.isDigit()})
                && (specialCharacters.matcher(password).find())

    }

    fun authenticate(username: String, password: String): Boolean {
        return validUser(username) && validPassword(password)
    }
}

class UserAuthenticatorTest {
    @Test
    fun `valid user and invalid password`() {
        val userAuthenticator = UserAuthenticator()
        assertFalse(userAuthenticator.authenticate("JohnDoe", "password"))
    }

    @Test
    fun `invalid user and valid password`() {
        val userAuthenticator = UserAuthenticator()
        assertFalse(userAuthenticator.authenticate("Jh!***", "Welcome2019!"))

    }

    @Test
    fun `valid user and valid password`() {
        val userAuthenticator = UserAuthenticator()
        assertTrue(userAuthenticator.authenticate("JohnDoe", "Welcome2019!"))

    }

    @Test
    fun `invalid user and invalid password`() {
        val userAuthenticator = UserAuthenticator()
        assertFalse(userAuthenticator.authenticate("!!hgasd", "password"))

    }
}

//Exercise 5: RecyclerView Item count

class MyAdapterTest {
    @Test
    fun `correct adapter count`() {
        val adapter = MyAdapter(listOf(Item("Item 1"), Item("Item 2"), Item("Item 3")))
        assertEquals(3, adapter.itemCount)
    }

    @Test
    fun `incorrect adapter count`() {
        val adapter = MyAdapter(listOf(Item("Item 1"), Item("Item 2"), Item("Item 3")))
        assertNotEquals(5, adapter.itemCount)

    }

}


//Exercise 6: Testing a password strength checker
class PasswordValidator {
    fun isPasswordStrong(password: String): Boolean {
        val specialCharacters = Pattern.compile("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]")


        return (password.length >= 12)
                && (password.any { char -> char.isUpperCase()})
                && (password.any { char -> char.isLowerCase()})
                && (password.any { char -> char.isDigit()})
                && (specialCharacters.matcher(password).find())

    }
}

class PasswordValidatorTest {
    @Test
    fun `strong password`() {
        assertTrue(PasswordValidator().isPasswordStrong("Password1234!@#"))
    }

    @Test
    fun `weak password`() {
        assertFalse(PasswordValidator().isPasswordStrong("password"))
    }
}


//Exercise 7: Testing a leap year calculator
class DateUtils {
    fun isLeapYear(year: Int): Boolean {
        // Leap year if divisible by 4 and not 100, or divisible by 400
        return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))
    }
}

class DateUtilsTest {
    @Test
    fun `leap year`() {
        assertTrue(DateUtils().isLeapYear(2000))
    }

    @Test
    fun `not leap year`() {
        assertFalse(DateUtils().isLeapYear(2001))
    }
}

//Exercise 8: Testing a User age validator
class UserValidator {
    fun isAgeValid(age: Int): Boolean {
        return age in 18..65
    }
}

class UserValidatorTest {
    @Test
    fun `valid user`() {
        assertTrue(UserValidator().isAgeValid(23))
    }

    @Test
    fun `invalid user`() {
        assertFalse(UserValidator().isAgeValid(11))

    }
}

