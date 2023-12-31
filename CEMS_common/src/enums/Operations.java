package enums;

/**
 * enum class for operations when client send message for server and server for
 * client 
 * 
 * @author Ofek Ben Avraham
 * @author Maayan Avittan
 * @author Rotem Porat
 * @author Guy Pariente
 * @author Almog Elbaz
 * @author Paz Fayer
 */
public enum Operations {
	
	LecturerLogin, HeadOfDepartmentLogin, StudentLogin, UserLogout, 
	HeadOfDepGetRequests,NoResponse,
	HeadOfDepAcceptTimeChangeRequest, HeadOfDepRejectTimeChangeRequest,GetLecturers,GetStudents,GetCourses,HeadOfDepGetExamByCourseCode,HeadOfDepGetExamByStudentId,HeadOfDepGetUsers,
	AddRequestToDB,
	LecturerGetCoursesToValidateExams, LecturerGetActiveExams, lecturerLockExam, LecturerSetTimeToChange,
	LecturerGetDoneExamsByCourseChosen,LecturerGetStudentsIdToValidate,LecturerGetSingleExamToValidate,
	 LecturerGetExamAfterCheck,LecturerGetStudentAnswersAfterCheck,LecturerApprovesExamCheck,LecturerGetExamsToShowStats,
	
	 AddQuestion,
	Port, UpdateQuestion, GetQuestion, UpdateQuestionClient, ConnectionSucceeded, GetQuestionRepository,
	GetPersonalQuestionRepository, Disconect, GetFields, GetCoursesByField, DeleteQuestionById, DeleteExamById,
	GetExamRepository, GetPersonalExamRepository, ShowQuestionsInExam, GetQuestionsByCourse, AddExam, AddQuestionInExam,
	DefineCode, AllWorks, ShowQuestionsInExamToActive, GetExamRepositoryToActive, ActivateExam,
	// Student
	StudentGetExam, StudentViewGrades, StudentStartComputerizedExam, StudentComputerizedExamLockCheck,
	StudentComputerizedExamTimeChangeCheck, StudentFinishComputerizedExam, StudentExamCheckIfCheated,
	StudentCheckIfDidTheExam, GetExamStatistics, GetDuration, RepositoryOwner, GetExamStatisticsStudent,
	
}
