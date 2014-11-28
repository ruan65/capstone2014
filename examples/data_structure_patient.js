db.patient.find({firstName: 'Walter'})
{
        "_id" : ObjectId("54786bc760b2c483b7a68d8c"),
        "_class" : "com.ruan.managecare.entities.peronalities.Patient",
        "mrn" : "MR-111-5555",
        "doctor" : "DL-007123",
        "firstName" : "Walter",
        "lastName" : "White",
        "diagnosis" : "Brain Tumor",
        "description" : "A brain tumor or intracranial neoplasm occurs when abnormal cells form within the brain.",
        "gender" : "male",
        "deviceNotificationsId" : "APA91bFABxFnrmh-HryCCSEyxo97LC-KYtDUVX-OM6eX0eDYY9J4ChegG9TWnqS_R54zdOVMB2zqZDIDB3WEDvhTl_MRmmdxVCHefq2EXZw0fRfrfUBJCOxlyjvIl_9Z7xNik26A457O5oS13rNHcdPQ_5QPtJ_4vvDewlQQxuiday1MLhDu3UQ",
        "dateOfBirth" : ISODate("1964-03-06T21:00:00Z"),
        "dob" : "1964-03-06",
        "checkIn" : {
                "_id" : null,
                "checked" : false,
                "simpleQuestions" : [
                        {
                                "_id" : ObjectId("5458c9b560b2ebabb5aef655"),
                                "category" : "Oncology",
                                "subCategory" : "Check-In",
                                "code" : "FOLL-1/001",
                                "text" : "Have you followed all doctor recommendations?",
                                "answered" : false,
                                "yesAnswer" : false,
                                "timeMarkNeeded" : false,
                                "clarifyingQuestionsNeeded" : false,
                                "clarifyingQuestions" : [ ]
                        },
                        {
                                "_id" : ObjectId("5466493e60b251340ae1e99f"),
                                "category" : "Oncology",
                                "subCategory" : "Check-In",
                                "code" : "pain-main",
                                "text" : "Did you take your painkiller medication?",
                                "answered" : false,
                                "yesAnswer" : false,
                                "timeMarkNeeded" : false,
                                "clarifyingQuestionsNeeded" : true,
                                "clarifyingQuestions" : [
                                        {
                                                "_id" : ObjectId("5453f7899014fbecefcd4a7a"),
                                                "category" : "Oncology",
                                                "subCategory" : "Check-In",
                                                "code" : "Lortab",
                                                "text" : "Did you take your Lortab?",
                                                "answered" : false,
                                                "yesAnswer" : false,
                                                "timeMarkNeeded" : true,
                                                "clarifyingQuestionsNeeded" : false,
                                                "clarifyingQuestions" : [ ]
                                        },
                                        {
                                                "_id" : ObjectId("54664b5b60b251340ae1e9a1"),
                                                "category" : "Oncology",
                                                "subCategory" : "Check-In",
                                                "code" : "OxyContin",
                                                "text" : "Did you take your OxyContin?",
                                                "answered" : false,
                                                "yesAnswer" : false,
                                                "timeMarkNeeded" : true,
                                                "clarifyingQuestionsNeeded" : false,
                                                "clarifyingQuestions" : [ ]
                                        },
                                        {
                                                "_id" : ObjectId("54664b2360b251340ae1e9a0"),
                                                "category" : "Oncology",
                                                "subCategory" : "Check-In",
                                                "code" : "Vicodin",
                                                "text" : "Did you take your Vicodin?",
                                                "answered" : false,
                                                "yesAnswer" : false,
                                                "timeMarkNeeded" : true,
                                                "clarifyingQuestionsNeeded" : false,
                                                "clarifyingQuestions" : [ ]
                                        }
                                ]
                        }
                ],
                "multipleChoiceQuestions" : [
                        {
                                "_id" : ObjectId("54561e4b60b219ce1a72e90d"),
                                "category" : "Oncology",
                                "subCategory" : "Check-In",
                                "code" : "Fe-Ch-Mo-001",
                                "text" : "How do you feel today?",
                                "answers" : [
                                        "good",
                                        "moderate",
                                        "bad"
                                ],
                                "answered" : false
                        },
                        {
                                "_id" : ObjectId("5453f5029014fbecefcd4a77"),
                                "category" : "Oncology",
                                "subCategory" : "Check-In",
                                "code" : "Ca-Ch-Mo-007",
                                "text" : "How bad is your mouth pain/sore throat?",
                                "answers" : [
                                        "well-controlled",
                                        "moderate",
                                        "severe"
                                ],
                                "answered" : false
                        },
                        {
                                "_id" : ObjectId("54561f4160b219ce1a72e90e"),
                                "category" : "Oncology",
                                "subCategory" : "Check-In",
                                "code" : "Eate-001",
                                "text" : "Does your pain stop you from eating/drinking?",
                                "answers" : [
                                        "no",
                                        "some",
                                        "I can’t eat"
                                ],
                                "answered" : false
                        }
                ]
        },
        "medication" : [
                "Lortab",
                "OxyContin",
                "Vicodin"
        ],
        "answeredCheckIns" : [
                {
                        "_id" : null,
                        "checked" : true,
                        "responseDateTime" : ISODate("2014-11-28T13:16:12.580Z"),
                        "simpleQuestions" : [
                                {
                                        "_id" : ObjectId("5458c9b560b2ebabb5aef655"),
                                        "category" : "Oncology",
                                        "subCategory" : "Check-In",
                                        "code" : "FOLL-1/001",
                                        "text" : "Have you followed all doctor recommendations?",
                                        "answered" : true,
                                        "yesAnswer" : true,
                                        "timeMarkNeeded" : false,
                                        "clarifyingQuestionsNeeded" : false,
                                        "clarifyingQuestions" : [ ],
                                        "answerTimeStamp" : ISODate("2014-11-28T13:15:43.088Z")
                                },
                                {
                                        "_id" : ObjectId("5466493e60b251340ae1e99f"),
                                        "category" : "Oncology",
                                        "subCategory" : "Check-In",
                                        "code" : "pain-main",
                                        "text" : "Did you take your painkiller medication?",
                                        "answered" : false,
                                        "yesAnswer" : false,
                                        "timeMarkNeeded" : false,
                                        "clarifyingQuestionsNeeded" : true,
                                        "clarifyingQuestions" : [
                                                {
                                                        "_id" : ObjectId("5453f7899014fbecefcd4a7a"),
                                                        "category" : "Oncology",
                                                        "subCategory" : "Check-In",
                                                        "code" : "Lortab",
                                                        "text" : "Did you take your Lortab?",
                                                        "answered" : true,
                                                        "yesAnswer" : true,
                                                        "timeMarkNeeded" : true,
                                                        "clarifyingQuestionsNeeded" : false,
                                                        "clarifyingQuestions" : [ ],
                                                        "timeMark" : ISODate("2014-11-28T11:15:00.259Z"),
                                                        "answerTimeStamp" : ISODate("2014-11-28T13:16:00.589Z")
                                                },
                                                {
                                                        "_id" : ObjectId("54664b5b60b251340ae1e9a1"),
                                                        "category" : "Oncology",
                                                        "subCategory" : "Check-In",
                                                        "code" : "OxyContin",
                                                        "text" : "Did you take your OxyContin?",
                                                        "answered" : true,
                                                        "yesAnswer" : false,
                                                        "timeMarkNeeded" : true,
                                                        "clarifyingQuestionsNeeded" : false,
                                                        "clarifyingQuestions" : [ ],
                                                        "answerTimeStamp" : ISODate("2014-11-28T13:16:06.617Z")
                                                },
                                                {
                                                        "_id" : ObjectId("54664b2360b251340ae1e9a0"),
                                                        "category" : "Oncology",
                                                        "subCategory" : "Check-In",
                                                        "code" : "Vicodin",
                                                        "text" : "Did you take your Vicodin?",
                                                        "answered" : true,
                                                        "yesAnswer" : true,
                                                        "timeMarkNeeded" : true,
                                                        "clarifyingQuestionsNeeded" : false,
                                                        "clarifyingQuestions" : [ ],
                                                        "timeMark" : ISODate("2014-11-27T13:16:00.922Z"),
                                                        "answerTimeStamp" : ISODate("2014-11-28T13:16:12.565Z")
                                                }
                                        ]
                                }
                        ],
                        "multipleChoiceQuestions" : [
                                {
                                        "_id" : ObjectId("54561e4b60b219ce1a72e90d"),
                                        "category" : "Oncology",
                                        "subCategory" : "Check-In",
                                        "code" : "Fe-Ch-Mo-001",
                                        "text" : "How do you feel today?",
                                        "answer" : "moderate",
                                        "answers" : [
                                                "good",
                                                "moderate",
                                                "bad"
                                        ],
                                        "answered" : true,
                                        "answerTimeStamp" : ISODate("2014-11-28T13:15:31.950Z")
                                },
                                {
                                        "_id" : ObjectId("5453f5029014fbecefcd4a77"),
                                        "category" : "Oncology",
                                        "subCategory" : "Check-In",
                                        "code" : "Ca-Ch-Mo-007",
                                        "text" : "How bad is your mouth pain/sore throat?",
                                        "answer" : "moderate",
                                        "answers" : [
                                                "well-controlled",
                                                "moderate",
                                                "severe"
                                        ],
                                        "answered" : true,
                                        "answerTimeStamp" : ISODate("2014-11-28T13:15:39.398Z")
                                },
                                {
                                        "_id" : ObjectId("54561f4160b219ce1a72e90e"),
                                        "category" : "Oncology",
                                        "subCategory" : "Check-In",
                                        "code" : "Eate-001",
                                        "text" : "Does your pain stop you from eating/drinking?",
                                        "answer" : "I can’t eat",
                                        "answers" : [
                                                "no",
                                                "some",
                                                "I can’t eat"
                                        ],
                                        "answered" : true,
                                        "answerTimeStamp" : ISODate("2014-11-28T13:15:40.967Z")
                                }
                        ]
                }
        ]
}
