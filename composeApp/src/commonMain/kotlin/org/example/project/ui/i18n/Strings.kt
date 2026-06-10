package org.example.project.ui.i18n

import androidx.compose.runtime.staticCompositionLocalOf

enum class AppLanguage(val code: String, val display: String) {
    EN("en", "EN"),
    KA("ka", "KA"),
}

interface Strings {
    val appName: String
    val brandTagline: String

    // Splash
    val splashTagline: String

    // Login
    val loginTitle: String
    val loginSubtitle: String
    val emailLabel: String
    val emailPlaceholder: String
    val passwordLabel: String
    val passwordPlaceholder: String
    val forgotPassword: String
    val loginAction: String

    // Forgot / reset password
    val forgotTitle: String
    val forgotSubtitle: String
    val forgotSendCodeAction: String
    val resetTitle: String
    val resetSubtitle: String
    val resetCodeLabel: String
    val resetCodePlaceholder: String
    val resetNewPasswordLabel: String
    val resetNewPasswordPlaceholder: String
    val resetConfirmAction: String
    val resetDemoNote: String
    val noAccountQuestion: String
    val registerCta: String
    val orDivider: String

    // Register picker
    val registerPickerTitle: String
    val registerPickerSubtitle: String
    val roleUserTitle: String
    val roleUserSubtitle: String
    val roleMechanicTitle: String
    val roleMechanicSubtitle: String

    // Register form
    val registerUserTitle: String
    val registerMechanicTitle: String
    val registerUserSubtitle: String
    val registerMechanicSubtitle: String
    val fullNameLabel: String
    val fullNamePlaceholder: String
    val phoneLabel: String
    val phonePlaceholder: String
    val phoneHint: String
    val phoneInvalid: String
    val confirmPasswordLabel: String
    val confirmPasswordPlaceholder: String
    val workshopNameLabel: String
    val workshopNamePlaceholder: String
    val specialtyLabel: String
    val specialtyPlaceholder: String
    val experienceLabel: String
    val experiencePlaceholder: String
    val cityLabel: String
    val cityPlaceholder: String
    val agreeTermsPrefix: String
    val agreeTermsLink: String
    val createAccountAction: String
    val haveAccountQuestion: String
    val loginCta: String
    val emailInvalid: String
    val passwordHint: String
    val pwStrengthWeak: String
    val pwStrengthFair: String
    val pwStrengthGood: String
    val pwStrengthStrong: String
    val registerTermsNotice: String

    // Guest mode
    val continueAsGuest: String
    val guestModeTitle: String
    val guestModeBody: String

    // Phone verification (OTP)
    val verifyPhoneTitle: String
    val verifyPhoneSubtitleSending: String
    val verifyPhoneSubtitleSent: String
    val verifyPhoneSubtitleFallback: String
    val verifyPhoneAction: String
    val verifyPhoneVerifying: String
    val verifyPhoneSuccessTitle: String
    val verifyPhoneSuccessBody: String
    val verifyPhoneResendQuestion: String
    val verifyPhoneResendAction: String
    val verifyPhoneResendSending: String
    val verifyPhoneResendSent: String

    // Mechanic dashboard
    val dashTitle: String
    val dashNotMechanic: String
    val dashLoginRequired: String
    val dashEditProfile: String
    val dashViewPublic: String
    val dashSaveProfile: String
    val dashSaving: String
    val dashAwaitingVerification: String
    val dashNoProfile: String
    val dashCreateProfileTitle: String
    val dashCreateProfileBody: String
    val dashStatRating: String
    val dashStatReviews: String
    val dashStatSpecs: String
    val dashStatStatus: String
    val dashSectionBusiness: String
    val dashSectionLocation: String
    val dashSectionServices: String
    val dashBusinessName: String
    val dashDescription: String
    val dashDescriptionPlaceholder: String
    val dashPhone: String
    val dashWhatsapp: String
    val dashCity: String
    val dashDistrict: String
    val dashAddress: String
    val dashSelectCity: String
    val dashSelectDistrict: String
    val dashLocationPin: String
    val dashUseMyLocation: String
    val dashClearPin: String
    val dashClickToPin: String
    val dashLocationNotSet: String
    val dashBrand: String
    val dashAllBrands: String
    val dashAllModels: String
    val dashAllServices: String
    val dashService: String
    val dashSpecsEmpty: String
    val dashCompleteness: String
    val dashTipsTitle: String
    val dashTip1: String
    val dashTip2: String
    val dashTip3: String
    val dashRecentReviews: String
    val dashReviewsAppearHere: String
    val dashProfileSaved: String
    val dashSaveError: String
    val dashIncompleteSpec: String
    val dashMissingBasics: String
    val dashMissingCity: String

