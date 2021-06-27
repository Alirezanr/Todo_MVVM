package dan.nr.myapplication.util


fun String.isValidEmail() : Boolean = this.matches(Regex("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"))

object Validator{
    fun validateEmail(email:String):Boolean
    {
        return email.isValidEmail()
    }
    fun validatePassword(password:String):Boolean
    {
        return password.length >= 8
    }

}