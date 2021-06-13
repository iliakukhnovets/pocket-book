package com.example.pocketbook.data.classes.book

import android.os.Bundle
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedBooksDataClass(
    val bookRating: Int,
    val bookUrl: String?,
    val bookName: String?,
    val bookAuthor: String?,
    val bookDescription: String?,
    val bookAgeLimit: String?,
    val bookSubscribeType: String?,
    val bookIsFinished: String,
    val bookLanguage: String?,
    val bookStyle: String?,
    val bookSeries: String?,
    val marksCount: Int,
    val objectId: String?,
    val marksSum: Int,
    val isRated: Boolean,
    val facts: String?
) : Parcelable {

    companion object {
        val SELECTED_BOOK_URL_ARG = "bookUrl"
        val SELECTED_BOOK_NAME_ARG = "bookName"
        val SELECTED_BOOK_AUTHOR_ARG = "bookAuthor"
        val SELECTED_BOOK_RATING_ARG = "bookRating"
        val SELECTED_BOOK_AGE_LIMIT_ARG = "bookAgeLimit"
        val SELECTED_BOOK_SUBSCRIBE_ARG = "bookSubscribeType"
        val SELECTED_BOOK_IS_FINISHED_ARG = "bookIsFinished"
        val SELECTED_BOOK_DESCRIPTION_ARG = "bookDescription"
        val SELECTED_BOOK_LANGUAGE_ARG = "bookLanguage"
        val SELECTED_BOOK_STYLE_ARG = "bookStyle"
        val SELECTED_BOOK_SERIES_ARG = "bookSeries"
        val SELECTED_BOOK_MARKS_COUNT = "bookMarks"
        val SELECTED_BOOK_IS_RATED = "bookIsRated"
        val SELECTED_BOOK_MARKS_SUM = "bookMarksSum"
        val SELECTED_BOOK_OBJECT_ID = "objectId"
        val SELECTED_BOOK_FACTS = "bookFacts"
    }

    fun putArgs(): Bundle {
        val fragmentArguments = Bundle()
        fragmentArguments.putInt(SELECTED_BOOK_RATING_ARG, bookRating)
        fragmentArguments.putString(SELECTED_BOOK_URL_ARG, bookUrl)
        fragmentArguments.putString(SELECTED_BOOK_NAME_ARG, bookName)
        fragmentArguments.putString(SELECTED_BOOK_AUTHOR_ARG, bookAuthor)
        fragmentArguments.putString(SELECTED_BOOK_DESCRIPTION_ARG, bookDescription)
        fragmentArguments.putString(SELECTED_BOOK_AGE_LIMIT_ARG, bookAgeLimit)
        fragmentArguments.putString(SELECTED_BOOK_SUBSCRIBE_ARG, bookSubscribeType)
        fragmentArguments.putString(SELECTED_BOOK_IS_FINISHED_ARG, bookIsFinished)
        fragmentArguments.putString(SELECTED_BOOK_LANGUAGE_ARG, bookLanguage)
        fragmentArguments.putString(SELECTED_BOOK_STYLE_ARG, bookStyle)
        fragmentArguments.putString(SELECTED_BOOK_SERIES_ARG, bookSeries)
        fragmentArguments.putInt(SELECTED_BOOK_MARKS_COUNT, marksCount)
        fragmentArguments.putString(SELECTED_BOOK_OBJECT_ID, objectId)
        fragmentArguments.putInt(SELECTED_BOOK_MARKS_SUM, marksSum)
        fragmentArguments.putBoolean(SELECTED_BOOK_IS_RATED, isRated)
        if (facts != null) {
            fragmentArguments.putString(SELECTED_BOOK_FACTS, facts)
        }
        return fragmentArguments
    }
}