    // Home (hero / featured)
    val homeEyebrow: String
    val homeTitleA: String
    val homeTitleB: String
    val homeSubtitle: String
    val homeBrowseCatalog: String
    val statMechanics: String
    val statBrands: String
    val statAvgRating: String
    val valueVerifiedTitle: String
    val valueVerifiedBody: String
    val valueReviewsTitle: String
    val valueReviewsBody: String
    val valueFeesTitle: String
    val valueFeesBody: String
    val featuredChip: String
    val featuredTitle: String
    val featuredSubtitle: String
    val featuredViewAll: String

    // Filters
    val filters: String
    val filtersReset: String
    val searchPlaceholder: String
    val anyBrand: String
    val anyService: String
    val anyCity: String
    val anyDistrict: String
    val anyRating: String
    val ratingStars: String
    val verifiedOnly: String
    val applyFilters: String

    // Catalog
    val catalogTitle: String
    val catalogSubtitle: String
    val catalogEmptyTitle: String
    val catalogEmptyBody: String
    val sortNearest: String
    val sortRating: String

    // Card
    val cardCall: String
    val cardWhatsapp: String
    val cardViewProfile: String
    val openNow: String
    val closed: String
    val expSuffix: String

    // Verified
    val badgeVerified: String
    val badgeUnverified: String
    val badgeVip: String
    val badgeSuperVip: String
    val distanceKmSuffix: String
    val distanceMSuffix: String

    // Detail
    val about: String
    val specialties: String
    val brandsServiced: String
    val contactLocation: String
    val recentReviews: String
    fun seeAll(n: Int): String
    val statRating: String
    val statTrade: String
    val statKm: String
    val contactPhone: String
    val contactWhatsapp: String
    val contactAddress: String
    val contactHours: String
    val openInMaps: String
    val directions: String
    val detailReviewsCount: String

    // Reviews
    val reviewsTitle: String

    // Map
    val nearby: String
    val mapViewList: String
    val mapViewMap: String

    // Shops
    val shopsTitle: String
    val shopsSubtitle: String
    val shopsEmptyTitle: String
    val shopsEmptyBody: String
    val shopCategory: String
    val shopAnyCategory: String
    val shopVisitWebsite: String
    fun shopCategoryName(id: String): String

    // Garage
    val garageTitle: String
    val garageSubtitle: String
    val garageAdd: String
    val garageEmptyTitle: String
    val garageEmptyBody: String
    val garageBrand: String
    val garageModel: String
    val garageYear: String
    val garageNickname: String
    val garageNicknamePlaceholder: String
    val garageSave: String
    val garageDelete: String
    val garageLoginRequired: String

    val done: String
    val cancel: String

    // Bottom nav
    val tabHome: String
    val tabShops: String
    val tabGarage: String
    val tabProfile: String

    // Profile / Settings
    val profileTitle: String
    val profileAccountSection: String
    val profileRoleLabel: String
    val profileLogoutAction: String
    val profileLogoutConfirmTitle: String
    val profileLogoutConfirmMessage: String
    fun profileRole(role: String): String

    // Specialty names
    fun specialtyName(id: String): String

    // Country names
    fun country(name: String): String
}

object EnglishStrings : Strings {
    override val appName = "AutoRepair"
    override val brandTagline = "Trusted Georgian mechanics, in one place"

    override val splashTagline = "Trusted Georgian mechanics, in one place"

    override val loginTitle = "Welcome back"
    override val loginSubtitle = "Sign in to manage reviews and your saved garage."
    override val emailLabel = "Email"
    override val emailPlaceholder = "you@example.com"
    override val passwordLabel = "Password"
    override val passwordPlaceholder = "Enter your password"
    override val forgotPassword = "Forgot password?"
    override val loginAction = "Sign in"

    override val forgotTitle = "Reset password"
    override val forgotSubtitle = "Enter your email and we'll send a code to reset your password."
    override val forgotSendCodeAction = "Send code"
    override val resetTitle = "Enter your code"
    override val resetSubtitle = "Type the 6-digit code and pick a new password."
    override val resetCodeLabel = "Reset code"
    override val resetCodePlaceholder = "123456"
    override val resetNewPasswordLabel = "New password"
    override val resetNewPasswordPlaceholder = "At least 8 characters"
    override val resetConfirmAction = "Reset password"
    override val resetDemoNote = "Demo mode: code prefilled from server."
    override val noAccountQuestion = "New here?"
    override val registerCta = "Create account"
    override val orDivider = "OR"

    override val registerPickerTitle = "Join AutoRepair"
    override val registerPickerSubtitle = "Pick the account type that fits you"
    override val roleUserTitle = "I'm a car owner"
    override val roleUserSubtitle = "Find and review trusted mechanics"
    override val roleMechanicTitle = "I'm a mechanic"
    override val roleMechanicSubtitle = "List my business in the catalog"

