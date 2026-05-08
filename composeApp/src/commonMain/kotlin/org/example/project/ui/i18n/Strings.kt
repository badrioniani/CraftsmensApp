package org.example.project.ui.i18n

import androidx.compose.runtime.staticCompositionLocalOf

enum class AppLanguage(val code: String, val display: String) {
    EN("en", "EN"),
    KA("ka", "KA"),
}

interface Strings {
    val appName: String
    val brandTagline: String
    val location: String

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

    // Home
    val homeTitle1: String
    val homeTitle2: String
    val searchPlaceholder: String
    val pickBrand: String
    val dontSeeBrand: String
    val addManually: String

    // Specialty
    val specQuestion: String
    val forYour: String
    val estPrefix: String
    val change: String
    val pickSpecialty: String
    val selectSpecialty: String
    val jobsDone: String
    fun findCraftsmenFor(spec: String): String

    // List
    val filters: String
    val sortNearest: String
    val sortRating: String
    val sortExperience: String
    fun fallbackNoBrand(brand: String, spec: String): String
    val noMatch: String
    val resetFilters: String
    val openNow: String
    val closed: String
    val craftsmenSuffix: String
    val expSuffix: String

    // Filter sheet
    val minRating: String
    val yearsExp: String
    fun maxDistanceLabel(km: Int): String
    val maxPrice: String
    val availableToday: String
    val reset: String
    val applyFilters: String
    val any: String
    fun yrsLabel(n: Int): String

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
    val bookAppointment: String

    // Reviews
    val reviewsTitle: String
    val reviewsSuffix: String

    // Map
    val nearby: String

    // Book
    val bookSlot: String
    val diagnosticSuffix: String
    val pickDate: String
    val pickTime: String
    val describeIssue: String
    val issuePlaceholder: String
    fun confirmAt(day: String, time: String): String
    val pickTimeSlot: String

    // Confirm
    val booked: String
    fun bookedSubtitle(name: String): String
    val confirmDateTime: String
    val backToHome: String

    val done: String

    // Bottom nav
    val tabHome: String
    val tabShop: String
    val tabSettings: String

    // Settings
    val settingsTitle: String
    val settingsAccountSection: String
    val settingsRoleLabel: String
    val settingsLogoutAction: String
    val settingsLogoutConfirmTitle: String
    val settingsLogoutConfirmMessage: String
    val settingsCancel: String
    fun settingsRole(role: String): String

    // Shop
    val shopTitle1: String
    val shopTitle2: String
    val shopSearchPlaceholder: String
    val partsCategoryAll: String
    val noPartsMatch: String
    val resetSearch: String
    val partsCount: String
    val outOfStock: String
    val inStockLabel: String
    fun fitsLabel(brand: String): String
    val fitsAnyLabel: String
    fun conditionLabel(condition: String): String
    val sellerSection: String
    val partAboutSection: String
    val callSeller: String
    val whatsappSeller: String
    val chatSeller: String
    fun ratingShort(rating: Double): String

    // Specialty names
    fun specialtyName(id: String): String
    fun specialtyDesc(id: String): String

    // Country names
    fun country(name: String): String
}

object EnglishStrings : Strings {
    override val appName = "AutoFix"
    override val brandTagline = "Mechanics that match your ride"
    override val location = "EAST SIDE"

    override val splashTagline = "Mechanics that match your ride"

    override val loginTitle = "Welcome back"
    override val loginSubtitle = "Sign in to continue"
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
    override val noAccountQuestion = "Don't have an account?"
    override val registerCta = "Create account"
    override val orDivider = "OR"

    override val registerPickerTitle = "Join AutoFix"
    override val registerPickerSubtitle = "Pick the account type that fits you"
    override val roleUserTitle = "I'm a car owner"
    override val roleUserSubtitle = "Find trusted mechanics for your car"
    override val roleMechanicTitle = "I'm a mechanic"
    override val roleMechanicSubtitle = "Get jobs and grow your workshop"

    override val registerUserTitle = "Create user account"
    override val registerMechanicTitle = "Create mechanic account"
    override val registerUserSubtitle = "Tell us a bit about yourself"
    override val registerMechanicSubtitle = "Set up your workshop profile"
    override val fullNameLabel = "Full name"
    override val fullNamePlaceholder = "Your name"
    override val phoneLabel = "Phone"
    override val phonePlaceholder = "+995 5XX XXX XXX"
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

    override val homeTitle1 = "Find a craftsman"
    override val homeTitle2 = "that knows your car."
    override val searchPlaceholder = "Search brand or model"
    override val pickBrand = "Pick your car brand"
    override val dontSeeBrand = "Don't see your brand?"
    override val addManually = "+ Add it manually"

