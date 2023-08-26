package ru.fi.englishtrainer20.elementsInterface

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import ru.fi.englishtrainer20.R

object Fonts {
    val comicNeueFamily = FontFamily(
        Font(R.font.comic_neue_italic, FontWeight.Normal),
        Font(R.font.comic_neue_bold_italic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.comic_neue_bold, FontWeight.Bold),
        Font(R.font.comic_neue_regular, FontWeight.Normal),
        Font(R.font.comic_neue_light, FontWeight.Light),
        Font(R.font.comic_neue_light_italic, FontWeight.Light, FontStyle.Italic)
    )
}