    override val registerUserTitle = "Create user account"
    override val registerMechanicTitle = "Create mechanic account"
    override val registerUserSubtitle = "Browse the catalog and save your garage"
    override val registerMechanicSubtitle = "Set up your workshop profile"
    override val fullNameLabel = "Full name"
    override val fullNamePlaceholder = "Your name"
    override val phoneLabel = "Phone"
    override val phonePlaceholder = "+995 5XX XXX XXX"
    override val phoneHint = "Georgian mobile only — 9 digits starting with 5. We'll send an SMS code."
    override val phoneInvalid = "Enter a valid Georgian mobile (e.g. +995 555 123 456)."
    override val confirmPasswordLabel = "Confirm password"
    override val confirmPasswordPlaceholder = "Re-enter your password"
    override val workshopNameLabel = "Workshop name"
    override val workshopNamePlaceholder = "E.g. East Side Auto"
    override val specialtyLabel = "Main specialty"
    override val specialtyPlaceholder = "E.g. Engine, Brakes, EV"
    override val experienceLabel = "Years of experience"
    override val experiencePlaceholder = "5"
    override val cityLabel = "City"
    override val cityPlaceholder = "E.g. Tbilisi"
    override val agreeTermsPrefix = "I agree to the"
    override val agreeTermsLink = "Terms & Privacy"
    override val createAccountAction = "Create account"
    override val haveAccountQuestion = "Already have an account?"
    override val loginCta = "Sign in"
    override val emailInvalid = "Looks like that email isn't valid."
    override val passwordHint = "At least 8 characters."
    override val pwStrengthWeak = "Weak — add more length and variety."
    override val pwStrengthFair = "Fair — try mixing letters, numbers, and a symbol."
    override val pwStrengthGood = "Good — solid choice."
    override val pwStrengthStrong = "Strong — nice work."
    override val registerTermsNotice =
        "By creating an account you agree to our terms and accept that this is a demo project."

    override val continueAsGuest = "Continue as guest"
    override val guestModeTitle = "You're browsing as a guest"
    override val guestModeBody =
        "Sign in or create an account to save your garage, leave reviews, and manage your profile."

    override val verifyPhoneTitle = "Verify your phone"
    override val verifyPhoneSubtitleSending = "Sending SMS..."
    override val verifyPhoneSubtitleSent = "6-digit code sent to"
    override val verifyPhoneSubtitleFallback = "Enter the SMS code"
    override val verifyPhoneAction = "Verify"
    override val verifyPhoneVerifying = "Verifying..."
    override val verifyPhoneSuccessTitle = "Phone verified!"
    override val verifyPhoneSuccessBody = "Taking you to the home screen..."
    override val verifyPhoneResendQuestion = "Didn't get the code?"
    override val verifyPhoneResendAction = "Resend code"
    override val verifyPhoneResendSending = "Sending..."
    override val verifyPhoneResendSent = "Sent ✓"

    override val dashTitle = "Mechanic dashboard"
    override val dashNotMechanic = "This dashboard is available for mechanic accounts."
    override val dashLoginRequired = "Login is required for the mechanic dashboard."
    override val dashEditProfile = "Edit profile"
    override val dashViewPublic = "View public profile"
    override val dashSaveProfile = "Save profile"
    override val dashSaving = "Saving…"
    override val dashAwaitingVerification = "Awaiting verification"
    override val dashNoProfile = "No mechanic profile yet."
    override val dashCreateProfileTitle = "No profile yet"
    override val dashCreateProfileBody = "Fill in your profile so you show up in the catalog."
    override val dashStatRating = "Rating"
    override val dashStatReviews = "Reviews"
    override val dashStatSpecs = "Specializations"
    override val dashStatStatus = "Status"
    override val dashSectionBusiness = "Business"
    override val dashSectionLocation = "Location"
    override val dashSectionServices = "Services & vehicles"
    override val dashBusinessName = "Business name"
    override val dashDescription = "Description"
    override val dashDescriptionPlaceholder =
        "Tell customers what your shop is great at — brands, hours, anything that builds trust."
    override val dashPhone = "Phone"
    override val dashWhatsapp = "WhatsApp"
    override val dashCity = "City"
    override val dashDistrict = "District"
    override val dashAddress = "Address"
    override val dashSelectCity = "Select city…"
    override val dashSelectDistrict = "Select district"
    override val dashLocationPin = "Pin your location on the map"
    override val dashUseMyLocation = "Use my location"
    override val dashClearPin = "Clear pin"
    override val dashClickToPin = "Tap the map to drop a pin"
    override val dashLocationNotSet = "Location pin not set yet."
    override val dashBrand = "Brand"
    override val dashAllBrands = "All brands"
    override val dashAllModels = "All models"
    override val dashAllServices = "All services"
    override val dashService = "Service"
    override val dashSpecsEmpty = "No specializations yet. Add at least one so customers can find you."
    override val dashCompleteness = "Profile completeness"
    override val dashTipsTitle = "Tips for a strong profile"
    override val dashTip1 = "Add a clear description — what makes your shop different?"
    override val dashTip2 = "Pin your exact location so customers can find you."
    override val dashTip3 = "List every brand you work on to maximise matches."
    override val dashRecentReviews = "Recent reviews"
    override val dashReviewsAppearHere = "Reviews will appear here."
    override val dashProfileSaved = "Profile saved."
    override val dashSaveError = "Could not save profile. Check required fields and specialization rows."
    override val dashIncompleteSpec = "Each specialization needs both a brand and a service. Fix or remove the highlighted rows."
    override val dashMissingBasics = "Workshop name and phone are required."
    override val dashMissingCity = "Please pick a city."