    override val specQuestion = "What's the issue?"
    override val forYour = "FOR YOUR"
    override val estPrefix = "EST."
    override val change = "Change"
    override val pickSpecialty = "Pick a specialty"
    override val selectSpecialty = "Select a specialty"
    override val jobsDone = "JOBS DONE"
    override fun findCraftsmenFor(spec: String) = "Find craftsmen for $spec"

    override val filters = "Filters"
    override val sortNearest = "Nearest"
    override val sortRating = "Top rated"
    override val sortExperience = "Most experience"
    override fun fallbackNoBrand(brand: String, spec: String) =
        "No $brand specialists for ${spec.lowercase()} nearby. Showing other ${spec.lowercase()} craftsmen."
    override val noMatch = "No craftsmen match your filters."
    override val resetFilters = "Reset filters"
    override val openNow = "Open now"
    override val closed = "Closed"
    override val craftsmenSuffix = "CRAFTSMEN"
    override val expSuffix = "y exp"

    override val minRating = "Minimum rating"
    override val yearsExp = "Years of experience"
    override fun maxDistanceLabel(km: Int) = "Max distance: $km km"
    override val maxPrice = "Max price"
    override val availableToday = "Available today"
    override val reset = "Reset"
    override val applyFilters = "Apply filters"
    override val any = "Any"
    override fun yrsLabel(n: Int) = "$n+ yrs"

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
    override val bookAppointment = "Book appointment"

    override val reviewsTitle = "Reviews"
    override val reviewsSuffix = "REVIEWS"

    override val nearby = "NEARBY"

    override val bookSlot = "Book a slot"
    override val diagnosticSuffix = "DIAGNOSTIC"
    override val pickDate = "Pick a date"
    override val pickTime = "Pick a time"
    override val describeIssue = "Describe the issue (optional)"
    override val issuePlaceholder = "E.g. car shudders at idle, check engine light on for 2 days..."
    override fun confirmAt(day: String, time: String) = "Confirm $day $time"
    override val pickTimeSlot = "Pick a time slot"

    override val booked = "You're booked."
    override fun bookedSubtitle(name: String) =
        "$name will see you Wednesday at 11:00 AM. We sent a confirmation to your phone."
    override val confirmDateTime = "WED 29 APR · 11:00 AM"
    override val backToHome = "Back to home"
    override val done = "Done"

    override val tabHome = "Home"
    override val tabShop = "Shop"
    override val tabSettings = "Settings"

    override val settingsTitle = "Settings"
    override val settingsAccountSection = "Account"
    override val settingsRoleLabel = "Role"
    override val settingsLogoutAction = "Sign out"
    override val settingsLogoutConfirmTitle = "Sign out?"
    override val settingsLogoutConfirmMessage = "You'll need to sign in again to use the app."
    override val settingsCancel = "Cancel"
    override fun settingsRole(role: String) = when (role) {
        "user" -> "Customer"
        "mechanic" -> "Mechanic"
        "admin" -> "Admin"
        else -> role.replaceFirstChar { it.uppercase() }
    }

    override val shopTitle1 = "Find parts"
    override val shopTitle2 = "and connect with sellers."
    override val shopSearchPlaceholder = "Search parts, brand, or seller"
    override val partsCategoryAll = "All"
    override val noPartsMatch = "No parts match your search."
    override val resetSearch = "Clear search"
    override val partsCount = "PARTS"
    override val outOfStock = "Out of stock"
    override val inStockLabel = "In stock"
    override fun fitsLabel(brand: String) = "Fits $brand"
    override val fitsAnyLabel = "Universal fit"
    override fun conditionLabel(condition: String) = when (condition) {
        "New" -> "New"
        "Used" -> "Used"
        "Refurbished" -> "Refurbished"
        else -> condition
    }
    override val sellerSection = "Seller"
    override val partAboutSection = "About this part"
    override val callSeller = "Call"
    override val whatsappSeller = "WhatsApp"
    override val chatSeller = "Message"
    override fun ratingShort(rating: Double) = rating.toString()


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

    override fun specialtyDesc(id: String) = when (id) {
        "engine" -> "Combustion, timing, head gasket"
        "gearbox" -> "Manual, automatic, CVT"
        "electrical" -> "Wiring, ECU, sensors, battery"
        "radiator" -> "Radiator, thermostat, pumps"
        "brakes" -> "Pads, rotors, ABS, hydraulics"
        "suspension" -> "Shocks, struts, alignment"
        "exhaust" -> "Muffler, catalytic, manifolds"
        "aircon" -> "Compressor, refrigerant, vents"
        "bodywork" -> "Dent repair, paint, panels"
        "tires" -> "Mounting, balancing, TPMS"
        "diagnostic" -> "OBD-II, computer scan, diagnosis"
        "ev" -> "High-voltage systems, battery"
        else -> ""
    }

