package org.example.project.ui.i18n

import androidx.compose.runtime.staticCompositionLocalOf

enum class AppLanguage(val code: String, val display: String) {
    EN("en", "EN"),
    KA("ka", "KA"),
}

interface Strings {
    val appName: String
    val location: String

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

    // Specialty names
    fun specialtyName(id: String): String
    fun specialtyDesc(id: String): String

    // Country names
    fun country(name: String): String
}

object EnglishStrings : Strings {
    override val appName = "craftsmen"
    override val location = "EAST SIDE"

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
    override val appName = "craftsmen"
    override val location = "აღმოსავლეთი"

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
