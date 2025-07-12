package com.example.gmailuilayout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime

class EmailsListViewModel() : ViewModel() {

    private val _emailsList = MutableLiveData<List<EmailItem>>(emptyList())
    val emailsList : LiveData<List<EmailItem>> get() = _emailsList

    init {
        getAllEmailsList()
    }

    fun getAllEmailsList(){
        _emailsList.value = dummyData()
    }



    private fun dummyData() : List<EmailItem> {

       return listOf(
            EmailItem(
                1,
                "https://randomuser.me/api/portraits/women/1.jpg",
                "Priya Verma",
                LocalDateTime.now().minusHours(1),
                "Meeting Rescheduled",
                "Hi, the client requested to move the meeting to 4 PM.",
                true
            ),
            EmailItem(
                2,
                "https://randomuser.me/api/portraits/men/2.jpg",
                "Arjun Kapoor",
                LocalDateTime.now().minusDays(1),
                "Code Review",
                "Please check the latest pull request before EOD.",
                false
            ),
            EmailItem(
                3,
                "https://randomuser.me/api/portraits/women/3.jpg",
                "Kavita Singh",
                LocalDateTime.now().minusDays(2),
                "Photos Uploaded",
                "I’ve shared the album from our trip.",
                true
            ),
            EmailItem(
                4,
                "https://randomuser.me/api/portraits/men/4.jpg",
                "Rahul Mehta",
                LocalDateTime.now().minusHours(3),
                "Invoice Attached",
                "Find attached invoice for July.",
                false
            ),
            EmailItem(
                5,
                "https://randomuser.me/api/portraits/women/5.jpg",
                "Sneha Patel",
                LocalDateTime.now().minusDays(1),
                "Happy Birthday!",
                "Wishing you a wonderful day filled with love and laughter!",
                true
            ),
            EmailItem(
                6,
                "https://randomuser.me/api/portraits/men/1.jpg",
                "Vikram Desai",
                LocalDateTime.now().minusMinutes(30),
                "New Task Assigned",
                "Check Jira for your next ticket.",
                false
            ),
            EmailItem(
                7,
                "https://randomuser.me/api/portraits/women/2.jpg",
                "Meera Nair",
                LocalDateTime.now().minusDays(3),
                "Project Timeline",
                "Updated project timeline is in the shared folder.",
                false
            ),
            EmailItem(
                8,
                "https://randomuser.me/api/portraits/men/5.jpg",
                "Karan Malhotra",
                LocalDateTime.now().minusDays(2),
                "Lunch Invite",
                "Let’s catch up over lunch tomorrow?",
                true
            ),
            EmailItem(
                9,
                "https://randomuser.me/api/portraits/women/4.jpg",
                "Radhika Jain",
                LocalDateTime.now().minusHours(5),
                "Weekly Summary",
                "Summary of team progress is attached.",
                false
            ),
            EmailItem(
                10,
                "https://randomuser.me/api/portraits/men/3.jpg",
                "Amit Reddy",
                LocalDateTime.now().minusMinutes(10),
                "Quick Call?",
                "Need 5 minutes to clarify the UI spec.",
                true
            ),
            EmailItem(
                11,
                "https://randomuser.me/api/portraits/women/10.jpg",
                "Neha Sharma",
                LocalDateTime.now().minusDays(4),
                "Reminder",
                "Don't forget to submit your appraisal by Friday.",
                false
            ),
            EmailItem(
                12,
                "https://randomuser.me/api/portraits/men/10.jpg",
                "Rohan Gupta",
                LocalDateTime.now().minusHours(2),
                "Feedback Required",
                "Please review the design mockups.",
                true
            ),
            EmailItem(
                13,
                "https://randomuser.me/api/portraits/women/7.jpg",
                "Anjali Rao",
                LocalDateTime.now().minusDays(5),
                "Flight Confirmation",
                "Your flight has been confirmed for next week.",
                false
            ),
            EmailItem(
                14,
                "https://randomuser.me/api/portraits/men/8.jpg",
                "Dev Sharma",
                LocalDateTime.now().minusDays(1),
                "App Deployment",
                "Production deployment scheduled for tonight.",
                false
            ),
            EmailItem(
                15,
                "https://randomuser.me/api/portraits/women/9.jpg",
                "Isha Batra",
                LocalDateTime.now().minusMinutes(5),
                "Follow Up",
                "Just following up on our last discussion.",
                true
            )


        )
    }


}