    override fun country(name: String) = name
}

object GeorgianStrings : Strings {
    override val appName = "AutoFix"
    override val brandTagline = "ხელოსნები შენი მანქანისთვის"
    override val location = "აღმოსავლეთი"

    override val splashTagline = "ხელოსნები შენი მანქანისთვის"

    override val loginTitle = "კეთილი იყოს დაბრუნება"
    override val loginSubtitle = "გაიარე ავტორიზაცია გასაგრძელებლად"
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
    override val noAccountQuestion = "არ გაქვს ანგარიში?"
    override val registerCta = "ანგარიშის შექმნა"
    override val orDivider = "ან"

    override val registerPickerTitle = "შემოუერთდი AutoFix-ს"
    override val registerPickerSubtitle = "აირჩიე ანგარიშის ტიპი"
    override val roleUserTitle = "ვარ მანქანის მფლობელი"
    override val roleUserSubtitle = "იპოვე სანდო ხელოსნები"
    override val roleMechanicTitle = "ვარ ხელოსანი"
    override val roleMechanicSubtitle = "მიიღე შეკვეთები და გაზარდე სახელოსნო"

    override val registerUserTitle = "მომხმარებლის ანგარიში"
    override val registerMechanicTitle = "ხელოსნის ანგარიში"
    override val registerUserSubtitle = "მოგვიყევი ცოტა შენ შესახებ"
    override val registerMechanicSubtitle = "დაამატე შენი სახელოსნოს დეტალები"
    override val fullNameLabel = "სახელი და გვარი"
    override val fullNamePlaceholder = "შენი სახელი"
    override val phoneLabel = "ტელეფონი"
    override val phonePlaceholder = "+995 5XX XXX XXX"
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

    override val homeTitle1 = "იპოვეთ ხელოსანი"
    override val homeTitle2 = "რომელმაც იცის თქვენი მანქანა."
    override val searchPlaceholder = "მოძებნეთ ბრენდი ან მოდელი"
    override val pickBrand = "აირჩიეთ მანქანის ბრენდი"
    override val dontSeeBrand = "ვერ ხედავთ თქვენს ბრენდს?"
    override val addManually = "+ დაამატეთ ხელით"

    override val specQuestion = "რა პრობლემაა?"
    override val forYour = "თქვენი"
    override val estPrefix = "დაარს."
    override val change = "შეცვლა"
    override val pickSpecialty = "აირჩიეთ სპეციალობა"
    override val selectSpecialty = "აირჩიეთ სპეციალობა"
    override val jobsDone = "შესრულებული"
    override fun findCraftsmenFor(spec: String) = "იპოვეთ ხელოსნები — $spec"

    override val filters = "ფილტრები"
    override val sortNearest = "უახლოესი"
    override val sortRating = "ტოპ რეიტინგი"
    override val sortExperience = "გამოცდილება"
    override fun fallbackNoBrand(brand: String, spec: String) =
        "$brand-ის სპეციალისტები ($spec) ახლოს არ მოიძებნა. ნაჩვენებია სხვა ხელოსნები."
    override val noMatch = "ფილტრებს არცერთი ხელოსანი არ ემთხვევა."
    override val resetFilters = "ფილტრების გასუფთავება"
    override val openNow = "ღიაა"
    override val closed = "დახურული"
    override val craftsmenSuffix = "ხელოსანი"
    override val expSuffix = "წ. გამოც."

    override val minRating = "მინ. რეიტინგი"
    override val yearsExp = "გამოცდილება (წელი)"
    override fun maxDistanceLabel(km: Int) = "მაქს. მანძილი: $km კმ"
    override val maxPrice = "მაქს. ფასი"
    override val availableToday = "ხელმისაწვდომია დღეს"
    override val reset = "გასუფთავება"
    override val applyFilters = "გამოყენება"
    override val any = "ნებისმიერი"
    override fun yrsLabel(n: Int) = "$n+ წელი"
    override val done = "მზადაა"

    override val about = "შესახებ"
    override val specialties = "სპეციალობები"
    override val brandsServiced = "მომსახურე ბრენდები"
    override val contactLocation = "კონტაქტი და მისამართი"
    override val recentReviews = "ბოლო შეფასებები"
    override fun seeAll(n: Int) = "ნახე ყველა ($n)"
    override val statRating = "რეიტინგი"
    override val statTrade = "გამოცდილება"
    override val statKm = "კილომეტრი"
    override val contactPhone = "ტელეფონი"
    override val contactWhatsapp = "WhatsApp"
    override val contactAddress = "მისამართი"
    override val contactHours = "სამუშაო საათები"
    override val bookAppointment = "ჯავშნა"

