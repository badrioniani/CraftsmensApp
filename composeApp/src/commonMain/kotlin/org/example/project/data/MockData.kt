package org.example.project.data

import androidx.compose.ui.graphics.Color

data class CarBrand(
    val id: String,
    val name: String,
    val country: String,
    val founded: Int,
    val color: Color,
    val carImage: String,
)

data class Specialty(
    val id: String,
    val name: String,
    val desc: String,
    val jobs: Int,
)

data class Mechanic(
    val id: String,
    val name: String,
    val shop: String,
    val rating: Double,
    val reviews: Int,
    val years: Int,
    val distance: Double,
    val price: Int,
    val available: Boolean,
    val verified: Boolean,
    val specialties: List<String>,
    val brands: List<String>,
    val phone: String,
    val whatsapp: String,
    val address: String,
    val hours: String,
    val bio: String,
    val photo: Color,
    val initials: String,
    val badges: List<String>,
)

data class Review(
    val id: String,
    val mechId: String,
    val author: String,
    val rating: Int,
    val date: String,
    val text: String,
    val job: String,
)

val CAR_BRANDS = listOf(
    // Germany
    CarBrand("bmw",           "BMW",            "Germany",       1916, Color(0xFF0066B1), "https://www.carlogos.org/car-logos/bmw-logo.png"),
    CarBrand("mercedesbenz",  "Mercedes-Benz",  "Germany",       1926, Color(0xFF000000), "https://www.carlogos.org/car-logos/mercedes-benz-logo.png"),
    CarBrand("audi",          "Audi",           "Germany",       1909, Color(0xFFBB0A30), "https://www.carlogos.org/car-logos/audi-logo.png"),
    CarBrand("porsche",       "Porsche",        "Germany",       1931, Color(0xFFD5001C), "https://www.carlogos.org/car-logos/porsche-logo.png"),
    CarBrand("volkswagen",    "Volkswagen",     "Germany",       1937, Color(0xFF001E50), "https://www.carlogos.org/car-logos/volkswagen-logo.png"),
    CarBrand("opel",          "Opel",           "Germany",       1899, Color(0xFFF7FF00), "https://www.carlogos.org/car-logos/opel-logo.png"),
    CarBrand("smart",         "Smart",          "Germany",       1994, Color(0xFF78BE20), "https://www.carlogos.org/car-logos/smart-logo.png"),
    CarBrand("maybach",       "Maybach",        "Germany",       1909, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/maybach-logo.png"),

    // Italy
    CarBrand("ferrari",       "Ferrari",        "Italy",         1939, Color(0xFFFF2800), "https://www.carlogos.org/car-logos/ferrari-logo.png"),
    CarBrand("lamborghini",   "Lamborghini",    "Italy",         1963, Color(0xFFE4A024), "https://www.carlogos.org/car-logos/lamborghini-logo.png"),
    CarBrand("maserati",      "Maserati",       "Italy",         1914, Color(0xFF003B6F), "https://www.carlogos.org/car-logos/maserati-logo.png"),
    CarBrand("alfaromeo",     "Alfa Romeo",     "Italy",         1910, Color(0xFFB30000), "https://www.carlogos.org/car-logos/alfa-romeo-logo.png"),
    CarBrand("fiat",          "Fiat",           "Italy",         1899, Color(0xFFB22222), "https://www.carlogos.org/car-logos/fiat-logo.png"),
    CarBrand("lancia",        "Lancia",         "Italy",         1906, Color(0xFF003D7A), "https://www.carlogos.org/car-logos/lancia-logo.png"),
    CarBrand("pagani",        "Pagani",         "Italy",         1992, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/pagani-logo.png"),
    CarBrand("abarth",        "Abarth",         "Italy",         1949, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/abarth-logo.png"),

    // France
    CarBrand("peugeot",       "Peugeot",        "France",        1889, Color(0xFF1B2A4A), "https://www.carlogos.org/car-logos/peugeot-logo.png"),
    CarBrand("renault",       "Renault",        "France",        1899, Color(0xFFFFCC33), "https://www.carlogos.org/car-logos/renault-logo.png"),
    CarBrand("citroen",       "Citroën",        "France",        1919, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/citroen-logo.png"),
    CarBrand("bugatti",       "Bugatti",        "France",        1909, Color(0xFF003DA5), "https://www.carlogos.org/car-logos/bugatti-logo.png"),
    CarBrand("ds",            "DS Automobiles", "France",        2014, Color(0xFFB8860B), "https://www.carlogos.org/car-logos/ds-logo.png"),
    CarBrand("alpine",        "Alpine",         "France",        1955, Color(0xFF0046A0), "https://www.carlogos.org/car-logos/alpine-logo.png"),

    // United Kingdom
    CarBrand("astonmartin",   "Aston Martin",   "UK",            1913, Color(0xFF00665E), "https://www.carlogos.org/car-logos/aston-martin-logo.png"),
    CarBrand("jaguar",        "Jaguar",         "UK",            1922, Color(0xFF1A3A1A), "https://www.carlogos.org/car-logos/jaguar-logo.png"),
    CarBrand("bentley",       "Bentley",        "UK",            1919, Color(0xFF003822), "https://www.carlogos.org/car-logos/bentley-logo.png"),
    CarBrand("rollsroyce",    "Rolls-Royce",    "UK",            1904, Color(0xFF68228B), "https://www.carlogos.org/car-logos/rolls-royce-logo.png"),
    CarBrand("mini",          "Mini",           "UK",            1959, Color(0xFFE60012), "https://www.carlogos.org/car-logos/mini-logo.png"),
    CarBrand("landrover",     "Land Rover",     "UK",            1948, Color(0xFF005A30), "https://www.carlogos.org/car-logos/land-rover-logo.png"),
    CarBrand("lotus",         "Lotus",          "UK",            1952, Color(0xFFFFB81C), "https://www.carlogos.org/car-logos/lotus-logo.png"),
    CarBrand("mclaren",       "McLaren",        "UK",            1985, Color(0xFFFF8000), "https://www.carlogos.org/car-logos/mclaren-logo.png"),
    CarBrand("mg",            "MG",             "UK",            1924, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/mg-logo.png"),
    CarBrand("morgan",        "Morgan",         "UK",            1910, Color(0xFF004225), "https://www.carlogos.org/car-logos/morgan-logo.png"),

    // USA
    CarBrand("ford",          "Ford",           "USA",           1903, Color(0xFF003478), "https://www.carlogos.org/car-logos/ford-logo.png"),
    CarBrand("chevrolet",     "Chevrolet",      "USA",           1911, Color(0xFFCB9D38), "https://www.carlogos.org/car-logos/chevrolet-logo.png"),
    CarBrand("cadillac",      "Cadillac",       "USA",           1902, Color(0xFF941E32), "https://www.carlogos.org/car-logos/cadillac-logo.png"),
    CarBrand("chrysler",      "Chrysler",       "USA",           1925, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/chrysler-logo.png"),
    CarBrand("dodge",         "Dodge",          "USA",           1900, Color(0xFFCC0000), "https://www.carlogos.org/car-logos/dodge-logo.png"),
    CarBrand("jeep",          "Jeep",           "USA",           1941, Color(0xFF3B5526), "https://www.carlogos.org/car-logos/jeep-logo.png"),
    CarBrand("gmc",           "GMC",            "USA",           1911, Color(0xFFCC0000), "https://www.carlogos.org/car-logos/gmc-logo.png"),
    CarBrand("buick",         "Buick",          "USA",           1903, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/buick-logo.png"),
    CarBrand("lincoln",       "Lincoln",        "USA",           1917, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/lincoln-logo.png"),
    CarBrand("ram",           "Ram",            "USA",           2010, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/ram-logo.png"),
    CarBrand("tesla",         "Tesla",          "USA",           2003, Color(0xFFCC0000), "https://www.carlogos.org/car-logos/tesla-logo.png"),
    CarBrand("rivian",        "Rivian",         "USA",           2009, Color(0xFF005A8E), "https://www.carlogos.org/car-logos/rivian-logo.png"),
    CarBrand("lucid",         "Lucid",          "USA",           2007, Color(0xFF1F2A44), "https://www.carlogos.org/car-logos/lucid-motors-logo.png"),
    CarBrand("hummer",        "Hummer",         "USA",           1992, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/hummer-logo.png"),

    // Japan
    CarBrand("toyota",        "Toyota",         "Japan",         1937, Color(0xFFEB0A1E), "https://www.carlogos.org/car-logos/toyota-logo.png"),
    CarBrand("honda",          "Honda",         "Japan",         1948, Color(0xFFCC0000), "https://www.carlogos.org/car-logos/honda-logo.png"),
    CarBrand("nissan",        "Nissan",         "Japan",         1933, Color(0xFFC3002F), "https://www.carlogos.org/car-logos/nissan-logo.png"),
    CarBrand("mazda",         "Mazda",          "Japan",         1920, Color(0xFF101010), "https://www.carlogos.org/car-logos/mazda-logo.png"),
    CarBrand("subaru",        "Subaru",         "Japan",         1953, Color(0xFF002663), "https://www.carlogos.org/car-logos/subaru-logo.png"),
    CarBrand("suzuki",        "Suzuki",         "Japan",         1955, Color(0xFFCC0000), "https://www.carlogos.org/car-logos/suzuki-logo.png"),
    CarBrand("mitsubishi",    "Mitsubishi",     "Japan",         1917, Color(0xFFE60012), "https://www.carlogos.org/car-logos/mitsubishi-logo.png"),
    CarBrand("lexus",         "Lexus",          "Japan",         1989, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/lexus-logo.png"),
    CarBrand("infiniti",      "Infiniti",       "Japan",         1989, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/infiniti-logo.png"),
    CarBrand("acura",         "Acura",          "Japan",         1986, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/acura-logo.png"),
    CarBrand("daihatsu",      "Daihatsu",       "Japan",         1907, Color(0xFFE60012), "https://www.carlogos.org/car-logos/daihatsu-logo.png"),
    CarBrand("isuzu",         "Isuzu",          "Japan",         1916, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/isuzu-logo.png"),

    // South Korea
    CarBrand("hyundai",       "Hyundai",        "South Korea",   1967, Color(0xFF002C5F), "https://www.carlogos.org/car-logos/hyundai-logo.png"),
    CarBrand("kia",           "Kia",            "South Korea",   1944, Color(0xFF05141F), "https://www.carlogos.org/car-logos/kia-logo.png"),
    CarBrand("genesis",       "Genesis",        "South Korea",   2015, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/genesis-logo.png"),
    CarBrand("kgmobility",    "KG Mobility",    "South Korea",   1954, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/ssangyong-logo.png"),

    // China
    CarBrand("byd",           "BYD",            "China",         2003, Color(0xFFD80000), "https://www.carlogos.org/car-logos/byd-logo.png"),
    CarBrand("geely",         "Geely",          "China",         1986, Color(0xFF002F6C), "https://www.carlogos.org/car-logos/geely-logo.png"),
    CarBrand("chery",         "Chery",          "China",         1997, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/chery-logo.png"),
    CarBrand("greatwall",     "Great Wall",     "China",         1984, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/great-wall-logo.png"),
    CarBrand("nio",           "NIO",            "China",         2014, Color(0xFF00BFA5), "https://www.carlogos.org/car-logos/nio-logo.png"),
    CarBrand("xpeng",         "XPeng",          "China",         2014, Color(0xFF00B0F0), "https://www.carlogos.org/car-logos/xpeng-logo.png"),
    CarBrand("liauto",        "Li Auto",        "China",         2015, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/li-auto-logo.png"),
    CarBrand("haval",         "Haval",          "China",         2013, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/haval-logo.png"),
    CarBrand("lynkco",        "Lynk & Co",      "China",         2016, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/lynk-co-logo.png"),
    CarBrand("hongqi",        "Hongqi",         "China",         1958, Color(0xFFB22222), "https://www.carlogos.org/car-logos/hongqi-logo.png"),
    CarBrand("zeekr",         "Zeekr",          "China",         2021, Color(0xFF1A1A1A), "https://www.carlogos.org/car-logos/zeekr-logo.png"),

    // Sweden
    CarBrand("volvo",         "Volvo",          "Sweden",        1927, Color(0xFF1C3F94), "https://www.carlogos.org/car-logos/volvo-logo.png"),
    CarBrand("koenigsegg",    "Koenigsegg",     "Sweden",        1994, Color(0xFF101010), "https://www.carlogos.org/car-logos/koenigsegg-logo.png"),
    CarBrand("polestar",      "Polestar",       "Sweden",        2017, Color(0xFFC0C0C0), "https://www.carlogos.org/car-logos/polestar-logo.png"),

    // Czech Republic
    CarBrand("skoda",         "Škoda",          "Czech Republic", 1895, Color(0xFF4BA82E), "https://www.carlogos.org/car-logos/skoda-logo.png"),

    // Romania
    CarBrand("dacia",         "Dacia",          "Romania",       1966, Color(0xFF646B52), "https://www.carlogos.org/car-logos/dacia-logo.png"),

    // Spain
    CarBrand("seat",          "SEAT",           "Spain",         1950, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/seat-logo.png"),
    CarBrand("cupra",         "Cupra",          "Spain",         2018, Color(0xFFB87333), "https://www.carlogos.org/car-logos/cupra-logo.png"),

    // Russia
    CarBrand("lada",          "Lada",           "Russia",        1966, Color(0xFF003F87), "https://www.carlogos.org/car-logos/lada-logo.png"),
    CarBrand("uaz",           "UAZ",            "Russia",        1941, Color(0xFF4B5320), "https://www.carlogos.org/car-logos/uaz-logo.png"),
    CarBrand("gaz",           "GAZ",            "Russia",        1932, Color(0xFF003F87), "https://www.carlogos.org/car-logos/gaz-logo.png"),

    // India
    CarBrand("tata",          "Tata Motors",    "India",         1991, Color(0xFF002F6C), "https://www.carlogos.org/car-logos/tata-logo.png"),
    CarBrand("mahindra",      "Mahindra",       "India",         1945, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/mahindra-logo.png"),

    // Malaysia
    CarBrand("proton",        "Proton",         "Malaysia",      1983, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/proton-logo.png"),
    CarBrand("perodua",       "Perodua",        "Malaysia",      1993, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/perodua-logo.png"),

    // Croatia
    CarBrand("rimac",         "Rimac",          "Croatia",       2009, Color(0xFFC8102E), "https://www.carlogos.org/car-logos/rimac-logo.png"),

    // Netherlands
    CarBrand("donkervoort",   "Donkervoort",    "Netherlands",   1978, Color(0xFFFF6600), "https://www.carlogos.org/car-logos/donkervoort-logo.png"),
    CarBrand("spyker",        "Spyker",         "Netherlands",   2000, Color(0xFFC0C0C0), "https://www.carlogos.org/car-logos/spyker-logo.png"),

    // Vietnam
    CarBrand("vinfast",       "VinFast",        "Vietnam",       2017, Color(0xFF00A4E4), "https://www.carlogos.org/car-logos/vinfast-logo.png"),
)

val SPECIALTIES = listOf(
    Specialty("engine", "Engine", "Combustion, timing, head gasket", 1240),
    Specialty("gearbox", "Transmission", "Manual, automatic, CVT", 890),
    Specialty("electrical", "Electrical", "Wiring, ECU, sensors, battery", 1567),
    Specialty("radiator", "Cooling", "Radiator, thermostat, pumps", 643),
    Specialty("brakes", "Brakes", "Pads, rotors, ABS, hydraulics", 2104),
    Specialty("suspension", "Suspension", "Shocks, struts, alignment", 978),
    Specialty("exhaust", "Exhaust", "Muffler, catalytic, manifolds", 412),
    Specialty("aircon", "A/C & Heating", "Compressor, refrigerant, vents", 756),
    Specialty("bodywork", "Bodywork", "Dent repair, paint, panels", 534),
    Specialty("tires", "Tires & Wheels", "Mounting, balancing, TPMS", 1823),
    Specialty("diagnostic", "Diagnostics", "OBD-II, computer scan, diagnosis", 1102),
    Specialty("ev", "EV Specialist", "High-voltage systems, battery", 287),
)

val MECHANICS = listOf(
    Mechanic(
        id = "m1", name = "Lasha Gafrindashvili", shop = "Rati's Garage",
        rating = 5.0, reviews = 956, years = 24, distance = 1.2, price = 3,
        available = true, verified = true,
        specialties = listOf("engine", "gearbox", "diagnostic"),
        brands = listOf("norvik", "glanz", "briton", "kazuya"),
        phone = "+995 (557) 41-96-45", whatsapp = "+995557419645",
        address = "Tbilisi Feiqrebi",
        hours = "Mon–Sat · 7:00 AM – 6:00 PM",
        bio = "Third-generation mechanic. ASE Master certified. Specializes in European engine rebuilds and dual-clutch transmissions. If it has pistons, I can fix it.",
        photo = Color(0xFFC87F3A), initials = "MH",
        badges = listOf("ASE Master", "European specialist", "24 yrs"),
    ),
    Mechanic(
        id = "m2", name = "Priya Subramanian", shop = "Subra Garage",
        rating = 4.8, reviews = 183, years = 12, distance = 2.4, price = 2,
        available = true, verified = true,
        specialties = listOf("electrical", "diagnostic", "ev"),
        brands = listOf("orbit", "kazuya", "volcar", "astre"),
        phone = "+1 (555) 670-2218", whatsapp = "+15556702218",
        address = "92 Maple Row, Northgate",
        hours = "Tue–Sat · 8:00 AM – 5:00 PM",
        bio = "EV-first shop. I trained on hybrid powertrains for 6 years before going independent. Love a tricky CAN-bus problem.",
        photo = Color(0xFF3A7A8A), initials = "PS",
        badges = listOf("EV certified", "High-voltage", "12 yrs"),
    ),
    Mechanic(
        id = "m3", name = "Diego Romero", shop = "Romero & Sons",
        rating = 4.7, reviews = 412, years = 25, distance = 3.1, price = 2,
        available = true, verified = true,
        specialties = listOf("engine", "exhaust", "brakes", "suspension"),
        brands = listOf("hauler", "norvik", "mirador", "tessera"),
        phone = "+1 (555) 884-0091", whatsapp = "+15558840091",
        address = "1207 Willow Ave, Southbridge",
        hours = "Mon–Fri · 7:30 AM – 7:00 PM",
        bio = "Family-run since 1998. We do honest work, no upsell. Diesel and big-truck specialty. Spanish/English.",
        photo = Color(0xFF9B3838), initials = "DR",
        badges = listOf("Diesel specialist", "Family-run", "25 yrs"),
    ),
    Mechanic(
        id = "m4", name = "Aisha Bensaid", shop = "Bensaid Mechanics",
        rating = 4.9, reviews = 96, years = 8, distance = 0.8, price = 3,
        available = true, verified = true,
        specialties = listOf("gearbox", "electrical", "aircon"),
        brands = listOf("astre", "glanz", "tessera"),
        phone = "+1 (555) 332-5567", whatsapp = "+15553325567",
        address = "64 Pine Court, Downtown",
        hours = "Mon–Sat · 9:00 AM – 6:00 PM",
        bio = "Newer shop, big team energy. We focus on women feeling safe asking questions. No condescension, ever.",
        photo = Color(0xFF5A4A7A), initials = "AB",
        badges = listOf("Women-owned", "Trans specialist", "8 yrs"),
    ),
    Mechanic(
        id = "m5", name = "Tomas Larsen", shop = "Larsen Motorworks",
        rating = 4.6, reviews = 318, years = 22, distance = 4.7, price = 4,
        available = true, verified = true,
        specialties = listOf("engine", "suspension", "bodywork"),
        brands = listOf("norvik", "glanz", "briton"),
        phone = "+1 (555) 109-7732", whatsapp = "+15551097732",
        address = "2200 Industrial Pkwy, Westend",
        hours = "Mon–Fri · 8:00 AM – 5:30 PM",
        bio = "Performance and restoration. Premium pricing, premium results. By appointment only.",
        photo = Color(0xFF3A4A5C), initials = "TL",
        badges = listOf("Performance", "Restoration", "Premium"),
    ),
    Mechanic(
        id = "m6", name = "Joon Park", shop = "Park Quick Service",
        rating = 4.5, reviews = 524, years = 6, distance = 1.6, price = 1,
        available = true, verified = false,
        specialties = listOf("brakes", "tires", "aircon", "radiator"),
        brands = listOf("volcar", "kazuya", "orbit"),
        phone = "+1 (555) 778-1140", whatsapp = "+15557781140",
        address = "880 Elm Plaza, Midtown",
        hours = "Daily · 8:00 AM – 9:00 PM",
        bio = "Fast, affordable, no-frills. Quick turnarounds for routine maintenance. Walk-ins welcome.",
        photo = Color(0xFF6B8C5A), initials = "JP",
        badges = listOf("Walk-ins", "Fast service", "Budget"),
    ),
    Mechanic(
        id = "m7", name = "Eleanor Voss", shop = "Voss Garage",
        rating = 4.8, reviews = 142, years = 15, distance = 5.3, price = 2,
        available = false, verified = true,
        specialties = listOf("radiator", "aircon", "electrical"),
        brands = listOf("briton", "glanz", "mirador"),
        phone = "+1 (555) 445-6602", whatsapp = "+15554456602",
        address = "317 Cedar Lane, Highlands",
        hours = "Mon–Thu · 8:00 AM – 5:00 PM",
        bio = "Cooling and climate specialty. If your car is overheating or your A/C is sad, call me first.",
        photo = Color(0xFFA86A3A), initials = "EV",
        badges = listOf("Cooling expert", "A/C certified", "15 yrs"),
    ),
)

val REVIEWS = listOf(
    Review("r1", "m1", "Sarah K.", 5, "2 weeks ago",
        "Marcus diagnosed a tricky timing belt issue three other shops missed. Charged less than the dealer quoted. Will return.",
        "Engine timing belt"),
    Review("r2", "m1", "James R.", 5, "1 month ago",
        "Honest, fast, and explained everything. Showed me the worn part before replacing it. Rare these days.",
        "Transmission service"),
    Review("r3", "m1", "Lina O.", 4, "2 months ago",
        "Great work, slightly busy schedule meant a 1-week wait. Worth it though.",
        "Engine diagnostic"),
    Review("r4", "m2", "David P.", 5, "5 days ago",
        "Priya found a parasitic battery drain that had been bothering me for 3 months. Total magic.",
        "Electrical diagnostic"),
    Review("r5", "m2", "Mei L.", 5, "3 weeks ago",
        "EV battery health check was thorough. She actually showed me the diagnostic readings.",
        "EV battery service"),
)

val SYNTHETIC_REVIEWS = listOf(
    Review("s1", "", "Hugo M.", 5, "4 months ago", "Reliable, fairly priced, no surprises on the bill. Booking was easy.", "General service"),
    Review("s2", "", "Yuki T.", 4, "6 months ago", "Good work but the parking is a nightmare. Service itself was solid.", "Brake replacement"),
    Review("s3", "", "Carlos B.", 5, "8 months ago", "Saved me $400 vs the dealer quote and the car runs better.", "ECU diagnostic"),
)