    override val homeEyebrow = "Trusted Georgian mechanics, in one place"
    override val homeTitleA = "Fix your car with a mechanic"
    override val homeTitleB = "you can trust."
    override val homeSubtitle = "Search by brand, model, service, district, and rating. Verified profiles only — or open it up to everyone with one tap."
    override val homeBrowseCatalog = "Browse catalog"
    override val statMechanics = "MECHANICS"
    override val statBrands = "BRANDS"
    override val statAvgRating = "AVG. RATING"
    override val valueVerifiedTitle = "Verified profiles"
    override val valueVerifiedBody = "Admins manually verify mechanic businesses before they get the green badge."
    override val valueReviewsTitle = "Real reviews"
    override val valueReviewsBody = "One review per customer per mechanic. Ratings update in real time."
    override val valueFeesTitle = "No hidden fees"
    override val valueFeesBody = "Browsing the catalog is free, forever. Mechanics keep 100% of every job."
    override val featuredChip = "FEATURED"
    override val featuredTitle = "Top verified mechanics"
    override val featuredSubtitle = "Filtered by Tbilisi."
    override val featuredViewAll = "View all"

    override val filters = "Filters"
    override val filtersReset = "Reset"
    override val searchPlaceholder = "Search by name, brand, service, city…"
    override val anyBrand = "Any brand"
    override val anyService = "Any service"
    override val anyCity = "Any city"
    override val anyDistrict = "Any district"
    override val anyRating = "Any"
    override val ratingStars = "+ stars"
    override val verifiedOnly = "Verified only"
    override val applyFilters = "Apply filters"

    override val catalogTitle = "Mechanic catalog"
    override val catalogSubtitle = "matching your filters"
    override val catalogEmptyTitle = "No mechanics found"
    override val catalogEmptyBody = "Adjust the filters or clear the model field for broader brand-level matches."
    override val sortNearest = "Nearest"
    override val sortRating = "Top rated"

    override val cardCall = "Call"
    override val cardWhatsapp = "WhatsApp"
    override val cardViewProfile = "View profile"
    override val openNow = "Open now"
    override val closed = "Closed"
    override val expSuffix = "y exp"

    override val badgeVerified = "Verified"
    override val badgeUnverified = "Unverified"
    override val badgeVip = "VIP"
    override val badgeSuperVip = "SUPER VIP"
    override val distanceKmSuffix = "km"
    override val distanceMSuffix = "m"

    override val about = "About"
    override val specialties = "Specialties"
    override val brandsServiced = "Brands serviced"
    override val contactLocation = "Contact & location"
    override val recentReviews = "Recent reviews"
    override fun seeAll(n: Int) = "See all ($n)"
    override val statRating = "RATING"
    override val statTrade = "IN TRADE"
    override val statKm = "KILOMETERS"
    override val contactPhone = "Phone"
    override val contactWhatsapp = "WhatsApp"
    override val contactAddress = "Address"
    override val contactHours = "Hours"
    override val openInMaps = "Open in maps"
    override val directions = "Directions"
    override val detailReviewsCount = "reviews"

    override val reviewsTitle = "Reviews"

    override val nearby = "NEARBY"
    override val mapViewList = "List"
    override val mapViewMap = "Map"

    override val shopsTitle = "Car Parts Shops"
    override val shopsSubtitle = "Find spare parts, tires, accessories and more."
    override val shopsEmptyTitle = "No shops found"
    override val shopsEmptyBody = "Try a different category or city."
    override val shopCategory = "Category"
    override val shopAnyCategory = "Any category"
    override val shopVisitWebsite = "Visit website"
    override fun shopCategoryName(id: String) = when (id) {
        "spare_parts" -> "Spare parts"
        "tires" -> "Tires"
        "electronics" -> "Electronics"
        "accessories" -> "Accessories"
        "lubricants" -> "Lubricants"
        "tools" -> "Tools"
        else -> "Other"
    }

    override val garageTitle = "My Garage"
    override val garageSubtitle = "Save your cars to personalise the catalog and profile pages."
    override val garageAdd = "Add a vehicle"
    override val garageEmptyTitle = "No saved vehicles yet"
    override val garageEmptyBody = "Add your car so we can pre-filter the catalog and highlight mechanics who work on it."
    override val garageBrand = "Brand"
    override val garageModel = "Model"
    override val garageYear = "Year"
    override val garageNickname = "Nickname"
    override val garageNicknamePlaceholder = "e.g. Daily driver"
    override val garageSave = "Save vehicle"
    override val garageDelete = "Delete"
    override val garageLoginRequired = "Login to manage your saved vehicles."

    override val done = "Done"
    override val cancel = "Cancel"

