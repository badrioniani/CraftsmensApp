# --- Kotlin / Coroutines ---
-keepclassmembernames class kotlinx.** { volatile <fields>; }
-dontwarn kotlinx.coroutines.debug.**

# --- Kotlinx Serialization ---
# Generated $$serializer companions are accessed reflectively.
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class **$$serializer { *; }
-keepclassmembers class * {
    *** Companion;
}
-keepclasseswithmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}

# --- Ktor ---
-dontwarn io.ktor.**
-keep class io.ktor.** { *; }

# --- OkHttp / Okio (Android engine) ---
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn org.conscrypt.**
-keep class okhttp3.** { *; }

# --- Compose (preserve runtime reflection points) ---
-keep class androidx.compose.runtime.** { *; }
-dontwarn androidx.compose.**

# --- Multiplatform Settings (SharedPreferences-backed) ---
-keep class com.russhwolf.settings.** { *; }

# --- Google Play Services / Maps ---
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# --- App domain models referenced from JSON (de)serialization ---
-keep class org.example.project.data.** { *; }
-keepclassmembers class org.example.project.data.** {
    <fields>;
    <init>(...);
}
