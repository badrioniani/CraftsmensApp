package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.data.auth.AuthState
import org.example.project.data.auth.AuthViewModel
import org.example.project.nav.Route
import org.example.project.nav.UserRole
import org.example.project.ui.screens.DetailScreen
import org.example.project.ui.screens.ForgotPasswordScreen
import org.example.project.ui.screens.LoginScreen
import org.example.project.ui.screens.MainShell
import org.example.project.ui.screens.MapScreen
import org.example.project.ui.screens.MechanicsListScreen
import org.example.project.ui.screens.MechanicDashboardScreen
import org.example.project.ui.screens.RegisterPickerScreen
import org.example.project.ui.screens.RegisterScreen
import org.example.project.ui.screens.ResetPasswordScreen
import org.example.project.ui.screens.ReviewsScreen
import org.example.project.ui.screens.ShopDetailScreen
import org.example.project.ui.screens.SplashScreen
import org.example.project.ui.screens.VerifyPhoneScreen
import org.example.project.ui.i18n.AppLanguage
import org.example.project.ui.i18n.LocalLanguage
import org.example.project.ui.i18n.LocalSetInputFocused
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.i18n.LocalToggleLanguage
import org.example.project.ui.i18n.stringsFor
import org.example.project.ui.theme.ACCENT_OPTIONS
import org.example.project.ui.theme.LocalCraftsmenColors
import org.example.project.ui.theme.buildTheme
import org.example.project.ui.util.rememberContactActions

