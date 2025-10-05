package it.casaricci.esamevds.persistence

import it.casaricci.esamevds.data.ExamData
import it.casaricci.esamevds.data.ExamQuestion
import java.util.*

class ExamRepository(private val database: AppDatabase) {

    fun startExam(examData: ExamData): Long {
        return database.runInTransaction<Long> {
            val examId = database.examDao().insert(createExam(examData))
            database.questionDao().insertAll(*createQuestions(examId, examData))
            examId
        }
    }

    fun updateExam(examId: Long, questionId: Int, answer: Int?) {
        return database.runInTransaction {
            // question index in database is 1-based
            database.examDao().updateExam(examId, questionId + 1, Date())
            database.questionDao().updateQuestion(examId, questionId + 1, answer)
        }
    }

    fun updateExamProgress(examId: Long, questionId: Int) {
        return database.runInTransaction {
            // question index in database is 1-based
            database.examDao().updateExam(examId, questionId + 1, Date())
        }
    }

    fun endExam(examId: Long) {
        return database.runInTransaction {
            database.examDao().updateExam(examId, 0, Date())
        }
    }

    @Suppress("ArrayInDataClass")
    data class OngoingExam(val examData: ExamData, val answers: Array<Int?>, val currentQuestion: Int)

    fun loadExam(examId: Long): OngoingExam? {
        val exam = database.examDao().findById(examId)
        exam?.let {
            val questions: MutableList<ExamQuestion> = mutableListOf()
            val answers: MutableList<Int?> = mutableListOf()
            for (question in database.questionDao().findForExam(examId)) {
                val answersCatalog = arrayOf(
                    question.answer1,
                    question.answer2,
                    question.answer3,
                    question.answer4
                ).filterNotNull().toTypedArray()

                questions.add(ExamQuestion(
                    question.text,
                    answersCatalog,
                    question.correctAnswer,
                    question.picturePath,
                    question.subject
                ))
                answers.add(question.answer)
            }

            return OngoingExam(
                ExamData(it.specialty, questions),
                answers.toTypedArray(),
                // question index in database is 1-based (coercing to 0 since -1 has no sense)
                exam.currentQuestion.dec().coerceAtLeast(0)
            )
        } ?: return null
    }

    fun getOngoingExam(specialty: Int): Exam? {
        return database.examDao().findOngoing(specialty)
    }

    data class ExamStats(val examId: Long, val questions: Int, val answers: Int)

    fun getExamStats(examId: Long): ExamStats? {
        val questions = database.questionDao().findForExam(examId)
        return ExamStats(
            examId,
            questions.size,
            questions.count { question -> question.answer != null }
        )
    }

    fun cancelExam(examId: Long) {
        return database.runInTransaction {
            database.questionDao().deleteForExam(examId)
            database.examDao().deleteById(examId)
        }
    }

    private fun getOngoingExamData(specialty: Int): Pair<Exam, List<Question>?>? {
        val exam = database.examDao().findOngoing(specialty) ?: return null
        return Pair(exam, database.questionDao().findForExam(exam.id))
    }

    private fun createExam(examData: ExamData): Exam {
        return Exam(
            specialty = examData.specialty,
            lastAccess = Date(),
            currentQuestion = 1
        )
    }

    private fun createQuestions(examId: Long, examData: ExamData): Array<Question> {
        return examData.questions.mapIndexed { index, question ->
            val answer1: String = question.answers[0]
            val answer2: String? = if (question.answers.size > 1) question.answers[1] else null
            val answer3: String? = if (question.answers.size > 2) question.answers[2] else null
            val answer4: String? = if (question.answers.size > 3) question.answers[3] else null
            Question(
                examId = examId,
                questionId = index + 1,
                text = question.question,
                answer1 = answer1,
                answer2 = answer2,
                answer3 = answer3,
                answer4 = answer4,
                picturePath = question.picturePath,
                correctAnswer = question.correctAnswer,
                answer = null,
                subject = question.subject
            )
        }.toTypedArray()
    }

}