    override val tabHome = "Home"
    override val tabShops = "Shops"
    override val tabGarage = "Garage"
    override val tabProfile = "Profile"

    override val profileTitle = "Profile"
    override val profileAccountSection = "Account"
    override val profileRoleLabel = "Role"
    override val profileLogoutAction = "Sign out"
    override val profileLogoutConfirmTitle = "Sign out?"
    override val profileLogoutConfirmMessage = "You'll need to sign in again to use the app."
    override fun profileRole(role: String) = when (role) {
        "user" -> "Customer"
        "mechanic" -> "Mechanic"
        "admin" -> "Admin"
        else -> role.replaceFirstChar { it.uppercase() }
    }

    override fun specialtyName(id: String) = when (id) {
        "engine" -> "Engine"
        "gearbox" -> "Transmission"
        "electrical" -> "Electrical"
        "radiator" -> "Cooling"
        "brakes" -> "Brakes"
        "suspension" -> "Suspension"
        "exhaust" -> "Exhaust"
        "aircon" -> "A/C & Heating"
        "bodywork" -> "Bodywork"
        "tires" -> "Tires & Wheels"
        "diagnostic" -> "Diagnostics"
        "ev" -> "EV Specialist"
        else -> id
    }

    override fun country(name: String) = name
}

object GeorgianStrings : Strings {
    override val appName = "AutoRepair"
    override val brandTagline = "სანდო ქართველი მექანიკოსები ერთ სივრცეში"

    override val splashTagline = "სანდო ქართველი მექანიკოსები ერთ სივრცეში"

    override val loginTitle = "კეთილი იყოს დაბრუნება"
    override val loginSubtitle = "შედი, რომ მართო შეფასებები და გარაჟი."
    override val emailLabel = "ელფოსტა"
    override val emailPlaceholder = "you@example.com"
    override val passwordLabel = "პაროლი"
    override val passwordPlaceholder = "შეიყვანე პაროლი"
    override val forgotPassword = "დაგავიწყდა პაროლი?"
    override val loginAction = "შესვლა"

    override val forgotTitle = "პაროლის აღდგენა"
    override val forgotSubtitle = "შეიყვანე ელფოსტა და გამოგიგზავნით კოდს."
    override val forgotSendCodeAction = "კოდის გაგზავნა"
    override val resetTitle = "შეიყვანე კოდი"
    override val resetSubtitle = "ჩაწერე 6-ციფრიანი კოდი და აირჩიე ახალი პაროლი."
    override val resetCodeLabel = "კოდი"
    override val resetCodePlaceholder = "123456"
    override val resetNewPasswordLabel = "ახალი პაროლი"
    override val resetNewPasswordPlaceholder = "მინიმუმ 8 სიმბოლო"
    override val resetConfirmAction = "პაროლის აღდგენა"
    override val resetDemoNote = "დემო რეჟიმი: კოდი წინასწარ ჩაწერილია."
    override val noAccountQuestion = "ახალი ხარ აქ?"
    override val registerCta = "ანგარიშის შექმნა"
    override val orDivider = "ან"

    override val registerPickerTitle = "შემოუერთდი AutoRepair-ს"
    override val registerPickerSubtitle = "აირჩიე ანგარიშის ტიპი"
    override val roleUserTitle = "ვარ მანქანის მფლობელი"
    override val roleUserSubtitle = "იპოვე და შეაფასე სანდო მექანიკოსები"
    override val roleMechanicTitle = "ვარ მექანიკოსი"
    override val roleMechanicSubtitle = "დაარეგისტრირე ბიზნესი კატალოგში"

    override val registerUserTitle = "მომხმარებლის ანგარიში"
    override val registerMechanicTitle = "მექანიკოსის ანგარიში"
    override val registerUserSubtitle = "დაათვალიერე კატალოგი და შეინახე გარაჟი"
    override val registerMechanicSubtitle = "დაამატე შენი სახელოსნოს დეტალები"
    override val fullNameLabel = "სახელი და გვარი"
    override val fullNamePlaceholder = "შენი სახელი"
    override val phoneLabel = "ტელეფონი"
    override val phonePlaceholder = "+995 5XX XXX XXX"
    override val phoneHint = "მხოლოდ ქართული მობილური — 9 ციფრი, იწყება 5-ით. SMS კოდი მოვა."
    override val phoneInvalid = "შეიყვანე სწორი ქართული მობილური (მაგ: +995 555 123 456)."
    override val confirmPasswordLabel = "გაიმეორე პაროლი"
    override val confirmPasswordPlaceholder = "შეიყვანე ისევ პაროლი"
    override val workshopNameLabel = "სახელოსნოს დასახელება"
    override val workshopNamePlaceholder = "მაგ. East Side Auto"
    override val specialtyLabel = "ძირითადი სპეციალობა"
    override val specialtyPlaceholder = "მაგ. ძრავი, მუხრუჭები, EV"
    override val experienceLabel = "გამოცდილება (წელი)"
    override val experiencePlaceholder = "5"
    override val cityLabel = "ქალაქი"
    override val cityPlaceholder = "მაგ. თბილისი"
    override val agreeTermsPrefix = "ვეთანხმები"
    override val agreeTermsLink = "პირობებსა და კონფიდენციალურობას"
    override val createAccountAction = "ანგარიშის შექმნა"
    override val haveAccountQuestion = "უკვე გაქვს ანგარიში?"
    override val loginCta = "შესვლა"
    override val emailInvalid = "ელფოსტა არასწორი ფორმატის ჩანს."
    override val passwordHint = "მინიმუმ 8 სიმბოლო."
    override val pwStrengthWeak = "სუსტი — დაამატე სიგრძე და მრავალფეროვნება."
    override val pwStrengthFair = "საშუალო — შეურიე ასოები, ციფრები და სიმბოლო."
    override val pwStrengthGood = "კარგი — საიმედო არჩევანი."
    override val pwStrengthStrong = "ძლიერი — მშვენიერია."
    override val registerTermsNotice =
        "ანგარიშის შექმნით ეთანხმები პირობებს და აცნობიერებ, რომ ეს სადემონსტრაციო პროექტია."

