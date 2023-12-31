package entities;

import java.io.Serializable;

/**
 * Represents the statistics of an exam, including average, median, and grade.
 * 
 * @author Paz Fayer
 * @author Maayan Avittan
 * 
 */
public class ExamStatistics implements Serializable {

	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 7381844305026846443L;

	private String avg;
	private String med;
	private String grade;

	/**
	 * Constructs an ExamStatistics object with the specified average, median, and
	 * grade.
	 *
	 * @param avg   the average of the exam scores
	 * @param med   the median of the exam scores
	 * @param grade the grade associated with the exam statistics
	 */
	public ExamStatistics(String avg, String med, String grade) {
		this.avg = avg;
		this.med = med;
		this.grade = grade;
	}

	/**
	 * Returns the average of the exam scores.
	 *
	 * @return the average of the exam scores
	 */
	public String getAvg() {
		return avg;
	}

	/**
	 * Sets the average of the exam scores.
	 *
	 * @param avg the average of the exam scores to be set
	 */
	public void setAvg(String avg) {
		this.avg = avg;
	}

	/**
	 * Returns the median of the exam scores.
	 *
	 * @return the median of the exam scores
	 */
	public String getMed() {
		return med;
	}

	/**
	 * Sets the median of the exam scores.
	 *
	 * @param med the median of the exam scores to be set
	 */
	public void setMed(String med) {
		this.med = med;
	}

	/**
	 * Returns the grade associated with the exam statistics.
	 *
	 * @return the grade associated with the exam statistics
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * Sets the grade associated with the exam statistics.
	 *
	 * @param grade the grade to be set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * Returns a string representation of the ExamStatistics object.
	 *
	 * @return a string representation of the ExamStatistics object
	 */
	@Override
	public String toString() {
		return "ExamStatistics [avg=" + avg + ", med=" + med + ", grade=" + grade + "]";
	}
	

}
