package com.example.ebs.service

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.ebs.ui.theme.provider
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.createSupabaseClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val supabase: SupabaseClient
) {
    fun sighUpWithEmail(emailValue: String, passwordValue: String): Flow<AuthResponse> = flow {
        try{
            supabase.auth.signUpWith(Email){
                email = emailValue
                password = passwordValue
            }
            emit(AuthResponse.Success)
        } catch(e: Exception){
            emit(AuthResponse.Error(e.localizedMessage))
        }
    }

    fun sighInWithEmail(emailValue: String, passwordValue: String): Flow<AuthResponse> = flow {
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

    private var googleIdTokenCredential: GoogleIdTokenCredential? = null

    fun loginGoogleUser(context: Context): Flow<AuthResponse> = flow {
        val hashedNonce = createNonce()
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("981332637673-otgo810evdbis5hd6oa6pkiq4ha6dq3e.apps.googleusercontent.com")
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

    fun getGoogleProfilePictureUrl(): String? {
        val payload = googleIdTokenCredential?.idToken?.let { idToken ->
            val parts = idToken.split(".")
            if (parts.size == 3) {
                val payloadJson = android.util.Base64.decode(parts[1], android.util.Base64.URL_SAFE)
                val json = String(payloadJson)
                val regex = """"picture"\s*:\s*"([^"]+)"""".toRegex()
                regex.find(json)?.groups?.get(1)?.value
            } else null
        }
        return payload
    }

    fun signOut(): Flow<AuthResponse> = flow {
        try {
            supabase.auth.signOut()
            emit(AuthResponse.Success)
        } catch (e: Exception) {
            emit(AuthResponse.Error(e.localizedMessage))
        }
    }

    fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.fold(""){ str,it ->
            str + "%02x".format(it)
        }
    }
}