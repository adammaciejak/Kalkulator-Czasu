package edu.put.inf151756

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.MonthDay
import java.time.temporal.ChronoUnit

class SecondActivity : AppCompatActivity() {
    private fun easterMondayDate(date: LocalDate): MonthDay
    {
        if (date.year == 2005)
        {
            return MonthDay.of(4, 8)
        }
        if (date.year == 2018)
        {
            return MonthDay.of(11, 12)
        }
        val a = date.year % 19
        val b = date.year / 100
        val c = date.year % 100
        val d = b / 4
        val e = b % 4
        val f = (b + 8) / 25
        val g = (b - f + 1) / 3
        val h = (19 * a + b - d - g + 15) % 30
        val i = c / 4
        val k = c % 4
        val l = (32 + 2 * e + 2 * i - h - k) % 7
        val m = (a + 11 * h + 22 * l) / 451
        val n = h + l - 7 * m + 114
        val month = n / 31
        val day = n % 31 + 1

        val localDate = LocalDate.of(date.year, month, day).plusDays(1)
        return MonthDay.of(localDate.monthValue, localDate.dayOfMonth)
    }

    private fun corpusChristiDate(easterDate: MonthDay, date: LocalDate): MonthDay
    {
        val localDate = easterDate.atYear(date.year).plusDays(60)
        return MonthDay.of(localDate.monthValue, localDate.dayOfMonth)

    }
    private fun isHoliday(date: LocalDate): Boolean
    {
        val holidays = setOf(
            MonthDay.of(1, 1), // New Year's Day
            MonthDay.of(1, 6), // Epiphany
            MonthDay.of(5, 1), // Labor Day
            MonthDay.of(5, 3), // Constitution Day
            MonthDay.of(8, 15), // Assumption of Mary
            MonthDay.of(11, 1), // All Saints' Day
            MonthDay.of(11, 11), // Independence Day
            MonthDay.of(12, 25), // Christmas Day
            MonthDay.of(12, 26), // Boxing Day
            easterMondayDate(date),
            corpusChristiDate(easterMondayDate(date), date)
        )
        return holidays.contains(MonthDay.from(date))
    }

    private fun getBusinessDaysCount(startDate: LocalDate, endDate: LocalDate): Int
    {
        var count = -1
        var currentDate = startDate

        while (!currentDate.isAfter(endDate))
        {
            if (currentDate.dayOfWeek != DayOfWeek.SATURDAY && currentDate.dayOfWeek != DayOfWeek.SUNDAY &&
                    !isHoliday(currentDate))
            {
                count++
            }
            currentDate = currentDate.plusDays(1)
        }
        return count
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        var date1 = LocalDate.now()
        var date2 = LocalDate.now()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val status: EditText = findViewById(R.id.dateDiff)
        val datePicker1: DatePicker = findViewById(R.id.datePicker1)
        val datePicker2: DatePicker = findViewById(R.id.datePicker2)
        val addButton: Button = findViewById(R.id.addButton)
        val tv: TextView = findViewById(R.id.textView2)

        fun changeText()
        {
            val daysBetweenDates = ChronoUnit.DAYS.between(date1, date2)
            if (daysBetweenDates >= 0)
            {
                status.setText(daysBetweenDates.toString())
                tv.text = "Dni roboczych pomiędzy datami: ${getBusinessDaysCount(date1, date2)}"
            }
            else
            {
                status.setText(kotlin.math.abs(daysBetweenDates).toString())
                tv.text = "Dni roboczych pomiędzy datami: ${getBusinessDaysCount(date2, date1)}"
            }

        }

        datePicker1.setOnDateChangedListener()
        {
                _, year, monthOfYear, dayOfMonth ->
            date1 = LocalDate.of(year, monthOfYear+1, dayOfMonth)
            changeText()
        }

        datePicker2.setOnDateChangedListener()
        {
                _, year, monthOfYear, dayOfMonth ->
            date2 = LocalDate.of(year, monthOfYear+1, dayOfMonth)
            changeText()
        }

        addButton.setOnClickListener {
            if (addButton.text == "+")
            {
                addButton.text = "-"
            }
            else
            {
                addButton.text = "+"
            }
        }

        status.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //status.setSelection(status.length())
                val daysToAdd = status.text.toString()

                if (daysToAdd.isEmpty())
                {
                    date2 = date1
                }
                else if (addButton.text == "+")
                {
                    date2 = date1.plusDays(daysToAdd.toLong())
                }
                else if (addButton.text == "-")
                {
                    date2 = date1.minusDays(daysToAdd.toLong())
                }
                datePicker2.updateDate(date2.year, date2.monthValue - 1, date2.dayOfMonth)
                true
            } else {
                false
            }
        }
    }
}