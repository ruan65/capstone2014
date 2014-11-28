q = db.patient.aggregate([

      {$project: {answeredCheckIns: 1, _id: 0}}
    , {$unwind: '$answeredCheckIns'}
    , {$unwind: '$answeredCheckIns.multipleChoiceQuestions'}
    , {$project: {
        mrn: 1,
        question: '$answeredCheckIns.multipleChoiceQuestions.text',
        answer: '$answeredCheckIns.multipleChoiceQuestions.answer',
        time: '$answeredCheckIns.multipleChoiceQuestions.answerTimeStamp',
        _id: 0}}
    , {$match: {'question': "Does your pain stop you from eating/drinking?"}}
])