@Composable
@Preview
fun App() {
    val accent = remember { ACCENT_OPTIONS.first().color }
    val dark = isSystemInDarkTheme()
    val theme = remember(dark, accent) { buildTheme(dark, accent) }

    var language by remember { mutableStateOf(AppLanguage.KA) }
    val strings = remember(language) { stringsFor(language) }
    val toggleLanguage: () -> Unit = {
        language = if (language == AppLanguage.EN) AppLanguage.KA else AppLanguage.EN
    }

    var inputFocused by remember { mutableStateOf(false) }
    val setInputFocused: (Boolean) -> Unit = { inputFocused = it }

    val authVm: AuthViewModel = viewModel { AuthViewModel() }
    val authState by authVm.state.collectAsStateWithLifecycle()
    val authBusy by authVm.busy.collectAsStateWithLifecycle()
    val authError by authVm.error.collectAsStateWithLifecycle()

    val stack = remember { mutableStateListOf<Route>(Route.Splash) }
    fun push(r: Route) { stack.add(r) }
    fun pop() { if (stack.size > 1) stack.removeAt(stack.lastIndex) }
    fun replace(r: Route) {
        if (stack.isNotEmpty()) stack.removeAt(stack.lastIndex)
        stack.add(r)
    }
    fun resetTo(r: Route) { stack.clear(); stack.add(r) }

    LaunchedEffect(authState) {
        val st = authState
        // Splash flips to either VerifyPhone (unverified phone), Home (verified), or Login.
        if (stack.last() is Route.Splash && st !is AuthState.Loading) {
            when (st) {
                is AuthState.Authenticated ->
                    resetTo(if (st.user.phoneVerified) Route.Home else Route.VerifyPhone)
                is AuthState.Anonymous -> resetTo(Route.Login)
                AuthState.Loading -> Unit
            }
        }
        // After login/register or a /me refresh, force unverified users onto the
        // VerifyPhone screen — mirrors the web's MobileVerificationGuard.
        if (st is AuthState.Authenticated && !st.user.phoneVerified) {
            val top = stack.last()
            val onAllowedScreen = top is Route.VerifyPhone ||
                top is Route.Login || top is Route.Register ||
                top is Route.RegisterPicker || top is Route.Splash
            if (!onAllowedScreen) resetTo(Route.VerifyPhone)
        }
    }

    MaterialTheme {
        CompositionLocalProvider(
            LocalCraftsmenColors provides theme,
            LocalStrings provides strings,
            LocalLanguage provides language,
            LocalToggleLanguage provides toggleLanguage,
            LocalSetInputFocused provides setInputFocused,
        ) {
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val contact = rememberContactActions()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(theme.bg)
                    // Shrink the visible area when the keyboard appears so the
                    // focused input scrolls into view above it instead of being
                    // hidden underneath. Works on Android and iOS.
                    .imePadding()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        })
                    },
            ) {
                when (val top = stack.last()) {
                    is Route.Splash -> SplashScreen(
                        theme = theme,
                        onDone = {
                            when (val st = authState) {
                                is AuthState.Authenticated ->
                                    resetTo(if (st.user.phoneVerified) Route.Home else Route.VerifyPhone)
                                is AuthState.Anonymous -> resetTo(Route.Login)
                                AuthState.Loading -> Unit
                            }
                        },
                    )
                    is Route.Login -> LoginScreen(
                        theme = theme,
                        onLogin = { email, password ->
                            authVm.login(email, password) { resetTo(Route.Home) }
                        },
                        onRegister = {
                            authVm.clearError()
                            push(Route.RegisterPicker)
                        },
                        onForgotPassword = {
                            authVm.clearError()
                            push(Route.ForgotPassword)
                        },
                        onGuest = {
                            authVm.clearError()
                            resetTo(Route.Home)
                        },
                        busy = authBusy,
                        errorText = authError,
                    )
                    is Route.ForgotPassword -> ForgotPasswordScreen(
                        theme = theme,
                        onBack = { authVm.clearError(); pop() },
                        onSendCode = { email ->
                            authVm.requestPasswordReset(email) { demoCode ->
                                replace(Route.ResetPassword(email = email, prefilledCode = demoCode))
                            }
                        },
                        busy = authBusy,
                        errorText = authError,
                    )
                    is Route.ResetPassword -> ResetPasswordScreen(
                        theme = theme,
                        email = top.email,
                        prefilledCode = top.prefilledCode,
                        onBack = { authVm.clearError(); pop() },
                        onConfirm = { email, code, newPassword ->
                            authVm.confirmPasswordReset(email, code, newPassword) {
                                resetTo(Route.Login)
                            }
                        },
                        busy = authBusy,
                        errorText = authError,
                    )
                    is Route.RegisterPicker -> RegisterPickerScreen(
                        theme = theme,
                        onBack = ::pop,
                        onPickRole = { role -> push(Route.Register(role)) },
                        onLogin = { authVm.clearError(); resetTo(Route.Login) },
                    )
                    is Route.Register -> RegisterScreen(
                        theme = theme,
                        role = top.role,
                        onBack = ::pop,
                        onSubmit = { form ->
                            authVm.register(
                                name = form.name,
                                email = form.email,
                                phone = form.phone,
                                password = form.password,
                                role = if (form.role == UserRole.Mechanic) "mechanic" else "user",
                            ) {
                                // New accounts always need SMS verification before
                                // anything else — the LaunchedEffect above also
                                // enforces this, but route explicitly so we don't
                                // flash the Home shell in between.
                                resetTo(Route.VerifyPhone)
                            }
                        },
                        onLogin = { authVm.clearError(); resetTo(Route.Login) },
                        busy = authBusy,
                        errorText = authError,
                    )
                    is Route.VerifyPhone -> VerifyPhoneScreen(
                        theme = theme,
                        authVm = authVm,
                        user = (authState as? AuthState.Authenticated)?.user,
                        onVerified = {
                            // After phone confirmation, mechanics drop into their
                            // dashboard like in the web's post-register flow.
                            resetTo(Route.Home)
                            val role = (authState as? AuthState.Authenticated)?.user?.role
                            if (role == "mechanic") push(Route.MechanicDashboard)
                        },
                        onLogout = {
                            authVm.logout()
                            resetTo(Route.Login)
                        },
                    )
                    is Route.Home -> MainShell(
                        theme = theme,
                        onPickMech = { m -> push(Route.MechanicDetail(m)) },
                        onBrowseCatalog = { push(Route.MechanicsList()) },
                        onPickShop = { sh -> push(Route.ShopDetail(sh)) },
                        currentUser = (authState as? AuthState.Authenticated)?.user,
                        onLogout = {
                            authVm.logout()
                            resetTo(Route.Login)
                        },
                        onOpenDashboard = { push(Route.MechanicDashboard) },
                        onSignIn = { authVm.clearError(); resetTo(Route.Login) },
                    )
                    is Route.MechanicsList -> MechanicsListScreen(
                        theme = theme,
                        initialQuery = top.initialQuery,
                        onBack = ::pop,
                        onPickMech = { m -> push(Route.MechanicDetail(m)) },
                        onMap = { push(Route.MapView) },
                    )
                    is Route.MapView -> MapScreen(
                        theme = theme,
                        onBack = ::pop,
                        onPickMech = { m -> push(Route.MechanicDetail(m)) },
                    )
                    is Route.MechanicDetail -> DetailScreen(
                        theme = theme,
                        mech = top.mech,
                        onBack = ::pop,
                        onCall = { contact.call(top.mech.phone) },
                        onWhatsapp = { contact.whatsapp(top.mech.whatsapp.ifBlank { top.mech.phone }) },
                        onDirections = { contact.directions("${top.mech.address}, ${top.mech.city}") },
                        onReviews = { push(Route.Reviews(top.mech)) },
                    )
                    is Route.Reviews -> ReviewsScreen(
                        theme = theme,
                        mech = top.mech,
                        onBack = ::pop,
                    )
                    is Route.ShopDetail -> ShopDetailScreen(
                        theme = theme,
                        shop = top.shop,
                        onBack = ::pop,
                        onCall = { contact.call(top.shop.phone) },
                        onWhatsapp = { contact.whatsapp(top.shop.whatsapp.ifBlank { top.shop.phone }) },
                        onDirections = { contact.directions("${top.shop.address}, ${top.shop.city}") },
                    )
                    is Route.MechanicDashboard -> MechanicDashboardScreen(
                        theme = theme,
                        user = (authState as? AuthState.Authenticated)?.user,
                        onBack = ::pop,
                        onViewPublic = { mech -> push(Route.MechanicDetail(mech)) },
                    )
                }

                AnimatedVisibility(
                    visible = inputFocused,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 48.dp, end = 24.dp),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(theme.accent)
                            .border(1.dp, theme.accent, CircleShape)
                            .clickable {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Text(
                            text = strings.done,
                            color = theme.accentText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}
