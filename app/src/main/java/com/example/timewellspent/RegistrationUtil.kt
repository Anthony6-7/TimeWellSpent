package com.example.timewellspent

object RegistrationUtil {
    // use this in the test class for the is username taken test
    // make another similar list for some taken emails
    var existingUsers: List<String> = listOf()
    var existingEmails: ArrayList<String> = arrayListOf()
//    you can use listOf<type>() instead of making the list & adding individually
//    List<String> blah = new ArrayList<String>();
//    blah.add("hi")
//    blah.add("hello")
//

    // isn't empty
    // already taken
    // minimum number of characters is 3
    fun validateUsername(username: String) : Boolean {
        //if(existingUsers.contains(username)) {
        //    return false
        //}
        if(username.length < 3){
            return false
        }
        return true
    }

    // make sure meets security requirements (deprecated ones that are still used everywhere)
    // min length 8 chars
    // at least one digit
    // at least one capital letter
    // both passwords match
    // not empty
    fun validatePassword(password : String, confirmPassword: String) : Boolean {
        if(password.length < 8 || confirmPassword.length < 8){
            return false
        }
        else if(password != confirmPassword){
            return false
        }
        var hasDigit = false
        for(i in 0 until password.length){
            if(password[i].isDigit()){
                hasDigit = true
            }

        }
        if(!hasDigit){
            return false
        }
        var hasUpperCase = false
        for(i in 0 until password.length){
            if(password[i].isUpperCase()){
                hasUpperCase = true
            }
        }
        if(!hasUpperCase){
            return false
        }
        return true
    }

    // isn't empty
    fun validateName(name: String) : Boolean {
        if(name.length > 0){
            return true
        }
        else{
            return false
        }
    }

    // isn't empty
    // make sure the email isn't used
    // make sure it's in the proper email format user@domain.tld
    fun validateEmail(email: String) : Boolean {
        if(email.isEmpty()){
            return false
        }
        //if(existingEmails.contains(email)) {
        //    return false
        //}
        if(!email.contains("@") || !email.contains(".")) {
            return false
        }
        return true
    }
}