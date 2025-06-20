package com.example.ebs.service.auth

import android.content.Context
import android.util.Base64
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.ebs.data.structure.GoogleProfileFields
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val supabase: SupabaseClient
) {
    private var signInResult: UserInfo? = null
    private var googleIdTokenCredential: GoogleIdTokenCredential? = null

    fun signOut(): Flow<AuthResponse> = flow {
        try {
            supabase.auth.signOut()
            emit(AuthResponse.Success)
        } catch (e: Exception) {
            emit(AuthResponse.Error(e.localizedMessage))
        }
    }

    fun isSignedIn(): Boolean {
        return supabase.auth.currentUserOrNull() != null
    }

    fun getUserId(): String? {
        return supabase.auth.currentUserOrNull()?.id
    }

    fun getAuthToken(): String? {
        return supabase.auth.currentSessionOrNull()?.accessToken
    }

    fun checkVerification(): Instant?{
        return if (signInResult?.emailConfirmedAt == null)
            supabase.auth.currentUserOrNull()?.emailConfirmedAt
        else
            signInResult?.emailConfirmedAt
    }

//    fun resendVerificationEmail(emailValue: String): Flow<AuthResponse> = flow {
//        try {
//           supabase.auth.sendEmailOtp(type = OtpType.Email, email = emailValue)
//            emit(AuthResponse.Success)
//        } catch (e: Exception) {
//            emit(AuthResponse.Error(e.localizedMessage))
//        }
//    }

    fun signUpWithEmail(
        emailValue: String, passwordValue: String
    ): Flow<AuthResponse> = flow {
        try{
           signInResult = supabase.auth.signUpWith(Email) {
               email = emailValue
               password = passwordValue
           }
            emit(AuthResponse.Success)
        } catch(e: Exception){
            emit(AuthResponse.Error(e.localizedMessage))
        }
    }

    fun signInWithEmail(
        emailValue: String, passwordValue: String
    ): Flow<AuthResponse> = flow {
        try{
            supabase.auth.signInWith(Email){
                email = emailValue
                password = passwordValue
            }
            emit(AuthResponse.Success)
        } catch(e: Exception){
            emit(AuthResponse.Error(e.localizedMessage))
        }
    }

    fun loginGoogleUser(
        context: Context
    ): Flow<AuthResponse> = flow {
        val hashedNonce = createNonce()
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("981332637673-7mots16s12g5habetmuav47s59o3nu8v.apps.googleusercontent.com")
            .setAutoSelectEnabled(false)
            .setNonce(hashedNonce)
            .build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        val credentialManager = CredentialManager.create(context)
        try{
            val credential = credentialManager.getCredential(
                context = context,
                request = request
            )
            googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credential.credential.data)
            val googleIdToken = googleIdTokenCredential!!.idToken
            supabase.auth.signInWith(IDToken) {
                idToken = googleIdToken
                provider = Google
            }
            emit(AuthResponse.Success)
        } catch (e: Exception){
            emit(AuthResponse.Error("Error: ${UUID.randomUUID()} - ${e.localizedMessage}"))
        }
    }

    fun getGoogleProfileInfo(): GoogleProfileFields? {
        val json: String? = if (googleIdTokenCredential != null) {
            googleIdTokenCredential?.idToken?.let { idToken ->
                val parts = idToken.split(".")
                if (parts.size == 3) {
                    val payloadJson =
                        Base64.decode(parts[1], Base64.URL_SAFE)
                    String(payloadJson)
                } else null
            }
        } else {
            supabase.auth.currentSessionOrNull()?.accessToken?.let { idToken ->
                val parts = idToken.split(".")
                if (parts.size == 3) {
                    val payloadJson =
                        Base64.decode(parts[1], Base64.URL_SAFE)
                    String(payloadJson)
                } else null
            }
        }
        return json?.let {
            GoogleProfileFields(
                email = """"email"\s*:\s*"([^"]+)"""".toRegex().find(it)?.groups?.get(1)?.value,
                emailVerified = """"email_verified"\s*:\s*("([^"]+)"|true|false)""".toRegex()
                    .find(it)?.groups?.get(2)?.value
                    ?: """"email_verified"\s*:\s*("([^"]+)"|true|false)""".toRegex()
                        .find(it)?.groups?.get(1)?.value,
                name = """"name"\s*:\s*"([^"]+)"""".toRegex().find(it)?.groups?.get(1)?.value ?: "Kamu",
                givenName = """"given_name"\s*:\s*"([^"]+)"""".toRegex()
                    .find(it)?.groups?.get(1)?.value,
                familyName = """"family_name"\s*:\s*"([^"]+)"""".toRegex()
                    .find(it)?.groups?.get(1)?.value,
                locale = """"locale"\s*:\s*"([^"]+)"""".toRegex().find(it)?.groups?.get(1)?.value,
                picture = """"picture"\s*:\s*"([^"]+)"""".toRegex().find(it)?.groups?.get(1)?.value
            )
        }
    }

    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.fold(""){ str,it ->
            str + "%02x".format(it)
        }
    }
}