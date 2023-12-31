package enums;

/**
 * Answers for client when message come from the server.
 * 
 * @author Ofek Ben Avraham
 * @author Maayan Avittan
 * @author Rotem Porat
 * @author Guy Pariente
 * @author Almog elbaz
 * @author Paz Fayer
 */
public enum Answers {
	// Login
	IncorrectLecturer, OfflineLecturer, OnlineLecturer, IncorrectHeadOfDepartment, OfflineHeadOfDepartment,
	OnlineHeadOfDepartment, IncorrectStudent, OfflineStudent, OnlineStudent, Logout,
	// Exams
	ExamNotFound, ExamNotStartYet, ExamClosed, FoundComputerizedExam, FoundManaulExam, AlreadyDoneTheExam,ExamsToValidateFound,ExamsToValidateNotFound,
	// Head Of Department
	NotRespondedRequestsNotFound, NotRespondedRequestsFound, updatedApprovedRequestSuccessfully, ErrorInDB,
	updatedRejectedRequestSuccessfully,
	// Lecturer
	CoursesForLecturerNotFound, CoursesByLectuerFound, ExamsInProgressFound, ExamsInProgressNotFound,
	UpdatedLockedExamSucessfully, UpdatedLockedExamFailed,NoStudentsToValidate,StudentsToValidateFound,
	NoStudentAnswersToValidate,
	// Student
	NoStudentGradesFound, GradesListFound, NoExamFound, ExamFound, NoQuestionsAvailable,
	QuestionsFoundForStudentInComputerizedExam, ExamActive, ExamLocked, NoSpecificData, StatusUpdated, TimeUpdated,
	StudentAnswersSavedInDB, NoStudentDidThisExamYet, ThereAreStudentsWhoTookThisExam, StudentCheckIfThereAreAnswers,
	GetStatisticsFromDB, NoStatFound, GetDurationFromDB
}