    override val continueAsGuest = "გაგრძელება სტუმრად"
    override val guestModeTitle = "ათვალიერებ როგორც სტუმარი"
    override val guestModeBody =
        "შედი ან შექმენი ანგარიში, რომ შეინახო გარაჟი, დატოვო შეფასებები და მართო პროფილი."

    override val verifyPhoneTitle = "ტელეფონის დადასტურება"
    override val verifyPhoneSubtitleSending = "SMS იგზავნება..."
    override val verifyPhoneSubtitleSent = "6-ნიშნა კოდი გამოგზავნილია"
    override val verifyPhoneSubtitleFallback = "შეიყვანე SMS კოდი"
    override val verifyPhoneAction = "დადასტურება"
    override val verifyPhoneVerifying = "მოწმდება..."
    override val verifyPhoneSuccessTitle = "ტელეფონი დადასტურდა!"
    override val verifyPhoneSuccessBody = "გადადიხართ მთავარ გვერდზე..."
    override val verifyPhoneResendQuestion = "კოდი არ მიგიღია?"
    override val verifyPhoneResendAction = "კოდის ხელახლა გაგზავნა"
    override val verifyPhoneResendSending = "იგზავნება..."
    override val verifyPhoneResendSent = "გამოგზავნილია ✓"

    override val dashTitle = "მექანიკოსის პანელი"
    override val dashNotMechanic = "ეს პანელი ხელმისაწვდომია მხოლოდ მექანიკოსის ანგარიშებისთვის."
    override val dashLoginRequired = "მექანიკოსის პანელისთვის საჭიროა ავტორიზაცია."
    override val dashEditProfile = "პროფილის რედაქტირება"
    override val dashViewPublic = "საჯარო პროფილის ნახვა"
    override val dashSaveProfile = "პროფილის შენახვა"
    override val dashSaving = "ინახება…"
    override val dashAwaitingVerification = "ველოდებით ვერიფიკაციას"
    override val dashNoProfile = "მექანიკოსის პროფილი ჯერ არ არის."
    override val dashCreateProfileTitle = "პროფილი ჯერ არ შექმნილა"
    override val dashCreateProfileBody = "შეავსე პროფილი და გამოჩნდები კატალოგში."
    override val dashStatRating = "რეიტინგი"
    override val dashStatReviews = "შეფასებები"
    override val dashStatSpecs = "სპეციალიზაცია"
    override val dashStatStatus = "სტატუსი"
    override val dashSectionBusiness = "ბიზნესი"
    override val dashSectionLocation = "მდებარეობა"
    override val dashSectionServices = "სერვისები და მანქანები"
    override val dashBusinessName = "ბიზნესის სახელი"
    override val dashDescription = "აღწერა"
    override val dashDescriptionPlaceholder =
        "უამბე მომხმარებელს რა გამოგდის საუკეთესოდ — ბრენდები, საათები, ყველაფერი რაც ნდობას აწვება."
    override val dashPhone = "ტელეფონი"
    override val dashWhatsapp = "WhatsApp"
    override val dashCity = "ქალაქი"
    override val dashDistrict = "უბანი"
    override val dashAddress = "მისამართი"
    override val dashSelectCity = "აირჩიე ქალაქი…"
    override val dashSelectDistrict = "აირჩიე უბანი"
    override val dashLocationPin = "მონიშნე შენი მდებარეობა რუკაზე"
    override val dashUseMyLocation = "ჩემი ლოკაცია"
    override val dashClearPin = "პინის წაშლა"
    override val dashClickToPin = "დააკლიკე რუკაზე პინის დასადებად"
    override val dashLocationNotSet = "მდებარეობის პინი ჯერ არ არის მონიშნული."
    override val dashBrand = "ბრენდი"
    override val dashAllBrands = "ყველა ბრენდი"
    override val dashAllModels = "ყველა მოდელი"
    override val dashAllServices = "ყველა სერვისი"
    override val dashService = "სერვისი"
    override val dashSpecsEmpty = "ჯერ არ არის სპეციალიზაციები. დაამატე ერთი მაინც."
    override val dashCompleteness = "პროფილის შევსებულობა"
    override val dashTipsTitle = "რჩევები ძლიერი პროფილისთვის"
    override val dashTip1 = "დაამატე ნათელი აღწერა — რით განსხვავდები სხვებისგან?"
    override val dashTip2 = "მონიშნე ზუსტი მდებარეობა, რომ მომხმარებელმა გიპოვოს."
    override val dashTip3 = "ჩამოწერე ყველა ბრენდი რომელზეც მუშაობ."
    override val dashRecentReviews = "ბოლო შეფასებები"
    override val dashReviewsAppearHere = "შეფასებები აქ გამოჩნდება."
    override val dashProfileSaved = "პროფილი შენახულია."
    override val dashSaveError = "პროფილის შენახვა ვერ მოხერხდა. შეამოწმე სავალდებულო ველები და სპეციალიზაციები."
    override val dashIncompleteSpec = "ყოველ სპეციალიზაციას სჭირდება ბრენდიც და სერვისიც. შეასწორე ან წაშალე მონიშნული სტრიქონები."
    override val dashMissingBasics = "სახელოსნოს დასახელება და ტელეფონი სავალდებულოა."
    override val dashMissingCity = "გთხოვ აირჩიო ქალაქი."

