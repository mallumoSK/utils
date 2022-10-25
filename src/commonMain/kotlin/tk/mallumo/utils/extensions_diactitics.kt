@file:Suppress("unused")

package tk.mallumo.utils

val String?.noDiacritics: String get() = removeDiacritic(this, DiacriticType.NORMAL)
val String?.noDiacriticsLower: String get() = removeDiacritic(this, DiacriticType.LOWER)
val String?.noDiacriticsUpper: String get() = removeDiacritic(this, DiacriticType.UPPER)

// pismena s diakritikou
private val pdA = arrayOf(
    "ď",
    "ľ",
    "š",
    "č",
    "ť",
    "ž",
    "ý",
    "á",
    "í",
    "é",
    "ú",
    "ä",
    "ň",
    "ĺ",
    "ŕ",
    "ô",
    "ó",
    "Ď",
    "Ľ",
    "Š",
    "Č",
    "Ť",
    "Ž",
    "Ý",
    "Á",
    "Í",
    "É",
    "Ú",
    "Ň",
    "Ó",
    "Ŕ",
    "Ĺ"
)

// pismena bez diakritky zodpovedajuce pismenam v poli pd
private val pA = arrayOf(
    "d",
    "l",
    "s",
    "c",
    "t",
    "z",
    "y",
    "a",
    "i",
    "e",
    "u",
    "a",
    "n",
    "l",
    "r",
    "o",
    "o",
    "D",
    "L",
    "S",
    "C",
    "T",
    "Z",
    "Y",
    "A",
    "I",
    "E",
    "U",
    "N",
    "O",
    "R",
    "L"
)

// pismena s diakritikou
private val pdL =
    arrayOf("ď", "ľ", "š", "č", "ť", "ž", "ý", "á", "í", "é", "ú", "ä", "ň", "ĺ", "ŕ", "ô", "ó")

// pismena bez diakritky zodpovedajuce pismenam v poli pd
private val pL =
    arrayOf("d", "l", "s", "c", "t", "z", "y", "a", "i", "e", "u", "a", "n", "l", "r", "o", "o")

// pismena s diakritikou
private val pdU =
    arrayOf("Ď", "Ľ", "Š", "Č", "Ť", "Ž", "Ý", "Á", "Í", "É", "Ú", "Ä", "Ň", "Ĺ", "Ŕ", "Ô", "Ó")

// pismena bez diakritky zodpovedajuce pismenam v poli pd
private val pU =
    arrayOf("D", "L", "S", "C", "T", "Z", "Y", "A", "I", "E", "U", "A", "N", "L", "R", "O", "O")

enum class DiacriticType {
    NORMAL, LOWER, UPPER
}

private fun removeDiacritic(input: String?, diacriticType: DiacriticType): String {
    if (input.isNullOrEmpty()) return ""

    var out = input.trim()
    when (diacriticType) {
        DiacriticType.NORMAL -> {
            for (i in pdA.indices) {
                out = out.replace(pdA[i], pA[i])
            }
        }
        DiacriticType.LOWER -> {
            out = out.lowercase()
            for (i in pdL.indices) {
                out = out.replace(pdL[i], pL[i])
            }
        }
        DiacriticType.UPPER -> {
            out = out.uppercase()
            for (i in pdU.indices) {
                out = out.replace(pdU[i], pU[i])
            }
        }
    }
    return out
}