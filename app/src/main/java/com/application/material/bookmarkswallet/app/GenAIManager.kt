package com.application.material.bookmarkswallet.app

import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject
import javax.inject.Singleton

const val GEMINI_MODEL_NAME = "gemini-3.5-flash-lite"
@Singleton
class GenAIManager @Inject constructor() {
    val generativeModel = GenerativeModel(
        // The Gemini 1.5 models are versatile and work with most use cases
        modelName = GEMINI_MODEL_NAME,
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = BuildConfig.GEMINI_API_KEY
    )
}