    override val homeEyebrow = "სანდო ქართველი მექანიკოსები ერთ სივრცეში"
    override val homeTitleA = "შეაკეთე მანქანა მექანიკოსთან,"
    override val homeTitleB = "რომელსაც ენდობი."
    override val homeSubtitle = "მოძებნე ბრენდის, მოდელის, სერვისის, უბნისა და რეიტინგის მიხედვით."
    override val homeBrowseCatalog = "კატალოგი"
    override val statMechanics = "მექანიკოსი"
    override val statBrands = "ბრენდი"
    override val statAvgRating = "საშ. რეიტინგი"
    override val valueVerifiedTitle = "ვერიფიცირებული პროფილები"
    override val valueVerifiedBody = "ადმინი ხელით ამოწმებს მექანიკოსებს მწვანე ნიშნის მინიჭებამდე."
    override val valueReviewsTitle = "ნამდვილი შეფასებები"
    override val valueReviewsBody = "ერთი შეფასება ერთ მექანიკოსზე ერთ მომხმარებელზე."
    override val valueFeesTitle = "ფარული გადასახადების გარეშე"
    override val valueFeesBody = "კატალოგი სამუდამოდ უფასოა. მექანიკოსი იღებს თანხის 100%-ს."
    override val featuredChip = "რჩეული"
    override val featuredTitle = "ტოპ ვერიფიცირებული მექანიკოსები"
    override val featuredSubtitle = "ფილტრი: თბილისი."
    override val featuredViewAll = "ყველას ნახვა"

    override val filters = "ფილტრები"
    override val filtersReset = "გასუფთავება"
    override val searchPlaceholder = "ძებნა სახელით, ბრენდით, სერვისით, ქალაქით…"
    override val anyBrand = "ნებისმიერი ბრენდი"
    override val anyService = "ნებისმიერი სერვისი"
    override val anyCity = "ნებისმიერი ქალაქი"
    override val anyDistrict = "ნებისმიერი უბანი"
    override val anyRating = "ნებისმიერი"
    override val ratingStars = "+ ვარსკვლავი"
    override val verifiedOnly = "მხოლოდ ვერიფიცირებული"
    override val applyFilters = "გამოყენება"

    override val catalogTitle = "მექანიკოსების კატალოგი"
    override val catalogSubtitle = "ფილტრის შესაბამისად"
    override val catalogEmptyTitle = "მექანიკოსები ვერ მოიძებნა"
    override val catalogEmptyBody = "შეცვალე ფილტრები ან გაასუფთავე მოდელის ველი."
    override val sortNearest = "უახლოესი"
    override val sortRating = "ტოპ რეიტინგი"

    override val cardCall = "ზარი"
    override val cardWhatsapp = "WhatsApp"
    override val cardViewProfile = "პროფილი"
    override val openNow = "ღიაა"
    override val closed = "დახურულია"
    override val expSuffix = "წ. გამოც."

    override val badgeVerified = "ვერიფიცირებული"
    override val badgeUnverified = "არავერიფიცირებული"
    override val badgeVip = "VIP"
    override val badgeSuperVip = "SUPER VIP"
    override val distanceKmSuffix = "კმ"
    override val distanceMSuffix = "მ"

