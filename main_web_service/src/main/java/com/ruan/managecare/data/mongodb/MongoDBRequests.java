package com.ruan.managecare.data.mongodb;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.*;

public class MongoDBRequests {

    private final static String mongoInstance = "95.85.17.118:55065";
    private final static String dbName = "managecare";
    private final static String cName = "patient";
    private final static String cred = "cred";

    final static DBObject project1 = new BasicDBObject("$project", new BasicDBObject("answeredCheckIns", 1)
            .append("_id", 0));

    final static DBObject project2 = new BasicDBObject("$project", new BasicDBObject("mrn", 1)
            .append("question", "$answeredCheckIns.multipleChoiceQuestions.text")
            .append("answer", "$answeredCheckIns.multipleChoiceQuestions.answer")
            .append("time", "$answeredCheckIns.multipleChoiceQuestions.answerTimeStamp")
            .append("_id", 0));

    final static List<DBObject> pipelineMock = Arrays.asList(
            project1
            , new BasicDBObject("$unwind", "$answeredCheckIns")
            , new BasicDBObject("$unwind", "$answeredCheckIns.multipleChoiceQuestions")
            , project2
    );

    private static DBCollection createCollection(String dbInstance, String dbName, String cName) throws UnknownHostException {
        return new MongoClient(dbInstance).getDB(dbName).getCollection(cName);
    }

    public static Iterable<DBObject> eatingAnswers(String pId) {

        try {
            return createCollection(mongoInstance, dbName, cName)
                    .aggregate(createPipeLine(pId, "Does your pain stop you from eating/drinking?"))
                    .results();
        } catch (Exception e) {
            System.out.println("Something wrong with db connection: " + e.getMessage());
            return null;
        }
    }

    public static Iterable<DBObject> painAnswers(String pId) {

        try {
            return createCollection(mongoInstance, dbName, cName)
                    .aggregate(createPipeLine(pId, "How bad is your mouth pain/sore throat?"))
                    .results();
        } catch (Exception e) {
            System.out.println("Something wrong with db connection: " + e.getMessage());
            return null;
        }
    }

    private static List<DBObject> createPipeLine(String pId, String question) {
        List<DBObject> pipeline = new ArrayList<>(pipelineMock);
        pipeline.add(0, new BasicDBObject("$match", new BasicDBObject("_id", new ObjectId(pId))));
        pipeline.add(new BasicDBObject("$match",
                new BasicDBObject("question", question)));
        return pipeline;
    }

    public static boolean isEatingOk(String pId) {

        Iterator<DBObject> answRev = getDbObjectReversIterator(eatingAnswers(pId));

        return getBadTimeMilliseconds(answRev, "I can’t eat") < 12 * 60 * 60 * 1000;
    }

    private static Iterator<DBObject> getDbObjectReversIterator(Iterable<DBObject> answers) {

        List<DBObject> temp = new ArrayList<>();

        for (DBObject answer : answers) {
            temp.add(0, answer);
        }
        return temp.iterator();
    }

    public static boolean isPainNotSevere(String pId) {

        Iterator<DBObject> answRev = getDbObjectReversIterator(painAnswers(pId));

        return getBadTimeMilliseconds(answRev, "severe") < 12 * 60 * 60 * 1000;
    }

    public static boolean isPainNotSevereOrModerate(String pId) {

        Iterator<DBObject> answRev = getDbObjectReversIterator(painAnswers(pId));

        return getBadTimeMillisecondsTwoAnswers(answRev, "severe", "moderate") < 16 * 60 * 60 * 1000;
    }

    private static long getBadTimeMilliseconds(Iterator<DBObject> answers, String aText) {
        Date firstBad = new Date();

        while (answers.hasNext()) {

            DBObject answer = answers.next();

            if (answer.get("answer").equals(aText)) {
                firstBad = (Date) answer.get("time");
            } else {
                break;
            }
        }

        return new Date().getTime() - firstBad.getTime();
    }

    private static long getBadTimeMillisecondsTwoAnswers(
            Iterator<DBObject> answers, String a1, String a2) {

        Date firstBad = new Date();

        while (answers.hasNext()) {

            DBObject answer = answers.next();

            if (answer.get("answer").equals(a1) || answer.get("answer").equals(a2)) {
                firstBad = (Date) answer.get("time");
            } else {
                break;
            }
        }

        return new Date().getTime() - firstBad.getTime();
    }

    public static List<AnswersData> getAnsweredCheckInsData(String pId, int limit) {
        List<AnswersData> answers = new ArrayList<>();

        Iterator<DBObject> iteratorEating = getDbObjectReversIterator(eatingAnswers(pId));
        Iterator<DBObject> iteratorPain = getDbObjectReversIterator(painAnswers(pId));

        for (int i = 0; i < limit; i++) {

            if (iteratorPain.hasNext() && iteratorEating.hasNext()) {

                AnswersData a = new AnswersData();

                DBObject pain = iteratorPain.next();
                DBObject eating = iteratorEating.next();

                a.setTimeStamp( (Date) pain.get("time"));
                a.addQuestionAndAnswer( (String) pain.get("question"), (String) pain.get("answer"));
                a.addQuestionAndAnswer( (String) eating.get("question"), (String) eating.get("answer"));

                answers.add(0, a);
            } else break;
        }
        return answers;
    }

    public static DBCursor getCred() throws UnknownHostException {
        return createCollection(mongoInstance, dbName, cred).find(new BasicDBObject());
    }
}
