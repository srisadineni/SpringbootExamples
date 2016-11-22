package etl.app.domin;

public class BaseTo {

	private String text;
	private Long employeeId;
	private String dbViewList;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getDbViewList() {
		return dbViewList;
	}

	public void setDbViewList(String dbViewList) {
		this.dbViewList = dbViewList;
	}

}