    override val about = "შესახებ"
    override val specialties = "სპეციალობები"
    override val brandsServiced = "მომსახურე ბრენდები"
    override val contactLocation = "კონტაქტი და მისამართი"
    override val recentReviews = "ბოლო შეფასებები"
    override fun seeAll(n: Int) = "ნახე ყველა ($n)"
    override val statRating = "რეიტინგი"
    override val statTrade = "გამოცდილება"
    override val statKm = "კმ"
    override val contactPhone = "ტელეფონი"
    override val contactWhatsapp = "WhatsApp"
    override val contactAddress = "მისამართი"
    override val contactHours = "სამუშაო საათები"
    override val openInMaps = "რუკაზე გახსნა"
    override val directions = "მიმართულებები"
    override val detailReviewsCount = "შეფასება"

    override val reviewsTitle = "შეფასებები"

    override val nearby = "ახლოს"
    override val mapViewList = "სია"
    override val mapViewMap = "რუკა"

    override val shopsTitle = "სათადარიგო ნაწილების მაღაზიები"
    override val shopsSubtitle = "იპოვე ნაწილები, საბურავები, აქსესუარები და სხვა."
    override val shopsEmptyTitle = "მაღაზია ვერ მოიძებნა"
    override val shopsEmptyBody = "სცადე სხვა კატეგორია ან ქალაქი."
    override val shopCategory = "კატეგორია"
    override val shopAnyCategory = "ნებისმიერი"
    override val shopVisitWebsite = "ვებსაიტი"
    override fun shopCategoryName(id: String) = when (id) {
        "spare_parts" -> "სათადარიგო ნაწილები"
        "tires" -> "საბურავები"
        "electronics" -> "ელექტრონიკა"
        "accessories" -> "აქსესუარები"
        "lubricants" -> "ზეთი / სმაზე"
        "tools" -> "ინსტრუმენტები"
        else -> "სხვა"
    }

    override val garageTitle = "ჩემი გარაჟი"
    override val garageSubtitle = "შეინახე მანქანები და კატალოგი თავად მოგერგება."
    override val garageAdd = "მანქანის დამატება"
    override val garageEmptyTitle = "შენახული მანქანები ჯერ არ არის"
    override val garageEmptyBody = "დაამატე შენი მანქანა, რომ კატალოგი თავად მოგერგოს."
    override val garageBrand = "ბრენდი"
    override val garageModel = "მოდელი"
    override val garageYear = "წელი"
    override val garageNickname = "მეტსახელი"
    override val garageNicknamePlaceholder = "მაგ. ყოველდღიური"
    override val garageSave = "შენახვა"
    override val garageDelete = "წაშლა"
    override val garageLoginRequired = "შესვლა საჭიროა გარაჟის სამართავად."

    override val done = "მზადაა"
    override val cancel = "გაუქმება"

    override val tabHome = "მთავარი"
    override val tabShops = "მაღაზიები"
    override val tabGarage = "გარაჟი"
    override val tabProfile = "პროფილი"

    override val profileTitle = "პროფილი"
    override val profileAccountSection = "ანგარიში"
    override val profileRoleLabel = "როლი"
    override val profileLogoutAction = "გასვლა"
    override val profileLogoutConfirmTitle = "გავიდე ანგარიშიდან?"
    override val profileLogoutConfirmMessage = "აპლიკაციის გამოყენებისთვის ხელახლა შესვლა მოგიწევს."
    override fun profileRole(role: String) = when (role) {
        "user" -> "მომხმარებელი"
        "mechanic" -> "მექანიკოსი"
        "admin" -> "ადმინი"
        else -> role
    }

    override fun specialtyName(id: String) = when (id) {
        "engine" -> "ძრავი"
        "gearbox" -> "ტრანსმისია"
        "electrical" -> "ელექტრო"
        "radiator" -> "გაგრილება"
        "brakes" -> "მუხრუჭები"
        "suspension" -> "საკიდი"
        "exhaust" -> "გამონაბოლქვი"
        "aircon" -> "კონდიც. და გათბობა"
        "bodywork" -> "ძარის სამუშაო"
        "tires" -> "საბურავები და დისკები"
        "diagnostic" -> "დიაგნოსტიკა"
        "ev" -> "ელექტრომობილი"
        else -> id
    }

    override fun country(name: String) = when (name) {
        "Sweden" -> "შვედეთი"
        "Italy" -> "იტალია"
        "USA" -> "აშშ"
        "Japan" -> "იაპონია"
        "Germany" -> "გერმანია"
        "France" -> "საფრანგეთი"
        "UK" -> "დიდი ბრიტანეთი"
        "Korea" -> "კორეა"
        "Spain" -> "ესპანეთი"
        else -> name
    }
}

fun stringsFor(lang: AppLanguage): Strings = when (lang) {
    AppLanguage.EN -> EnglishStrings
    AppLanguage.KA -> GeorgianStrings
}

val LocalStrings = staticCompositionLocalOf<Strings> { EnglishStrings }
val LocalLanguage = staticCompositionLocalOf { AppLanguage.KA }
val LocalToggleLanguage = staticCompositionLocalOf<() -> Unit> { {} }


val LocalSetInputFocused = staticCompositionLocalOf<(Boolean) -> Unit> { {} }