    override val reviewsTitle = "შეფასებები"
    override val reviewsSuffix = "შეფასება"

    override val nearby = "ახლოს"

    override val bookSlot = "ჯავშნა"
    override val diagnosticSuffix = "დიაგნოსტიკა"
    override val pickDate = "აირჩიეთ თარიღი"
    override val pickTime = "აირჩიეთ დრო"
    override val describeIssue = "აღწერეთ პრობლემა (არასავალდებულო)"
    override val issuePlaceholder = "მაგ. მანქანა ცარიელ სვლაზე ცახცახებს, ძრავის ნათურა ანთია 2 დღეა..."
    override fun confirmAt(day: String, time: String) = "დადასტურება — $day $time"
    override val pickTimeSlot = "აირჩიეთ დრო"

    override val booked = "თქვენ დაჯავშნეთ."
    override fun bookedSubtitle(name: String) =
        "$name შეგხვდებათ ოთხშაბათს 11:00-ზე. დადასტურება გამოგიგზავნეთ თქვენს ტელეფონზე."
    override val confirmDateTime = "ოთხ 29 აპრ · 11:00"
    override val backToHome = "მთავარზე დაბრუნება"

    override val tabHome = "მთავარი"
    override val tabShop = "მაღაზია"
    override val tabSettings = "პარამეტრები"

    override val settingsTitle = "პარამეტრები"
    override val settingsAccountSection = "ანგარიში"
    override val settingsRoleLabel = "როლი"
    override val settingsLogoutAction = "გასვლა"
    override val settingsLogoutConfirmTitle = "გავიდე ანგარიშიდან?"
    override val settingsLogoutConfirmMessage = "აპლიკაციის გამოყენებისთვის მოგიწევს ხელახლა შესვლა."
    override val settingsCancel = "გაუქმება"
    override fun settingsRole(role: String) = when (role) {
        "user" -> "მომხმარებელი"
        "mechanic" -> "ხელოსანი"
        "admin" -> "ადმინი"
        else -> role
    }

    override val shopTitle1 = "იპოვე ნაწილები"
    override val shopTitle2 = "და დაუკავშირდი გამყიდველს."
    override val shopSearchPlaceholder = "მოძებნე ნაწილი, ბრენდი ან გამყიდველი"
    override val partsCategoryAll = "ყველა"
    override val noPartsMatch = "ნაწილები ვერ მოიძებნა."
    override val resetSearch = "ძიების გასუფთავება"
    override val partsCount = "ნაწილი"
    override val outOfStock = "არ არის მარაგში"
    override val inStockLabel = "მარაგშია"
    override fun fitsLabel(brand: String) = "ერგება $brand-ს"
    override val fitsAnyLabel = "ნებისმიერ მოდელზე"
    override fun conditionLabel(condition: String) = when (condition) {
        "New" -> "ახალი"
        "Used" -> "მეორადი"
        "Refurbished" -> "აღდგენილი"
        else -> condition
    }
    override val sellerSection = "გამყიდველი"
    override val partAboutSection = "ნაწილის შესახებ"
    override val callSeller = "ზარი"
    override val whatsappSeller = "WhatsApp"
    override val chatSeller = "მესიჯი"
    override fun ratingShort(rating: Double) = rating.toString()

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

    override fun specialtyDesc(id: String) = when (id) {
        "engine" -> "წვა, ლილვის დრო, თავის შუასადებელი"
        "gearbox" -> "მექანიკა, ავტომატი, CVT"
        "electrical" -> "გაყვანილობა, ECU, სენსორები, ბატარეა"
        "radiator" -> "რადიატორი, თერმოსტატი, ტუმბოები"
        "brakes" -> "ხუნდები, დისკები, ABS, ჰიდრავლიკა"
        "suspension" -> "ამორტიზატორი, სკიდი, განკუთვნება"
        "exhaust" -> "ხმაუროვა, კატალიზატორი, კოლექტორი"
        "aircon" -> "კომპრესორი, ფრეონი, ვენტილაცია"
        "bodywork" -> "ჩაღრმავება, საღებავი, პანელები"
        "tires" -> "მონტაჟი, ბალანსი, TPMS"
        "diagnostic" -> "OBD-II, კომპიუტერული სკანი"
        "ev" -> "მაღალი ძაბვა, ბატარეა"
        else -> ""
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
val LocalLanguage = staticCompositionLocalOf { AppLanguage.EN }
val LocalToggleLanguage = staticCompositionLocalOf<() -> Unit> { {} }


val LocalSetInputFocused = staticCompositionLocalOf<(Boolean) -> Unit> { {} }