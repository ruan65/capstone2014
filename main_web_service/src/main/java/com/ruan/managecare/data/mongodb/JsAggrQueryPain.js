qp = db.patient.aggregate([

      {$match: {_id: ObjectId("546bc41460b2689179d45a82")}}
    , {$project: {answeredCheckIns: 1, _id: 0}}
    , {$unwind: '$answeredCheckIns'}
    , {$unwind: '$answeredCheckIns.multipleChoiceQuestions'}
    , {$project: {
        question: '$answeredCheckIns.multipleChoiceQuestions.text',
        answer: '$answeredCheckIns.multipleChoiceQuestions.answer',
        time: '$answeredCheckIns.multipleChoiceQuestions.answerTimeStamp',
        _id: 0}}
    , {$match: {'question': "How bad is your mouth pain/sore throat?"}}
])