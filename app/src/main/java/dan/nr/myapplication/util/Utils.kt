package dan.nr.myapplication.util

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dan.nr.myapplication.ui.authentication.login.LoginFragment


fun View.setViewVisibility(isVisible: Boolean)
{
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.isViewEnable(enabled: Boolean)
{
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.9f
}

fun View.snackBar(message: String,
                  action: (() -> Unit)? = null,
                  backgroundColor: Int = LITE_GRAY,
                  textColor: Int = DARk_GRAY)
{
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(backgroundColor)
        setTextColor(textColor)
    }
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(failure: Resource.Error,
                            retry: (() -> Unit)? = null)
{
    when
    {
        failure.isNetworkError ->
        {
            requireView().snackBar("Please check your network connection.", retry, RED_ERROR, WHITE)
        }
        failure.errorCode == 401 ->
        {
            if (this is LoginFragment)
            {
                requireView().snackBar("You have entered wrong email or password",
                                       null,
                                       RED_ERROR,
                                       WHITE)
            } else
            {
                //(this as BaseFragment< *>).logout()
            }
        }
        else ->
        {
            requireView().snackBar(failure.errorBody.toString(),
                                   null,
                                   RED_ERROR,
                                   WHITE)
        }
    }
}

