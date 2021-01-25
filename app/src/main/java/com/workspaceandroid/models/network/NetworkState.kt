package com.workspaceandroid.models.network

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.workspaceandroid.R
import com.workspaceandroid.data.remote.base.ServerErrorResponse
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

enum class ApiRequestStatus {
    RUNNING,
    SUCCESSFUL,
    FAILED,
    NO_INTERNET
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: ApiRequestStatus,
        val commonError: CommonServerError? = null,
        val errorResourceId: Int? = null
) {

    companion object {
        const val NO_INTERNET_CODE = "no_internet_code"
        const val UNKNOWN_ERROR = "unknown_error_code"
        const val BAD_REQUEST = "bad request"
        const val NO_INTERNET_CONNECTION = "No Internet Connection!"

        private val localizationErrors = hashMapOf(  //todo fill data
                Pair("Please insert a valid phone number", R.string.error_phone_number),
                Pair("You must accept our terms and conditions", R.string.error_accept_terms_conditions),
                Pair("ERROR_INVALID_USER_EMAIL",R.string.error_invalid_user_email))


        private const val TITLE_ERROR = "error"
        private const val TITLE_UNKNOWN_ERROR = "unknown error"

        val LOADING = NetworkState(ApiRequestStatus.RUNNING)
        val SUCCESSFUL = NetworkState(ApiRequestStatus.SUCCESSFUL)
        val FAILED = NetworkState(ApiRequestStatus.FAILED)
        val NO_INTERNET = NetworkState(ApiRequestStatus.NO_INTERNET)


        fun error(msg: String?, title: String? = null, code: String? = null): NetworkState {
            val error = CommonServerError(
                    code ?: UNKNOWN_ERROR, msg ?: TITLE_UNKNOWN_ERROR
            )
            return NetworkState(
                    ApiRequestStatus.FAILED,
                    error
            )
        }

        fun error(msgResource: Int): NetworkState {
            return NetworkState(
                    status = ApiRequestStatus.FAILED,
                    errorResourceId = msgResource
            )
        }

        fun parseError(error: String?): Int {
            return if (error != null) {
                localizationErrors.getOrElse(error) {
                    R.string.error_server_default
                }
            } else {
                R.string.error_server_default
            }
        }

        fun error(t: Throwable?): NetworkState {
            return when (t) {
                is HttpException -> {
                    val errorBody = t.response()!!.errorBody() //TODO implicitly
                    val serverError =
                            getCommonErrorFromResponse(errorBody)
                    NetworkState(
                            ApiRequestStatus.FAILED,
                            serverError
                    )
                }
                is IOException -> {
                    val serverError = CommonServerError(
                            NO_INTERNET_CODE,
                            "You do not have internet connection"
                    )
                    NetworkState(
                            ApiRequestStatus.FAILED,
                            serverError
                    )
                }
                else -> {
                    val serverError =
                            CommonServerError(UNKNOWN_ERROR, TITLE_UNKNOWN_ERROR)
                    NetworkState(
                            ApiRequestStatus.FAILED,
                            serverError
                    )
                }
            }
        }

//        fun error(errorBody: ResponseBody?): NetworkState {
//            val errorBodyContent = errorBody?.string()
//            logd("NetworkState", errorBodyContent
//                    ?: "") // java.lang.NullPointerException: println needs a message
//            val serverError = getErrorFromResponse(errorBodyContent)
//            val serverErrors = getErrorsFromResponse(errorBodyContent)
//            return when {
//                serverError?.errorCode == "Unauthorized" -> NetworkState(ApiRequestStatus.FAILED, serverError)
//                serverError?.errorCode != UNKNOWN_ERROR -> NetworkState(ApiRequestStatus.FAILED, serverError)
//                serverErrors?.errorCode != UNKNOWN_ERROR -> NetworkState(ApiRequestStatus.FAILED, serverErrors)
//                else -> NetworkState(ApiRequestStatus.FAILED, CommonServerError(UNKNOWN_ERROR, TITLE_UNKNOWN_ERROR))
//            }
//        }

//        private fun getErrorFromResponse(errorBody: String?): CommonServerError? {
//            return errorBody?.let {
//                try {
//                    val errorResponse =
//                            Gson().fromJson(errorBody, ServerErrorResponse::class.java)
//                    CommonServerError(
//                            errorResponse?.code.toString(),
//                            errorResponse?.data?.get(0)?.error ?: TITLE_UNKNOWN_ERROR
//                    )
//                } catch (jse: JsonSyntaxException) {
//                    jse.printStackTrace()
//                    null
//                } catch (jpe: JsonParseException) {
//                    jpe.printStackTrace()
//                    null
//                }
//            } ?: CommonServerError(UNKNOWN_ERROR, TITLE_UNKNOWN_ERROR)
//        }
//
//        private fun getErrorsFromResponse(errorBody: String?): CommonServerError? {
//            return errorBody?.let {
//                try {
//                    val errorResponse =
//                            Gson().fromJson(errorBody, ServerErrorsResponse::class.java)
//                    CommonServerError(
//                            errorResponse?.code.toString(),
//                            errorResponse?.data?.get(0)?.error?.get(0) ?: TITLE_UNKNOWN_ERROR
//                    )
//                } catch (jse: JsonSyntaxException) {
//                    jse.printStackTrace()
//                    null
//                } catch (jpe: JsonParseException) {
//                    jpe.printStackTrace()
//                    null
//                }
//            } ?: CommonServerError(UNKNOWN_ERROR, TITLE_UNKNOWN_ERROR)
//        }

        private fun getCommonErrorFromResponse(
                errorBody: ResponseBody?,
                alternateError: String? = null
        ): CommonServerError? {
            return errorBody?.let {
                try {
                    val errorResponse =
                            Gson().fromJson(errorBody.string(), ServerErrorResponse::class.java)
                    errorResponse?.data?.get(0)
                } catch (jse: JsonSyntaxException) {
                    jse.printStackTrace()
                    null
                } catch (jpe: JsonParseException) {
                    jpe.printStackTrace()
                    null
                }
            } ?: CommonServerError(
                    UNKNOWN_ERROR,
                    alternateError ?: TITLE_UNKNOWN_ERROR
            )
        }
    }
}
