package com.example.ebs.service

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.IDTokenProvider
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val supabase: SupabaseClient
) {
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
        Log.e("TAG", "isSignedIn: ${supabase.auth.currentUserOrNull() != null}")
        return supabase.auth.currentUserOrNull() != null
    }

    fun getAuthToken(): String? {
        return supabase.auth.currentSessionOrNull()?.accessToken
    }

    fun signInAuthToken(token: String): Flow<AuthResponse> = flow {
        try {
            Log.e("Inui","Inininin ${isSignedIn()}")
            supabase.auth.signInWith(IDToken) {
                idToken = token
                provider = Google
            }
            emit(AuthResponse.Success)
        } catch (e: Exception) {
            Log.e("Itui","Itititit ${isSignedIn()}")
            emit(AuthResponse.Error(e.localizedMessage))
        }
    }

    fun signUpWithEmail(emailValue: String, passwordValue: String): Flow<AuthResponse> = flow {
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

    fun signInWithEmail(emailValue: String, passwordValue: String): Flow<AuthResponse> = flow {
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
        val payload = if(googleIdTokenCredential != null) googleIdTokenCredential?.idToken?.let { idToken ->
            val parts = idToken.split(".")
            if (parts.size == 3) {
                val payloadJson = Base64.decode(parts[1], Base64.URL_SAFE)
                val json = String(payloadJson)
                val regex = """"picture"\s*:\s*"([^"]+)"""".toRegex()
                regex.find(json)?.groups?.get(1)?.value
            } else null
        } else supabase.auth.currentSessionOrNull()?.accessToken?.let { idToken ->
            val parts = idToken.split(".")
            if (parts.size == 3) {
                val payloadJson = Base64.decode(parts[1], Base64.URL_SAFE)
                val json = String(payloadJson)
                val regex = """"picture"\s*:\s*"([^"]+)"""".toRegex()
                regex.find(json)?.groups?.get(1)?.value
            } else null
        }
        return payload
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